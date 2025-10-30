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

package de.markusbordihn.cookiescandyandcakes.data.candies;

import java.util.Locale;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum CandyType {
  TEST_CANDY(CandyVariant.NORMAL, null, 0),
  TEST_CANDY_MYSTIC(CandyVariant.MYSTIC, MobEffects.MOVEMENT_SPEED, 0),
  TEST_CANDY_CURSED(CandyVariant.CURSED, MobEffects.MOVEMENT_SLOWDOWN, 0),
  CHORUS_CANDY(CandyVariant.NORMAL, null, 0),
  CHORUS_CANDY_MYSTIC(CandyVariant.MYSTIC, MobEffects.LEVITATION, 0),
  CHORUS_CANDY_CURSED(CandyVariant.CURSED, MobEffects.CONFUSION, 0);

  private static final int NUTRITION = 2;
  private static final int EFFECT_DURATION = 200;
  private static final float EFFECT_CHANCE = 1.0F;

  private final CandyVariant variant;
  private final Holder<MobEffect> effect;
  private final int amplifier;
  private final String id;

  CandyType(final CandyVariant variant, final Holder<MobEffect> effect, final int amplifier) {
    this.variant = variant;
    this.effect = effect;
    this.amplifier = amplifier;
    this.id = name().toLowerCase(Locale.ROOT);
  }

  public String getId() {
    return id;
  }

  public CandyVariant getVariant() {
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
    return NUTRITION;
  }

  public int getEffectDuration() {
    return EFFECT_DURATION;
  }

  public float getEffectChance() {
    return EFFECT_CHANCE;
  }

  public enum CandyVariant {
    NORMAL,
    MYSTIC,
    CURSED
  }
}
