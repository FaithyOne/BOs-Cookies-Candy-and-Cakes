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

package de.markusbordihn.cookiescandyandcakes.effect.cookie;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectInterface;
import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectManager;
import de.markusbordihn.cookiescandyandcakes.effect.cookie.server.ElderGuardianCookieMysticEffect;
import de.markusbordihn.cookiescandyandcakes.effect.cookie.server.SlimeSugarCookieMysticEffect;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;

public class CookieServerEffectManager extends ServerEffectManager {

  private static final Map<CookieType, ServerEffectInterface> COOKIE_EFFECTS = new HashMap<>();
  private static final CookieServerEffectManager INSTANCE = new CookieServerEffectManager();

  static {
    COOKIE_EFFECTS.put(CookieType.SLIME_SUGAR_COOKIE_MYSTIC, new SlimeSugarCookieMysticEffect());
    COOKIE_EFFECTS.put(
        CookieType.ELDER_GUARDIAN_COOKIE_MYSTIC, new ElderGuardianCookieMysticEffect());
  }

  private CookieServerEffectManager() {}

  public static CookieServerEffectManager getInstance() {
    return INSTANCE;
  }

  public static void applyCookieEffect(final ServerPlayer player, final CookieType cookieType) {
    if (player == null || cookieType == null) {
      return;
    }

    ServerEffectInterface effect = COOKIE_EFFECTS.get(cookieType);
    if (effect != null) {
      getInstance().applyEffect(player, effect);
    }
  }

  public static void tickPlayer(final ServerPlayer player) {
    getInstance().tick(player);
  }

  public static void tickPlayers(final Iterable<ServerPlayer> players) {
    getInstance().tickAll(players);
  }

  public static void removeCookieEffect(final ServerPlayer player) {
    getInstance().removeEffect(player);
  }

  public static void clearAllEffects() {
    getInstance().clearAll();
  }

  public static boolean hasCookieEffect(final ServerPlayer player) {
    return getInstance().hasActiveEffect(player);
  }
}
