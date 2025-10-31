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

package de.markusbordihn.cookiescandyandcakes.data;

public record SpecialItemEffect(
    float lightningChance,
    boolean enableSounds,
    float soundVolume,
    int darknessEffectDuration,
    String mysticSoundType,
    String cursedSoundType) {

  public static final SpecialItemEffect NONE =
      new SpecialItemEffect(0.0f, false, 0.0f, 0, "none", "none");

  public static final SpecialItemEffect DEFAULT_MYSTIC =
      new SpecialItemEffect(0.5f, true, 1.0f, 0, "levelup", "none");

  public static final SpecialItemEffect DEFAULT_CURSED =
      new SpecialItemEffect(0.5f, true, 4.0f, 60, "none", "ender_dragon");

  public boolean hasEffects() {
    return lightningChance > 0.0f || enableSounds;
  }
}
