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
import de.markusbordihn.cookiescandyandcakes.effect.CookieEffectManager;
import de.markusbordihn.cookiescandyandcakes.item.base.BaseSpecialCookie;
import de.markusbordihn.cookiescandyandcakes.item.base.IdentifiableCookie;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MysticCookie extends BaseSpecialCookie implements IdentifiableCookie {

  public MysticCookie(final CookieType cookieType) {
    super(cookieType);
  }

  @Override
  public CookieType getCookieType() {
    return cookieType;
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
    onConsume(livingEntity);

    if (!level.isClientSide) {
      spawnParticles(level, livingEntity);

      if (livingEntity instanceof ServerPlayer serverPlayer) {
        CookieEffectManager.applyEffect(serverPlayer, cookieType);
      }

      CookieType.SpecialCookieEffect effect = cookieType.getSpecialCookieEffect();

      if (!effect.hasEffects()) {
        return super.finishUsingItem(stack, level, livingEntity);
      }

      if (level.random.nextFloat() < effect.lightningChance()) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
          lightning.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
          lightning.setVisualOnly(true);
          level.addFreshEntity(lightning);
        }
      }

      if (effect.enableSounds()) {
        SoundEvent sound = CookieSoundType.getMysticSound(effect.mysticSoundType());
        if (sound != null) {
          level.playSound(
              null,
              livingEntity.getX(),
              livingEntity.getY(),
              livingEntity.getZ(),
              sound,
              SoundSource.PLAYERS,
              effect.soundVolume(),
              CookieSoundType.getPitch(effect.mysticSoundType(), level.random.nextFloat()));
        }
      }
    }

    return super.finishUsingItem(stack, level, livingEntity);
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    tooltipComponents.add(getIdentifiedTooltip());
  }

  @Override
  public Component getName(ItemStack stack) {
    return getUnidentifiedName();
  }
}
