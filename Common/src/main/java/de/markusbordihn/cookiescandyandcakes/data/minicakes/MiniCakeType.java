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

package de.markusbordihn.cookiescandyandcakes.data.minicakes;

import de.markusbordihn.cookiescandyandcakes.config.MiniCakeConfig;
import java.util.Locale;

public enum MiniCakeType {
  MINI_APPLE_CAKE(3),
  MINI_MELON_CAKE(3),
  MINI_PUMPKIN_CAKE(3),
  MINI_CARROT_CAKE(3),
  MINI_BEETROOT_CAKE(3),
  MINI_GLOW_BERRY_CAKE(3),
  MINI_SWEET_BERRY_CAKE(3),
  MINI_CHOCOLATE_CAKE(3),
  MINI_MUSHROOM_CAKE(3);

  private final int defaultNutrition;
  private final String id;

  MiniCakeType(final int defaultNutrition) {
    this.defaultNutrition = defaultNutrition;
    this.id = name().toLowerCase(Locale.ROOT);
  }

  public String getId() {
    return id;
  }

  public int getDefaultNutrition() {
    return defaultNutrition;
  }

  public int getNutrition() {
    return MiniCakeConfig.getNutrition(this);
  }
}
