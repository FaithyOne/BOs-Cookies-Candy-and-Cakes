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

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class CookieSoundType {

  private CookieSoundType() {}

  public static SoundEvent getMysticSound(final String soundType) {
    return switch (soundType.toLowerCase()) {
      case "levelup" -> SoundEvents.PLAYER_LEVELUP;
      case "enchant" -> SoundEvents.ENCHANTMENT_TABLE_USE;
      case "amethyst" -> SoundEvents.AMETHYST_BLOCK_CHIME;
      case "bell" -> SoundEvents.BELL_RESONATE;
      case "none" -> null;
      default -> SoundEvents.PLAYER_LEVELUP;
    };
  }

  public static SoundEvent getCursedSound(final String soundType) {
    return switch (soundType.toLowerCase()) {
      case "ender_dragon" -> SoundEvents.ENDER_DRAGON_AMBIENT;
      case "wither" -> SoundEvents.WITHER_AMBIENT;
      case "ambient_cave" -> SoundEvents.AMBIENT_CAVE.value();
      case "sculk" -> SoundEvents.SCULK_SHRIEKER_SHRIEK;
      case "none" -> null;
      default -> SoundEvents.ENDER_DRAGON_AMBIENT;
    };
  }

  public static float getPitch(final String soundType, final float randomValue) {
    return switch (soundType.toLowerCase()) {
      case "levelup" -> 1.0f + randomValue * 0.2f;
      case "enchant" -> 0.8f + randomValue * 0.4f;
      case "amethyst" -> 1.2f + randomValue * 0.3f;
      case "bell" -> 0.9f + randomValue * 0.2f;
      case "ender_dragon" -> 0.7f + randomValue * 0.3f;
      case "wither" -> 0.6f + randomValue * 0.4f;
      case "ambient_cave" -> 1.0f;
      case "sculk" -> 0.8f + randomValue * 0.2f;
      default -> 1.0f;
    };
  }
}
