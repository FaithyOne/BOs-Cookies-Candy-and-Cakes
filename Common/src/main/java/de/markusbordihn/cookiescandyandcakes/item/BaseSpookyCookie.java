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

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieProperties;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class BaseSpookyCookie extends Item {

  protected static final ParticleOptions[] SPOOKY_PARTICLES = {
    ParticleTypes.SOUL,
    ParticleTypes.SOUL_FIRE_FLAME,
    ParticleTypes.SMOKE,
    ParticleTypes.LARGE_SMOKE,
    ParticleTypes.WARPED_SPORE,
    ParticleTypes.CRIMSON_SPORE
  };

  protected BaseSpookyCookie(CookieType cookieType) {
    super(new Item.Properties().food(buildFoodProperties(cookieType)).stacksTo(CookieProperties.STACK_SIZE));
  }

  private static FoodProperties buildFoodProperties(CookieType cookieType) {
    FoodProperties.Builder builder = new FoodProperties.Builder().nutrition(CookieProperties.NUTRITION).fast();
    if (cookieType.hasEffect()) {
      builder.effect(
          new MobEffectInstance(cookieType.getEffect(), CookieProperties.EFFECT_DURATION, cookieType.getAmplifier()),
          CookieProperties.EFFECT_CHANCE);
    }
    return builder.build();
  }

  protected void spawnParticles(Level level, LivingEntity livingEntity) {
    ParticleOptions particle = SPOOKY_PARTICLES[level.random.nextInt(SPOOKY_PARTICLES.length)];

    for (int i = 0; i < 15; i++) {
      double angle = (2 * Math.PI * i) / 15;
      double offsetX = Math.cos(angle) * 0.5;
      double offsetZ = Math.sin(angle) * 0.5;
      level.addParticle(particle, livingEntity.getX() + offsetX, livingEntity.getY() + 1.0,
          livingEntity.getZ() + offsetZ, 0.0, 0.1, 0.0);
    }

    for (int i = 0; i < 10; i++) {
      level.addParticle(particle,
          livingEntity.getX() + (level.random.nextDouble() - 0.5) * 1.5,
          livingEntity.getY() + level.random.nextDouble() * 2.0,
          livingEntity.getZ() + (level.random.nextDouble() - 0.5) * 1.5,
          (level.random.nextDouble() - 0.5) * 0.1, level.random.nextDouble() * 0.1,
          (level.random.nextDouble() - 0.5) * 0.1);
    }
  }

  @Override
  public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".desc"));
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return true;
  }
}
