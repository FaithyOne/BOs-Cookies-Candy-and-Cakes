/*
 * Copyright 2025 Markus Bordihn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.markusbordihn.cookiescandyandcakes.item;

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.entity.ThrownCandy;
import de.markusbordihn.cookiescandyandcakes.registry.ModEntityTypes;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class BaseCandy extends Item {

  private static final int STACK_SIZE = 16;
  private static final int THROW_MIN_TICKS = 2;
  private static final int SHOW_BAR_MIN_TICKS = 5;
  private static final int THROW_MAX_TICKS = 20;
  private static final int PAUSE_TICKS = 25;
  private static final int EAT_START_TICKS = 45;
  private static final int USE_DURATION_TICKS = 75;
  private static final float THROW_MIN_VELOCITY = 0.5F;
  private static final float THROW_MAX_VELOCITY = 1.5F;
  private static final float THROW_INACCURACY = 1.0F;
  private static final float LOOK_DOWN_ANGLE = 45.0F;

  protected final CandyType candyType;

  protected BaseCandy(CandyType candyType) {
    super(new Item.Properties().food(buildFoodProperties(candyType)).stacksTo(STACK_SIZE));
    this.candyType = candyType;
  }

  protected static FoodProperties buildFoodProperties(CandyType candyType) {
    FoodProperties.Builder builder =
        new FoodProperties.Builder().nutrition(candyType.getNutrition()).fast();
    if (candyType.hasEffect()) {
      builder.effect(
          new MobEffectInstance(
              candyType.getEffect(), candyType.getEffectDuration(), candyType.getAmplifier()),
          candyType.getEffectChance());
    }
    return builder.build();
  }

  public CandyType getCandyType() {
    return candyType;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack itemStack = player.getItemInHand(hand);
    player.startUsingItem(hand);
    return InteractionResultHolder.consume(itemStack);
  }

  private boolean shouldEatDirectly(Player player) {
    if (player.getXRot() < LOOK_DOWN_ANGLE) {
      return false;
    }

    Vec3 eyePos = player.getEyePosition();
    Vec3 lookVec = player.getLookAngle();
    Vec3 endPos = eyePos.add(lookVec.scale(2.5));

    BlockHitResult hitResult = player.level().clip(new ClipContext(
        eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

    return hitResult.getType() == HitResult.Type.BLOCK 
        && !player.level().getBlockState(hitResult.getBlockPos()).isAir();
  }

  @Override
  public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
    if (!(entity instanceof Player player)) {
      return;
    }

    int useDuration = this.getUseDuration(stack, entity);
    int usedTicks = useDuration - timeLeft;

    if (usedTicks < THROW_MIN_TICKS || usedTicks >= EAT_START_TICKS) {
      return;
    }

    if (!shouldEatDirectly(player)) {
      float velocity = calculateThrowVelocity(usedTicks);
      throwCandy(level, player, stack, velocity);
    }
  }

  private float calculateThrowVelocity(int usedTicks) {
    if (usedTicks < THROW_MIN_TICKS) {
      return THROW_MIN_VELOCITY;
    }
    if (usedTicks >= THROW_MAX_TICKS) {
      return THROW_MAX_VELOCITY;
    }

    int chargeTicks = usedTicks - THROW_MIN_TICKS;
    int maxChargeTicks = THROW_MAX_TICKS - THROW_MIN_TICKS;
    float chargeProgress = (float) chargeTicks / maxChargeTicks;

    return THROW_MIN_VELOCITY + (THROW_MAX_VELOCITY - THROW_MIN_VELOCITY) * chargeProgress;
  }

  private void throwCandy(Level level, Player player, ItemStack stack, float velocity) {
    level.playSound(
        null,
        player.getX(),
        player.getY(),
        player.getZ(),
        SoundEvents.SNOWBALL_THROW,
        SoundSource.PLAYERS,
        0.5F,
        0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

    if (!level.isClientSide) {
      ThrownCandy thrownCandy = new ThrownCandy(ModEntityTypes.THROWN_CANDY.get(), level, player);
      thrownCandy.setItem(stack.copyWithCount(1));
      thrownCandy.shootFromRotation(
          player, player.getXRot(), player.getYRot(), 0.0F, velocity, THROW_INACCURACY);
      level.addFreshEntity(thrownCandy);
    }

    player.awardStat(Stats.ITEM_USED.get(this));
    if (!player.getAbilities().instabuild) {
      stack.shrink(1);
    }
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    if (entity instanceof Player player) {
      player.awardStat(Stats.ITEM_USED.get(this));
    }
    return super.finishUsingItem(stack, level, entity);
  }

  @Override
  public int getUseDuration(ItemStack stack, LivingEntity entity) {
    return USE_DURATION_TICKS;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack stack) {
    return UseAnim.EAT;
  }

  @Override
  public void appendHoverText(
      ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
    tooltipComponents.add(
        Component.translatable(this.getDescriptionId() + ".desc")
            .withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return candyType.getVariant() == CandyType.CandyVariant.MYSTIC;
  }

  public static int getThrowMinTicks() {
    return THROW_MIN_TICKS;
  }

  public static int getShowBarMinTicks() {
    return SHOW_BAR_MIN_TICKS;
  }

  public static int getThrowMaxTicks() {
    return THROW_MAX_TICKS;
  }

  public static int getPauseTicks() {
    return PAUSE_TICKS;
  }

  public static int getEatStartTicks() {
    return EAT_START_TICKS;
  }
}
