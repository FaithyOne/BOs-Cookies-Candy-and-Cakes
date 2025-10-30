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

package de.markusbordihn.cookiescandyandcakes.effect.candy;

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.effect.ClientEffectInterface;
import de.markusbordihn.cookiescandyandcakes.effect.ClientEffectManager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.player.LocalPlayer;

public class CandyClientEffectManager extends ClientEffectManager {

  private static final Map<CandyType, ClientEffectInterface> CANDY_EFFECTS = new HashMap<>();
  private static final CandyClientEffectManager INSTANCE = new CandyClientEffectManager();

  static {}

  private CandyClientEffectManager() {}

  public static CandyClientEffectManager getInstance() {
    return INSTANCE;
  }

  public static void applyCandyEffect(final LocalPlayer player, final CandyType candyType) {
    if (player == null || candyType == null) {
      return;
    }

    ClientEffectInterface effect = CANDY_EFFECTS.get(candyType);
    if (effect != null) {
      getInstance().applyEffect(player, effect);
    }
  }

  public static void tickPlayer(final LocalPlayer player) {
    getInstance().tick(player);
  }

  public static void removeCandyEffect(final LocalPlayer player) {
    getInstance().removeEffect(player);
  }

  public static void clearAllEffects() {
    getInstance().clearAll();
  }

  public static boolean hasCandyEffect(final LocalPlayer player) {
    return getInstance().hasActiveEffect(player);
  }
}
