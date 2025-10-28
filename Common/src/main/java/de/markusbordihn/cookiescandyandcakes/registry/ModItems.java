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

package de.markusbordihn.cookiescandyandcakes.registry;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.item.variants.CursedCookie;
import de.markusbordihn.cookiescandyandcakes.item.variants.MysticCookie;
import de.markusbordihn.cookiescandyandcakes.item.variants.NormalCookie;
import net.minecraft.world.item.Item;

public class ModItems {
  public static final Item APPLE_COOKIE = createCookieItem(CookieType.APPLE_COOKIE);
  public static final Item APPLE_COOKIE_MYSTIC = createCookieItem(CookieType.APPLE_COOKIE_MYSTIC);
  public static final Item APPLE_COOKIE_CURSED = createCookieItem(CookieType.APPLE_COOKIE_CURSED);
  public static final Item CARROT_COOKIE = createCookieItem(CookieType.CARROT_COOKIE);
  public static final Item CARROT_COOKIE_MYSTIC = createCookieItem(CookieType.CARROT_COOKIE_MYSTIC);
  public static final Item CARROT_COOKIE_CURSED = createCookieItem(CookieType.CARROT_COOKIE_CURSED);
  public static final Item GLOW_BERRY_COOKIE = createCookieItem(CookieType.GLOW_BERRY_COOKIE);
  public static final Item GLOW_BERRY_COOKIE_MYSTIC =
      createCookieItem(CookieType.GLOW_BERRY_COOKIE_MYSTIC);
  public static final Item GLOW_BERRY_COOKIE_CURSED =
      createCookieItem(CookieType.GLOW_BERRY_COOKIE_CURSED);
  public static final Item MELON_COOKIE = createCookieItem(CookieType.MELON_COOKIE);
  public static final Item MELON_COOKIE_MYSTIC = createCookieItem(CookieType.MELON_COOKIE_MYSTIC);
  public static final Item MELON_COOKIE_CURSED = createCookieItem(CookieType.MELON_COOKIE_CURSED);
  public static final Item PUMPKIN_COOKIE = createCookieItem(CookieType.PUMPKIN_COOKIE);
  public static final Item PUMPKIN_COOKIE_MYSTIC =
      createCookieItem(CookieType.PUMPKIN_COOKIE_MYSTIC);
  public static final Item PUMPKIN_COOKIE_CURSED =
      createCookieItem(CookieType.PUMPKIN_COOKIE_CURSED);
  public static final Item SWEET_BERRY_COOKIE = createCookieItem(CookieType.SWEET_BERRY_COOKIE);
  public static final Item SWEET_BERRY_COOKIE_MYSTIC =
      createCookieItem(CookieType.SWEET_BERRY_COOKIE_MYSTIC);
  public static final Item SWEET_BERRY_COOKIE_CURSED =
      createCookieItem(CookieType.SWEET_BERRY_COOKIE_CURSED);
  public static final Item SLIME_SUGAR_COOKIE = createCookieItem(CookieType.SLIME_SUGAR_COOKIE);
  public static final Item SLIME_SUGAR_COOKIE_MYSTIC =
      createCookieItem(CookieType.SLIME_SUGAR_COOKIE_MYSTIC);
  public static final Item SLIME_SUGAR_COOKIE_CURSED =
      createCookieItem(CookieType.SLIME_SUGAR_COOKIE_CURSED);
  public static final Item CREEPER_CRUNCH_COOKIE =
      createCookieItem(CookieType.CREEPER_CRUNCH_COOKIE);
  public static final Item CREEPER_CRUNCH_COOKIE_MYSTIC =
      createCookieItem(CookieType.CREEPER_CRUNCH_COOKIE_MYSTIC);
  public static final Item CREEPER_CRUNCH_COOKIE_CURSED =
      createCookieItem(CookieType.CREEPER_CRUNCH_COOKIE_CURSED);

  private ModItems() {}

  private static Item createCookieItem(CookieType cookieType) {
    return switch (cookieType.getVariant()) {
      case NORMAL -> new NormalCookie(cookieType);
      case MYSTIC -> new MysticCookie(cookieType);
      case CURSED -> new CursedCookie(cookieType);
    };
  }
}
