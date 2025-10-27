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

package de.markusbordihn.cookiescandyandcakes.effect;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

public class CookieEffectManager {

  private static final Map<CookieType, CookieEffectInterface> SPECIAL_EFFECTS = new HashMap<>();
  private static final Map<UUID, ActiveEffect> activeEffects = new HashMap<>();

  static {
    SPECIAL_EFFECTS.put(CookieType.SLIME_SUGAR_COOKIE_MYSTIC, new SlimeSugarCookieMysticEffect());
  }

  private CookieEffectManager() {}

  public static void applyEffect(final ServerPlayer player, final CookieType cookieType) {
    if (player == null || cookieType == null) {
      return;
    }

    CookieEffectInterface effect = SPECIAL_EFFECTS.get(cookieType);
    if (effect != null) {
      UUID playerUUID = player.getUUID();
      activeEffects.put(playerUUID, new ActiveEffect(effect, 0, effect.getDuration()));
      effect.onStart(player);
    }
  }

  public static void tick(final ServerPlayer player) {
    if (player == null) {
      return;
    }

    UUID playerUUID = player.getUUID();
    ActiveEffect activeEffect = activeEffects.get(playerUUID);

    if (activeEffect != null) {
      activeEffect.tick(player);

      if (activeEffect.isExpired()) {
        activeEffect.effect.onEnd(player);
        activeEffects.remove(playerUUID);
      }
    }
  }

  public static void tickAll(final Iterable<ServerPlayer> players) {
    for (ServerPlayer player : players) {
      tick(player);
    }
  }

  public static void removeEffect(final ServerPlayer player) {
    if (player != null) {
      UUID playerUUID = player.getUUID();
      ActiveEffect activeEffect = activeEffects.remove(playerUUID);
      if (activeEffect != null) {
        activeEffect.effect.onEnd(player);
      }
    }
  }

  public static void clearAll() {
    activeEffects.clear();
  }

  static class ActiveEffect {
    private final CookieEffectInterface effect;
    private final int duration;
    private int elapsedTicks;

    public ActiveEffect(CookieEffectInterface effect, int elapsedTicks, int duration) {
      this.effect = effect;
      this.elapsedTicks = elapsedTicks;
      this.duration = duration;
    }

    public void tick(ServerPlayer player) {
      effect.tick(player, elapsedTicks);
      elapsedTicks++;
    }

    public boolean isExpired() {
      return elapsedTicks >= duration;
    }
  }
}
