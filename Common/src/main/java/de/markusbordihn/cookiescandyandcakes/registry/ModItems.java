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

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.data.minicakes.MiniCakeType;
import de.markusbordihn.cookiescandyandcakes.item.MoldItem;
import de.markusbordihn.cookiescandyandcakes.item.variants.CursedCandy;
import de.markusbordihn.cookiescandyandcakes.item.variants.CursedCookie;
import de.markusbordihn.cookiescandyandcakes.item.variants.MysticCandy;
import de.markusbordihn.cookiescandyandcakes.item.variants.MysticCookie;
import de.markusbordihn.cookiescandyandcakes.item.variants.NormalCandy;
import de.markusbordihn.cookiescandyandcakes.item.variants.NormalCookie;
import de.markusbordihn.cookiescandyandcakes.item.variants.NormalMiniCake;
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
  public static final Item ELDER_GUARDIAN_COOKIE =
      createCookieItem(CookieType.ELDER_GUARDIAN_COOKIE);
  public static final Item ELDER_GUARDIAN_COOKIE_MYSTIC =
      createCookieItem(CookieType.ELDER_GUARDIAN_COOKIE_MYSTIC);
  public static final Item ELDER_GUARDIAN_COOKIE_CURSED =
      createCookieItem(CookieType.ELDER_GUARDIAN_COOKIE_CURSED);
  public static final Item MINI_APPLE_CAKE = createMiniCakeItem(MiniCakeType.MINI_APPLE_CAKE);
  public static final Item MINI_MELON_CAKE = createMiniCakeItem(MiniCakeType.MINI_MELON_CAKE);
  public static final Item MINI_PUMPKIN_CAKE = createMiniCakeItem(MiniCakeType.MINI_PUMPKIN_CAKE);
  public static final Item MINI_CARROT_CAKE = createMiniCakeItem(MiniCakeType.MINI_CARROT_CAKE);
  public static final Item MINI_BEETROOT_CAKE = createMiniCakeItem(MiniCakeType.MINI_BEETROOT_CAKE);
  public static final Item MINI_GLOW_BERRY_CAKE =
      createMiniCakeItem(MiniCakeType.MINI_GLOW_BERRY_CAKE);
  public static final Item MINI_SWEET_BERRY_CAKE =
      createMiniCakeItem(MiniCakeType.MINI_SWEET_BERRY_CAKE);
  public static final Item MINI_CHOCOLATE_CAKE =
      createMiniCakeItem(MiniCakeType.MINI_CHOCOLATE_CAKE);
  public static final Item MINI_MUSHROOM_CAKE = createMiniCakeItem(MiniCakeType.MINI_MUSHROOM_CAKE);
  public static final Item MINI_CAKE_MOLD = new MoldItem();
  public static final Item CANDY_MOLD = new MoldItem();
  public static final Item CHOCOLATE_MOLD = new MoldItem();
  public static final Item FLOUR = new Item(new Item.Properties());
  public static final Item BUTTER_CHURN = new MoldItem();
  public static final Item BUTTER = new Item(new Item.Properties());
  public static final Item CHOCOLATE =
      new Item(new Item.Properties().food(new net.minecraft.world.food.FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build()));
  public static final Item CINNAMON_STICK = new Item(new Item.Properties());
  public static final Item CINNAMON_KNIFE = new de.markusbordihn.cookiescandyandcakes.item.CinnamonKnifeItem();
  public static final Item VANILLA_BEAN = new Item(new Item.Properties());
  public static final Item GINGER = new Item(new Item.Properties());
  public static final Item TEST_CANDY = createCandyItem(CandyType.TEST_CANDY);
  public static final Item TEST_CANDY_MYSTIC = createCandyItem(CandyType.TEST_CANDY_MYSTIC);
  public static final Item TEST_CANDY_CURSED = createCandyItem(CandyType.TEST_CANDY_CURSED);
  public static final Item APPLE_CANDY = createCandyItem(CandyType.APPLE_CANDY);
  public static final Item APPLE_CANDY_MYSTIC = createCandyItem(CandyType.APPLE_CANDY_MYSTIC);
  public static final Item APPLE_CANDY_CURSED = createCandyItem(CandyType.APPLE_CANDY_CURSED);
  public static final Item CHORUS_CANDY = createCandyItem(CandyType.CHORUS_CANDY);
  public static final Item CHORUS_CANDY_MYSTIC = createCandyItem(CandyType.CHORUS_CANDY_MYSTIC);
  public static final Item CHORUS_CANDY_CURSED = createCandyItem(CandyType.CHORUS_CANDY_CURSED);
  public static final Item CHOCOLATE_CANDY = createCandyItem(CandyType.CHOCOLATE_CANDY);
  public static final Item CHOCOLATE_CANDY_MYSTIC =
      createCandyItem(CandyType.CHOCOLATE_CANDY_MYSTIC);
  public static final Item CHOCOLATE_CANDY_CURSED =
      createCandyItem(CandyType.CHOCOLATE_CANDY_CURSED);
  public static final Item GLOW_BERRY_CANDY = createCandyItem(CandyType.GLOW_BERRY_CANDY);
  public static final Item GLOW_BERRY_CANDY_MYSTIC =
      createCandyItem(CandyType.GLOW_BERRY_CANDY_MYSTIC);
  public static final Item GLOW_BERRY_CANDY_CURSED =
      createCandyItem(CandyType.GLOW_BERRY_CANDY_CURSED);
  public static final Item GOLDEN_CANDY = createCandyItem(CandyType.GOLDEN_CANDY);
  public static final Item GOLDEN_CANDY_MYSTIC = createCandyItem(CandyType.GOLDEN_CANDY_MYSTIC);
  public static final Item GOLDEN_CANDY_CURSED = createCandyItem(CandyType.GOLDEN_CANDY_CURSED);
  public static final Item MELON_CANDY = createCandyItem(CandyType.MELON_CANDY);
  public static final Item MELON_CANDY_MYSTIC = createCandyItem(CandyType.MELON_CANDY_MYSTIC);
  public static final Item MELON_CANDY_CURSED = createCandyItem(CandyType.MELON_CANDY_CURSED);
  public static final Item PUMPKIN_CANDY = createCandyItem(CandyType.PUMPKIN_CANDY);
  public static final Item PUMPKIN_CANDY_MYSTIC = createCandyItem(CandyType.PUMPKIN_CANDY_MYSTIC);
  public static final Item PUMPKIN_CANDY_CURSED = createCandyItem(CandyType.PUMPKIN_CANDY_CURSED);
  public static final Item SWEET_BERRY_CANDY = createCandyItem(CandyType.SWEET_BERRY_CANDY);
  public static final Item SWEET_BERRY_CANDY_MYSTIC =
      createCandyItem(CandyType.SWEET_BERRY_CANDY_MYSTIC);
  public static final Item SWEET_BERRY_CANDY_CURSED =
      createCandyItem(CandyType.SWEET_BERRY_CANDY_CURSED);

  private ModItems() {}

  private static Item createCookieItem(final CookieType cookieType) {
    return switch (cookieType.getVariant()) {
      case NORMAL -> new NormalCookie(cookieType);
      case MYSTIC -> new MysticCookie(cookieType);
      case CURSED -> new CursedCookie(cookieType);
    };
  }

  private static Item createCandyItem(final CandyType candyType) {
    return switch (candyType.getVariant()) {
      case NORMAL -> new NormalCandy(candyType);
      case MYSTIC -> new MysticCandy(candyType);
      case CURSED -> new CursedCandy(candyType);
    };
  }

  private static Item createMiniCakeItem(final MiniCakeType miniCakeType) {
    return new NormalMiniCake(miniCakeType);
  }
}
