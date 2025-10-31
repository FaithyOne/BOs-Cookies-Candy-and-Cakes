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

package de.markusbordihn.cookiescandyandcakes.entity;

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.item.BaseCandy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownCandy extends ThrowableItemProjectile {

  public ThrownCandy(final EntityType<? extends ThrownCandy> entityType, final Level level) {
    super(entityType, level);
  }

  public ThrownCandy(
      final EntityType<? extends ThrownCandy> entityType,
      final Level level,
      final LivingEntity shooter) {
    super(entityType, shooter, level);
  }

  @Override
  protected Item getDefaultItem() {
    return ItemStack.EMPTY.getItem();
  }

  @Override
  protected void onHitEntity(EntityHitResult entityHitResult) {
    super.onHitEntity(entityHitResult);
    if (!this.level().isClientSide && entityHitResult.getEntity() instanceof LivingEntity target) {
      boolean hasEffect = applyCandyEffect(target);
      Vec3 location = entityHitResult.getLocation();
      spawnImpactEffects(location);
      if (!hasEffect) {
        dropDefaultItem(
            this.level(), new BlockPos((int) location.x, (int) location.y, (int) location.z));
      }
    }
    this.discard();
  }

  @Override
  protected void onHit(HitResult hitResult) {
    super.onHit(hitResult);
    if (!this.level().isClientSide) {
      this.level().broadcastEntityEvent(this, (byte) 3);
      if (hitResult instanceof BlockHitResult blockHitResult) {
        BlockPos placePos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        spawnImpactEffects(hitResult.getLocation());
        dropDefaultItem(this.level(), placePos);
      }
      this.discard();
    }
  }

  private void spawnImpactEffects(final Vec3 location) {
    Level level = this.level();
    ItemStack itemStack = this.getItem();

    if (!itemStack.isEmpty()) {
      for (int i = 0; i < 8; i++) {
        level.addParticle(
            new ItemParticleOption(ParticleTypes.ITEM, itemStack),
            location.x,
            location.y,
            location.z,
            (level.random.nextFloat() - 0.5) * 0.2,
            level.random.nextFloat() * 0.2,
            (level.random.nextFloat() - 0.5) * 0.2);
      }
    }

    level.playSound(
        null,
        new BlockPos((int) location.x, (int) location.y, (int) location.z),
        SoundEvents.HONEY_BLOCK_BREAK,
        SoundSource.NEUTRAL,
        0.8F,
        1.2F + level.random.nextFloat() * 0.4F);
  }

  private void dropDefaultItem(final Level level, final BlockPos blockPos) {
    ItemStack itemStack = this.getItem();
    if (itemStack == null || itemStack.isEmpty()) {
      return;
    }
    ItemEntity droppedItem =
        new ItemEntity(
            level,
            blockPos.getX() + 0.5,
            blockPos.getY() + 0.5,
            blockPos.getZ() + 0.5,
            new ItemStack(itemStack.getItem()));
    droppedItem.setDeltaMovement(
        (level.random.nextFloat() - 0.5F) * 0.3F, 0.25F, (level.random.nextFloat() - 0.5F) * 0.3F);
    level.addFreshEntity(droppedItem);
  }

  private boolean applyCandyEffect(final LivingEntity target) {
    ItemStack stack = this.getItem();
    if (stack.getItem() instanceof BaseCandy candy) {
      CandyType candyType = candy.getCandyType();
      if (candyType.hasEffect()) {
        target.addEffect(
            new MobEffectInstance(
                candyType.getEffect(), candyType.getEffectDuration(), candyType.getAmplifier()));
        return true;
      }
    }
    return false;
  }
}
