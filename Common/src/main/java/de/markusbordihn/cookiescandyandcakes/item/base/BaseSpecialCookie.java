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

package de.markusbordihn.cookiescandyandcakes.item.base;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.effect.cookie.CookieClientEffectManager;
import de.markusbordihn.cookiescandyandcakes.effect.cookie.CookieServerEffectManager;
import de.markusbordihn.cookiescandyandcakes.item.BaseCookie;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class BaseSpecialCookie extends BaseCookie
    implements BaseSpecialItem<IdentifiableCookie> {

  protected static final ParticleOptions[] SPECIAL_PARTICLES = {
    ParticleTypes.SOUL,
    ParticleTypes.SOUL_FIRE_FLAME,
    ParticleTypes.SMOKE,
    ParticleTypes.LARGE_SMOKE,
    ParticleTypes.WARPED_SPORE,
    ParticleTypes.CRIMSON_SPORE
  };

  protected BaseSpecialCookie(final CookieType cookieType) {
    super(cookieType);
  }

  @Override
  public IdentifiableCookie getIdentifiable() {
    return (IdentifiableCookie) this;
  }

  @Override
  public void applyEffects(Level level, LivingEntity entity) {
    if (!level.isClientSide) {
      spawnParticles(level, entity);
      if (entity instanceof ServerPlayer serverPlayer) {
        CookieServerEffectManager.applyCookieEffect(serverPlayer, cookieType);
      }
    } else {
      if (entity instanceof LocalPlayer localPlayer) {
        CookieClientEffectManager.applyCookieEffect(localPlayer, cookieType);
      }
    }
  }

  protected void spawnParticles(final Level level, final LivingEntity livingEntity) {
    ParticleOptions particle = SPECIAL_PARTICLES[level.random.nextInt(SPECIAL_PARTICLES.length)];
    for (int i = 0; i < 15; i++) {
      double angle = (2 * Math.PI * i) / 15;
      double offsetX = Math.cos(angle) * 0.5;
      double offsetZ = Math.sin(angle) * 0.5;
      level.addParticle(
          particle,
          livingEntity.getX() + offsetX,
          livingEntity.getY() + 1.0,
          livingEntity.getZ() + offsetZ,
          0.0,
          0.1,
          0.0);
    }

    for (int i = 0; i < 10; i++) {
      level.addParticle(
          particle,
          livingEntity.getX() + (level.random.nextDouble() - 0.5) * 1.5,
          livingEntity.getY() + level.random.nextDouble() * 2.0,
          livingEntity.getZ() + (level.random.nextDouble() - 0.5) * 1.5,
          (level.random.nextDouble() - 0.5) * 0.1,
          level.random.nextDouble() * 0.1,
          (level.random.nextDouble() - 0.5) * 0.1);
    }
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    BaseSpecialItem.super.finishUsingItem(stack, level, entity);
    return super.finishUsingItem(stack, level, entity);
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    BaseSpecialItem.super.appendHoverText(stack, tooltipComponents, tooltipFlag);
  }

  @Override
  public Component getName(ItemStack stack) {
    return BaseSpecialItem.super.getName(stack);
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return true;
  }
}
