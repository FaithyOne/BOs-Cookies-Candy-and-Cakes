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

package de.markusbordihn.cookiescandyandcakes.item;

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.data.minicakes.MiniCakeType;
import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import de.markusbordihn.cookiescandyandcakes.tabs.ModCreativeTabs;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class FabricModItems {

  public static final CreativeModeTab COOKIES_TAB =
      Registry.register(
          BuiltInRegistries.CREATIVE_MODE_TAB,
          ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, ModCreativeTabs.COOKIES_TAB_ID),
          ModCreativeTabs.createCookiesTab().build());

  public static final CreativeModeTab SPECIAL_COOKIES_TAB =
      Registry.register(
          BuiltInRegistries.CREATIVE_MODE_TAB,
          ResourceLocation.fromNamespaceAndPath(
              Constants.MOD_ID, ModCreativeTabs.SPECIAL_COOKIES_TAB_ID),
          ModCreativeTabs.createSpecialCookiesTab().build());

  static {
    registerCookie(CookieType.APPLE_COOKIE, ModItems.APPLE_COOKIE);
    registerCookie(CookieType.APPLE_COOKIE_MYSTIC, ModItems.APPLE_COOKIE_MYSTIC);
    registerCookie(CookieType.APPLE_COOKIE_CURSED, ModItems.APPLE_COOKIE_CURSED);
    registerCookie(CookieType.CARROT_COOKIE, ModItems.CARROT_COOKIE);
    registerCookie(CookieType.CARROT_COOKIE_MYSTIC, ModItems.CARROT_COOKIE_MYSTIC);
    registerCookie(CookieType.CARROT_COOKIE_CURSED, ModItems.CARROT_COOKIE_CURSED);
    registerCookie(CookieType.GLOW_BERRY_COOKIE, ModItems.GLOW_BERRY_COOKIE);
    registerCookie(CookieType.GLOW_BERRY_COOKIE_MYSTIC, ModItems.GLOW_BERRY_COOKIE_MYSTIC);
    registerCookie(CookieType.GLOW_BERRY_COOKIE_CURSED, ModItems.GLOW_BERRY_COOKIE_CURSED);
    registerCookie(CookieType.MELON_COOKIE, ModItems.MELON_COOKIE);
    registerCookie(CookieType.MELON_COOKIE_MYSTIC, ModItems.MELON_COOKIE_MYSTIC);
    registerCookie(CookieType.MELON_COOKIE_CURSED, ModItems.MELON_COOKIE_CURSED);
    registerCookie(CookieType.PUMPKIN_COOKIE, ModItems.PUMPKIN_COOKIE);
    registerCookie(CookieType.PUMPKIN_COOKIE_MYSTIC, ModItems.PUMPKIN_COOKIE_MYSTIC);
    registerCookie(CookieType.PUMPKIN_COOKIE_CURSED, ModItems.PUMPKIN_COOKIE_CURSED);
    registerCookie(CookieType.SWEET_BERRY_COOKIE, ModItems.SWEET_BERRY_COOKIE);
    registerCookie(CookieType.SWEET_BERRY_COOKIE_MYSTIC, ModItems.SWEET_BERRY_COOKIE_MYSTIC);
    registerCookie(CookieType.SWEET_BERRY_COOKIE_CURSED, ModItems.SWEET_BERRY_COOKIE_CURSED);
    registerCookie(CookieType.SLIME_SUGAR_COOKIE, ModItems.SLIME_SUGAR_COOKIE);
    registerCookie(CookieType.SLIME_SUGAR_COOKIE_MYSTIC, ModItems.SLIME_SUGAR_COOKIE_MYSTIC);
    registerCookie(CookieType.SLIME_SUGAR_COOKIE_CURSED, ModItems.SLIME_SUGAR_COOKIE_CURSED);
    registerCookie(CookieType.CREEPER_CRUNCH_COOKIE, ModItems.CREEPER_CRUNCH_COOKIE);
    registerCookie(CookieType.CREEPER_CRUNCH_COOKIE_MYSTIC, ModItems.CREEPER_CRUNCH_COOKIE_MYSTIC);
    registerCookie(CookieType.CREEPER_CRUNCH_COOKIE_CURSED, ModItems.CREEPER_CRUNCH_COOKIE_CURSED);
    registerCookie(CookieType.ELDER_GUARDIAN_COOKIE, ModItems.ELDER_GUARDIAN_COOKIE);
    registerCookie(CookieType.ELDER_GUARDIAN_COOKIE_MYSTIC, ModItems.ELDER_GUARDIAN_COOKIE_MYSTIC);
    registerCookie(CookieType.ELDER_GUARDIAN_COOKIE_CURSED, ModItems.ELDER_GUARDIAN_COOKIE_CURSED);
    registerMiniCake(MiniCakeType.MINI_APPLE_CAKE, ModItems.MINI_APPLE_CAKE);
    registerMiniCake(MiniCakeType.MINI_MELON_CAKE, ModItems.MINI_MELON_CAKE);
    registerMiniCake(MiniCakeType.MINI_PUMPKIN_CAKE, ModItems.MINI_PUMPKIN_CAKE);
    registerMiniCake(MiniCakeType.MINI_CARROT_CAKE, ModItems.MINI_CARROT_CAKE);
    registerMiniCake(MiniCakeType.MINI_BEETROOT_CAKE, ModItems.MINI_BEETROOT_CAKE);
    registerMiniCake(MiniCakeType.MINI_GLOW_BERRY_CAKE, ModItems.MINI_GLOW_BERRY_CAKE);
    registerMiniCake(MiniCakeType.MINI_SWEET_BERRY_CAKE, ModItems.MINI_SWEET_BERRY_CAKE);
    registerMiniCake(MiniCakeType.MINI_CHOCOLATE_CAKE, ModItems.MINI_CHOCOLATE_CAKE);
    registerMiniCake(MiniCakeType.MINI_MUSHROOM_CAKE, ModItems.MINI_MUSHROOM_CAKE);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mini_cake_mold"),
        ModItems.MINI_CAKE_MOLD);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "candy_mold"),
        ModItems.CANDY_MOLD);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chocolate_mold"),
        ModItems.CHOCOLATE_MOLD);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "flour"),
        ModItems.FLOUR);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "butter_churn"),
        ModItems.BUTTER_CHURN);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "butter"),
        ModItems.BUTTER);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chocolate"),
        ModItems.CHOCOLATE);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cinnamon_stick"),
        ModItems.CINNAMON_STICK);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cinnamon_knife"),
        ModItems.CINNAMON_KNIFE);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "vanilla_bean"),
        ModItems.VANILLA_BEAN);
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ginger"),
        ModItems.GINGER);
    registerCandy(CandyType.TEST_CANDY, ModItems.TEST_CANDY);
    registerCandy(CandyType.TEST_CANDY_MYSTIC, ModItems.TEST_CANDY_MYSTIC);
    registerCandy(CandyType.TEST_CANDY_CURSED, ModItems.TEST_CANDY_CURSED);
    registerCandy(CandyType.APPLE_CANDY, ModItems.APPLE_CANDY);
    registerCandy(CandyType.APPLE_CANDY_MYSTIC, ModItems.APPLE_CANDY_MYSTIC);
    registerCandy(CandyType.APPLE_CANDY_CURSED, ModItems.APPLE_CANDY_CURSED);
    registerCandy(CandyType.CHORUS_CANDY, ModItems.CHORUS_CANDY);
    registerCandy(CandyType.CHORUS_CANDY_MYSTIC, ModItems.CHORUS_CANDY_MYSTIC);
    registerCandy(CandyType.CHORUS_CANDY_CURSED, ModItems.CHORUS_CANDY_CURSED);
    registerCandy(CandyType.CHOCOLATE_CANDY, ModItems.CHOCOLATE_CANDY);
    registerCandy(CandyType.CHOCOLATE_CANDY_MYSTIC, ModItems.CHOCOLATE_CANDY_MYSTIC);
    registerCandy(CandyType.CHOCOLATE_CANDY_CURSED, ModItems.CHOCOLATE_CANDY_CURSED);
    registerCandy(CandyType.GLOW_BERRY_CANDY, ModItems.GLOW_BERRY_CANDY);
    registerCandy(CandyType.GLOW_BERRY_CANDY_MYSTIC, ModItems.GLOW_BERRY_CANDY_MYSTIC);
    registerCandy(CandyType.GLOW_BERRY_CANDY_CURSED, ModItems.GLOW_BERRY_CANDY_CURSED);
    registerCandy(CandyType.GOLDEN_CANDY, ModItems.GOLDEN_CANDY);
    registerCandy(CandyType.GOLDEN_CANDY_MYSTIC, ModItems.GOLDEN_CANDY_MYSTIC);
    registerCandy(CandyType.GOLDEN_CANDY_CURSED, ModItems.GOLDEN_CANDY_CURSED);
    registerCandy(CandyType.MELON_CANDY, ModItems.MELON_CANDY);
    registerCandy(CandyType.MELON_CANDY_MYSTIC, ModItems.MELON_CANDY_MYSTIC);
    registerCandy(CandyType.MELON_CANDY_CURSED, ModItems.MELON_CANDY_CURSED);
    registerCandy(CandyType.PUMPKIN_CANDY, ModItems.PUMPKIN_CANDY);
    registerCandy(CandyType.PUMPKIN_CANDY_MYSTIC, ModItems.PUMPKIN_CANDY_MYSTIC);
    registerCandy(CandyType.PUMPKIN_CANDY_CURSED, ModItems.PUMPKIN_CANDY_CURSED);
    registerCandy(CandyType.SWEET_BERRY_CANDY, ModItems.SWEET_BERRY_CANDY);
    registerCandy(CandyType.SWEET_BERRY_CANDY_MYSTIC, ModItems.SWEET_BERRY_CANDY_MYSTIC);
    registerCandy(CandyType.SWEET_BERRY_CANDY_CURSED, ModItems.SWEET_BERRY_CANDY_CURSED);
  }

  private FabricModItems() {}

  public static void register() {
    // Items are registered in static initialization
  }

  private static void registerCookie(final CookieType cookieType, final Item item) {
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, cookieType.getId()),
        item);
  }

  private static void registerMiniCake(final MiniCakeType miniCakeType, final Item item) {
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, miniCakeType.getId()),
        item);
  }

  private static void registerCandy(final CandyType candyType, final Item item) {
    Registry.register(
        BuiltInRegistries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, candyType.getId()),
        item);
  }
}
