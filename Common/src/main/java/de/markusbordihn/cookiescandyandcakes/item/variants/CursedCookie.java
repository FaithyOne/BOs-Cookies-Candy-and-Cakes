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

package de.markusbordihn.cookiescandyandcakes.item.variants;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieSoundType;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.item.base.BaseSpecialCookie;
import de.markusbordihn.cookiescandyandcakes.item.base.IdentifiableCookie;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CursedCookie extends BaseSpecialCookie implements IdentifiableCookie {

  public CursedCookie(final CookieType cookieType) {
    super(cookieType);
  }

  @Override
  public CookieType getCookieType() {
    return cookieType;
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    if (!level.isClientSide) {
      applySpecialEffects(level, entity);
    }
    return super.finishUsingItem(stack, level, entity);
  }

  private void applySpecialEffects(Level level, LivingEntity entity) {
    CookieType.SpecialCookieEffect effect = cookieType.getSpecialCookieEffect();
    if (!effect.hasEffects()) {
      return;
    }

    spawnLightningIfNeeded(level, entity, effect);
    playSoundIfEnabled(level, entity, effect);
    applyDarknessIfNeeded(entity, effect);
  }

  private void spawnLightningIfNeeded(
      Level level, LivingEntity entity, CookieType.SpecialCookieEffect effect) {
    if (level.random.nextFloat() < effect.lightningChance()) {
      LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
      if (lightning == null) {
        return;
      }
      lightning.moveTo(entity.getX(), entity.getY(), entity.getZ());
      lightning.setVisualOnly(true);
      level.addFreshEntity(lightning);
    }
  }

  private void playSoundIfEnabled(
      Level level, LivingEntity entity, CookieType.SpecialCookieEffect effect) {
    if (effect.enableSounds()) {
      SoundEvent sound = CookieSoundType.getCursedSound(effect.cursedSoundType());
      if (sound != null) {
        level.playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            sound,
            SoundSource.PLAYERS,
            effect.soundVolume(),
            CookieSoundType.getPitch(effect.cursedSoundType(), level.random.nextFloat()));
      }
    }
  }

  private void applyDarknessIfNeeded(LivingEntity entity, CookieType.SpecialCookieEffect effect) {
    if (entity instanceof Player player && effect.darknessEffectDuration() > 0) {
      player.addEffect(
          new MobEffectInstance(
              MobEffects.DARKNESS, effect.darknessEffectDuration(), 0, false, false, false));
    }
  }
}
