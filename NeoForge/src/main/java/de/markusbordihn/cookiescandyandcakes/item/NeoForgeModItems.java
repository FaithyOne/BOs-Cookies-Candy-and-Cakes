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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COOKIES_TAB =
      CREATIVE_MODE_TABS.register(
          ModCreativeTabs.COOKIES_TAB_ID, ModCreativeTabs.createCookiesTab()::build);

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPECIAL_COOKIES_TAB =
      CREATIVE_MODE_TABS.register(
          ModCreativeTabs.SPECIAL_COOKIES_TAB_ID, ModCreativeTabs.createSpecialCookiesTab()::build);

  static {
    ITEMS.register(CookieType.APPLE_COOKIE.getId(), () -> ModItems.APPLE_COOKIE);
    ITEMS.register(CookieType.APPLE_COOKIE_MYSTIC.getId(), () -> ModItems.APPLE_COOKIE_MYSTIC);
    ITEMS.register(CookieType.APPLE_COOKIE_CURSED.getId(), () -> ModItems.APPLE_COOKIE_CURSED);
    ITEMS.register(CookieType.CARROT_COOKIE.getId(), () -> ModItems.CARROT_COOKIE);
    ITEMS.register(CookieType.CARROT_COOKIE_MYSTIC.getId(), () -> ModItems.CARROT_COOKIE_MYSTIC);
    ITEMS.register(CookieType.CARROT_COOKIE_CURSED.getId(), () -> ModItems.CARROT_COOKIE_CURSED);
    ITEMS.register(CookieType.GLOW_BERRY_COOKIE.getId(), () -> ModItems.GLOW_BERRY_COOKIE);
    ITEMS.register(
        CookieType.GLOW_BERRY_COOKIE_MYSTIC.getId(), () -> ModItems.GLOW_BERRY_COOKIE_MYSTIC);
    ITEMS.register(
        CookieType.GLOW_BERRY_COOKIE_CURSED.getId(), () -> ModItems.GLOW_BERRY_COOKIE_CURSED);
    ITEMS.register(CookieType.MELON_COOKIE.getId(), () -> ModItems.MELON_COOKIE);
    ITEMS.register(CookieType.MELON_COOKIE_MYSTIC.getId(), () -> ModItems.MELON_COOKIE_MYSTIC);
    ITEMS.register(CookieType.MELON_COOKIE_CURSED.getId(), () -> ModItems.MELON_COOKIE_CURSED);
    ITEMS.register(CookieType.PUMPKIN_COOKIE.getId(), () -> ModItems.PUMPKIN_COOKIE);
    ITEMS.register(CookieType.PUMPKIN_COOKIE_MYSTIC.getId(), () -> ModItems.PUMPKIN_COOKIE_MYSTIC);
    ITEMS.register(CookieType.PUMPKIN_COOKIE_CURSED.getId(), () -> ModItems.PUMPKIN_COOKIE_CURSED);
    ITEMS.register(CookieType.SWEET_BERRY_COOKIE.getId(), () -> ModItems.SWEET_BERRY_COOKIE);
    ITEMS.register(
        CookieType.SWEET_BERRY_COOKIE_MYSTIC.getId(), () -> ModItems.SWEET_BERRY_COOKIE_MYSTIC);
    ITEMS.register(
        CookieType.SWEET_BERRY_COOKIE_CURSED.getId(), () -> ModItems.SWEET_BERRY_COOKIE_CURSED);
    ITEMS.register(CookieType.SLIME_SUGAR_COOKIE.getId(), () -> ModItems.SLIME_SUGAR_COOKIE);
    ITEMS.register(
        CookieType.SLIME_SUGAR_COOKIE_MYSTIC.getId(), () -> ModItems.SLIME_SUGAR_COOKIE_MYSTIC);
    ITEMS.register(
        CookieType.SLIME_SUGAR_COOKIE_CURSED.getId(), () -> ModItems.SLIME_SUGAR_COOKIE_CURSED);
    ITEMS.register(CookieType.CREEPER_CRUNCH_COOKIE.getId(), () -> ModItems.CREEPER_CRUNCH_COOKIE);
    ITEMS.register(
        CookieType.CREEPER_CRUNCH_COOKIE_MYSTIC.getId(),
        () -> ModItems.CREEPER_CRUNCH_COOKIE_MYSTIC);
    ITEMS.register(
        CookieType.CREEPER_CRUNCH_COOKIE_CURSED.getId(),
        () -> ModItems.CREEPER_CRUNCH_COOKIE_CURSED);
    ITEMS.register(CookieType.ELDER_GUARDIAN_COOKIE.getId(), () -> ModItems.ELDER_GUARDIAN_COOKIE);
    ITEMS.register(
        CookieType.ELDER_GUARDIAN_COOKIE_MYSTIC.getId(),
        () -> ModItems.ELDER_GUARDIAN_COOKIE_MYSTIC);
    ITEMS.register(
        CookieType.ELDER_GUARDIAN_COOKIE_CURSED.getId(),
        () -> ModItems.ELDER_GUARDIAN_COOKIE_CURSED);
    ITEMS.register(MiniCakeType.MINI_APPLE_CAKE.getId(), () -> ModItems.MINI_APPLE_CAKE);
    ITEMS.register(MiniCakeType.MINI_MELON_CAKE.getId(), () -> ModItems.MINI_MELON_CAKE);
    ITEMS.register(MiniCakeType.MINI_PUMPKIN_CAKE.getId(), () -> ModItems.MINI_PUMPKIN_CAKE);
    ITEMS.register(MiniCakeType.MINI_CARROT_CAKE.getId(), () -> ModItems.MINI_CARROT_CAKE);
    ITEMS.register(MiniCakeType.MINI_BEETROOT_CAKE.getId(), () -> ModItems.MINI_BEETROOT_CAKE);
    ITEMS.register(MiniCakeType.MINI_GLOW_BERRY_CAKE.getId(), () -> ModItems.MINI_GLOW_BERRY_CAKE);
    ITEMS.register(
        MiniCakeType.MINI_SWEET_BERRY_CAKE.getId(), () -> ModItems.MINI_SWEET_BERRY_CAKE);
    ITEMS.register(MiniCakeType.MINI_CHOCOLATE_CAKE.getId(), () -> ModItems.MINI_CHOCOLATE_CAKE);
    ITEMS.register(MiniCakeType.MINI_MUSHROOM_CAKE.getId(), () -> ModItems.MINI_MUSHROOM_CAKE);
    ITEMS.register("mini_cake_mold", () -> ModItems.MINI_CAKE_MOLD);
    ITEMS.register("candy_mold", () -> ModItems.CANDY_MOLD);
    ITEMS.register("chocolate_mold", () -> ModItems.CHOCOLATE_MOLD);
    ITEMS.register("flour", () -> ModItems.FLOUR);
    ITEMS.register("butter_churn", () -> ModItems.BUTTER_CHURN);
    ITEMS.register("butter", () -> ModItems.BUTTER);
    ITEMS.register("chocolate", () -> ModItems.CHOCOLATE);
    ITEMS.register("cinnamon_stick", () -> ModItems.CINNAMON_STICK);
    ITEMS.register("cinnamon_knife", () -> ModItems.CINNAMON_KNIFE);
    ITEMS.register("vanilla_bean", () -> ModItems.VANILLA_BEAN);
    ITEMS.register("ginger", () -> ModItems.GINGER);
    ITEMS.register(CandyType.TEST_CANDY.getId(), () -> ModItems.TEST_CANDY);
    ITEMS.register(CandyType.TEST_CANDY_MYSTIC.getId(), () -> ModItems.TEST_CANDY_MYSTIC);
    ITEMS.register(CandyType.TEST_CANDY_CURSED.getId(), () -> ModItems.TEST_CANDY_CURSED);
    ITEMS.register(CandyType.APPLE_CANDY.getId(), () -> ModItems.APPLE_CANDY);
    ITEMS.register(CandyType.APPLE_CANDY_MYSTIC.getId(), () -> ModItems.APPLE_CANDY_MYSTIC);
    ITEMS.register(CandyType.APPLE_CANDY_CURSED.getId(), () -> ModItems.APPLE_CANDY_CURSED);
    ITEMS.register(CandyType.CHORUS_CANDY.getId(), () -> ModItems.CHORUS_CANDY);
    ITEMS.register(CandyType.CHORUS_CANDY_MYSTIC.getId(), () -> ModItems.CHORUS_CANDY_MYSTIC);
    ITEMS.register(CandyType.CHORUS_CANDY_CURSED.getId(), () -> ModItems.CHORUS_CANDY_CURSED);
    ITEMS.register(CandyType.CHOCOLATE_CANDY.getId(), () -> ModItems.CHOCOLATE_CANDY);
    ITEMS.register(CandyType.CHOCOLATE_CANDY_MYSTIC.getId(), () -> ModItems.CHOCOLATE_CANDY_MYSTIC);
    ITEMS.register(CandyType.CHOCOLATE_CANDY_CURSED.getId(), () -> ModItems.CHOCOLATE_CANDY_CURSED);
    ITEMS.register(CandyType.GLOW_BERRY_CANDY.getId(), () -> ModItems.GLOW_BERRY_CANDY);
    ITEMS.register(
        CandyType.GLOW_BERRY_CANDY_MYSTIC.getId(), () -> ModItems.GLOW_BERRY_CANDY_MYSTIC);
    ITEMS.register(
        CandyType.GLOW_BERRY_CANDY_CURSED.getId(), () -> ModItems.GLOW_BERRY_CANDY_CURSED);
    ITEMS.register(CandyType.GOLDEN_CANDY.getId(), () -> ModItems.GOLDEN_CANDY);
    ITEMS.register(CandyType.GOLDEN_CANDY_MYSTIC.getId(), () -> ModItems.GOLDEN_CANDY_MYSTIC);
    ITEMS.register(CandyType.GOLDEN_CANDY_CURSED.getId(), () -> ModItems.GOLDEN_CANDY_CURSED);
    ITEMS.register(CandyType.MELON_CANDY.getId(), () -> ModItems.MELON_CANDY);
    ITEMS.register(CandyType.MELON_CANDY_MYSTIC.getId(), () -> ModItems.MELON_CANDY_MYSTIC);
    ITEMS.register(CandyType.MELON_CANDY_CURSED.getId(), () -> ModItems.MELON_CANDY_CURSED);
    ITEMS.register(CandyType.PUMPKIN_CANDY.getId(), () -> ModItems.PUMPKIN_CANDY);
    ITEMS.register(CandyType.PUMPKIN_CANDY_MYSTIC.getId(), () -> ModItems.PUMPKIN_CANDY_MYSTIC);
    ITEMS.register(CandyType.PUMPKIN_CANDY_CURSED.getId(), () -> ModItems.PUMPKIN_CANDY_CURSED);
    ITEMS.register(CandyType.SWEET_BERRY_CANDY.getId(), () -> ModItems.SWEET_BERRY_CANDY);
    ITEMS.register(
        CandyType.SWEET_BERRY_CANDY_MYSTIC.getId(), () -> ModItems.SWEET_BERRY_CANDY_MYSTIC);
    ITEMS.register(
        CandyType.SWEET_BERRY_CANDY_CURSED.getId(), () -> ModItems.SWEET_BERRY_CANDY_CURSED);
  }

  private NeoForgeModItems() {}

  public static void register(final IEventBus eventBus) {
    ITEMS.register(eventBus);
    CREATIVE_MODE_TABS.register(eventBus);
  }
}
