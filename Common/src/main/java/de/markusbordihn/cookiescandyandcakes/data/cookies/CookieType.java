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

package de.markusbordihn.cookiescandyandcakes.data.cookies;

import de.markusbordihn.cookiescandyandcakes.config.CookieConfig;
import java.util.Locale;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum CookieType {
  APPLE_COOKIE(CookieVariant.NORMAL, null, 0),
  APPLE_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.ABSORPTION, 0),
  APPLE_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.POISON, 0),
  CARROT_COOKIE(CookieVariant.NORMAL, null, 0),
  CARROT_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.NIGHT_VISION, 0),
  CARROT_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.BLINDNESS, 0),
  GLOW_BERRY_COOKIE(CookieVariant.NORMAL, null, 0),
  GLOW_BERRY_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.GLOWING, 0),
  GLOW_BERRY_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.DARKNESS, 0),
  MELON_COOKIE(CookieVariant.NORMAL, null, 0),
  MELON_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.REGENERATION, 0),
  MELON_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.WITHER, 0),
  PUMPKIN_COOKIE(CookieVariant.NORMAL, null, 0),
  PUMPKIN_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.DAMAGE_RESISTANCE, 0),
  PUMPKIN_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.WEAKNESS, 1),
  SWEET_BERRY_COOKIE(CookieVariant.NORMAL, null, 0),
  SWEET_BERRY_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.JUMP, 2),
  SWEET_BERRY_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.MOVEMENT_SLOWDOWN, 1),
  SLIME_SUGAR_COOKIE(CookieVariant.NORMAL, null, 0),
  SLIME_SUGAR_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.JUMP, 1),
  SLIME_SUGAR_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.MOVEMENT_SLOWDOWN, 0),
  CREEPER_CRUNCH_COOKIE(CookieVariant.NORMAL, null, 0),
  CREEPER_CRUNCH_COOKIE_MYSTIC(CookieVariant.MYSTIC, MobEffects.MOVEMENT_SPEED, 0),
  CREEPER_CRUNCH_COOKIE_CURSED(CookieVariant.CURSED, MobEffects.CONFUSION, 0) {
    @Override
    public int getEffectDuration() {
      return 60; // 3 seconds (60 ticks) for nausea effect
    }
  };

  private final CookieVariant variant;
  private final Holder<MobEffect> effect;
  private final int amplifier;
  private final String id;

  CookieType(CookieVariant variant, Holder<MobEffect> effect, int amplifier) {
    this.variant = variant;
    this.effect = effect;
    this.amplifier = amplifier;
    this.id = name().toLowerCase(Locale.ROOT);
  }

  public String getId() {
    return id;
  }

  public CookieVariant getVariant() {
    return variant;
  }

  public Holder<MobEffect> getEffect() {
    return effect;
  }

  public int getAmplifier() {
    return amplifier;
  }

  public boolean hasEffect() {
    return effect != null;
  }

  public int getNutrition() {
    return CookieConfig.getNutrition(this);
  }

  public int getEffectDuration() {
    return CookieConfig.getEffectDuration(this);
  }

  public float getEffectChance() {
    return CookieConfig.getEffectChance(this);
  }

  public SpecialCookieEffect getSpecialCookieEffect() {
    return CookieConfig.getSpecialCookieEffect(this);
  }

  public enum CookieVariant {
    NORMAL,
    MYSTIC,
    CURSED
  }

  public record SpecialCookieEffect(
      float lightningChance,
      boolean enableSounds,
      float soundVolume,
      int darknessEffectDuration,
      String mysticSoundType,
      String cursedSoundType) {

    public static final SpecialCookieEffect NONE =
        new SpecialCookieEffect(0.0f, false, 0.0f, 0, "none", "none");

    public static final SpecialCookieEffect DEFAULT_MYSTIC =
        new SpecialCookieEffect(0.5f, true, 1.0f, 0, "levelup", "none");

    public static final SpecialCookieEffect DEFAULT_CURSED =
        new SpecialCookieEffect(0.5f, true, 4.0f, 60, "none", "ender_dragon");

    public boolean hasEffects() {
      return lightningChance > 0.0f || enableSounds;
    }
  }
}
