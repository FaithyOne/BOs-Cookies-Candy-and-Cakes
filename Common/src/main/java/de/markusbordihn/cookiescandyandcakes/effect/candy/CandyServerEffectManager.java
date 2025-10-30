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
import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectInterface;
import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectManager;
import de.markusbordihn.cookiescandyandcakes.effect.candy.server.ChorusCandyCursedEffect;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;

public class CandyServerEffectManager extends ServerEffectManager {

  private static final Map<CandyType, ServerEffectInterface> CANDY_EFFECTS = new HashMap<>();
  private static final CandyServerEffectManager INSTANCE = new CandyServerEffectManager();

  static {
    CANDY_EFFECTS.put(CandyType.CHORUS_CANDY_CURSED, new ChorusCandyCursedEffect());
  }

  private CandyServerEffectManager() {}

  public static CandyServerEffectManager getInstance() {
    return INSTANCE;
  }

  public static void applyCandyEffect(final ServerPlayer player, final CandyType candyType) {
    if (player == null || candyType == null) {
      return;
    }

    ServerEffectInterface effect = CANDY_EFFECTS.get(candyType);
    if (effect != null) {
      getInstance().applyEffect(player, effect);
    }
  }

  public static void tickPlayer(final ServerPlayer player) {
    getInstance().tick(player);
  }

  public static void tickPlayers(final Iterable<ServerPlayer> players) {
    for (ServerPlayer player : players) {
      tickPlayer(player);
    }
  }

  public static void removeCandyEffect(final ServerPlayer player) {
    getInstance().removeEffect(player);
  }

  public static void clearAllEffects() {
    getInstance().clearAll();
  }

  public static boolean hasCandyEffect(final ServerPlayer player) {
    return getInstance().hasActiveEffect(player);
  }
}
