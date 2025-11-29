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

package de.markusbordihn.cookiescandyandcakes.tabs;

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.registry.ModBlockItems;
import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTabs {
  public static final String COOKIES_TAB_ID = "cookies";
  public static final String SPECIAL_COOKIES_TAB_ID = "special_cookies";

  private ModCreativeTabs() {}

  public static CreativeModeTab.Builder createCookiesTab() {
    return CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
        .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".cookies"))
        .icon(() -> new ItemStack(ModItems.SWEET_BERRY_COOKIE))
        .displayItems(
            (parameters, output) -> {
              // Cookies
              output.accept(ModItems.APPLE_COOKIE);
              output.accept(ModItems.CARROT_COOKIE);
              output.accept(ModItems.CREEPER_CRUNCH_COOKIE);
              output.accept(ModItems.ELDER_GUARDIAN_COOKIE);
              output.accept(ModItems.GLOW_BERRY_COOKIE);
              output.accept(ModItems.MELON_COOKIE);
              output.accept(ModItems.PUMPKIN_COOKIE);
              output.accept(ModItems.SLIME_SUGAR_COOKIE);
              output.accept(ModItems.SWEET_BERRY_COOKIE);

              // Mini Cakes
              output.accept(ModItems.MINI_APPLE_CAKE);
              output.accept(ModItems.MINI_BEETROOT_CAKE);
              output.accept(ModItems.MINI_CARROT_CAKE);
              output.accept(ModItems.MINI_CHOCOLATE_CAKE);
              output.accept(ModItems.MINI_GLOW_BERRY_CAKE);
              output.accept(ModItems.MINI_MELON_CAKE);
              output.accept(ModItems.MINI_MUSHROOM_CAKE);
              output.accept(ModItems.MINI_PUMPKIN_CAKE);
              output.accept(ModItems.MINI_SWEET_BERRY_CAKE);

              // Candies
              output.accept(ModItems.APPLE_CANDY);
              output.accept(ModItems.CHOCOLATE_CANDY);
              output.accept(ModItems.CHORUS_CANDY);
              output.accept(ModItems.GLOW_BERRY_CANDY);
              output.accept(ModItems.GOLDEN_CANDY);
              output.accept(ModItems.MELON_CANDY);
              output.accept(ModItems.PUMPKIN_CANDY);
              output.accept(ModItems.SWEET_BERRY_CANDY);

              // Ingredients & Molds
              output.accept(ModItems.FLOUR);
              output.accept(ModItems.BUTTER);
              output.accept(ModItems.CHOCOLATE);
              output.accept(ModItems.MINI_CAKE_MOLD);
              output.accept(ModItems.CANDY_MOLD);
              output.accept(ModItems.CHOCOLATE_MOLD);

              // Cookie Jars
              output.accept(ModBlockItems.PUMPKIN_HEAD_COOKIE_JAR.get());
              output.accept(ModBlockItems.SHULKER_BOX_COOKIE_JAR.get());
              output.accept(ModBlockItems.SKELETON_HEAD_COOKIE_JAR.get());
              output.accept(ModBlockItems.TNT_COOKIE_JAR.get());
            });
  }

  public static CreativeModeTab.Builder createSpecialCookiesTab() {
    return CreativeModeTab.builder(CreativeModeTab.Row.TOP, 3)
        .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".special_cookies"))
        .icon(() -> new ItemStack(ModItems.APPLE_COOKIE_MYSTIC))
        .displayItems(
            (parameters, output) -> {
              output.accept(ModItems.APPLE_COOKIE_CURSED);
              output.accept(ModItems.APPLE_COOKIE_MYSTIC);
              output.accept(ModItems.CARROT_COOKIE_CURSED);
              output.accept(ModItems.CARROT_COOKIE_MYSTIC);
              output.accept(ModItems.CREEPER_CRUNCH_COOKIE_CURSED);
              output.accept(ModItems.CREEPER_CRUNCH_COOKIE_MYSTIC);
              output.accept(ModItems.ELDER_GUARDIAN_COOKIE_CURSED);
              output.accept(ModItems.ELDER_GUARDIAN_COOKIE_MYSTIC);
              output.accept(ModItems.GLOW_BERRY_COOKIE_CURSED);
              output.accept(ModItems.GLOW_BERRY_COOKIE_MYSTIC);
              output.accept(ModItems.MELON_COOKIE_CURSED);
              output.accept(ModItems.MELON_COOKIE_MYSTIC);
              output.accept(ModItems.PUMPKIN_COOKIE_CURSED);
              output.accept(ModItems.PUMPKIN_COOKIE_MYSTIC);
              output.accept(ModItems.SLIME_SUGAR_COOKIE_CURSED);
              output.accept(ModItems.SLIME_SUGAR_COOKIE_MYSTIC);
              output.accept(ModItems.SWEET_BERRY_COOKIE_CURSED);
              output.accept(ModItems.SWEET_BERRY_COOKIE_MYSTIC);

              output.accept(ModItems.APPLE_CANDY_CURSED);
              output.accept(ModItems.APPLE_CANDY_MYSTIC);
              output.accept(ModItems.CHOCOLATE_CANDY_CURSED);
              output.accept(ModItems.CHOCOLATE_CANDY_MYSTIC);
              output.accept(ModItems.CHORUS_CANDY_CURSED);
              output.accept(ModItems.CHORUS_CANDY_MYSTIC);
              output.accept(ModItems.GLOW_BERRY_CANDY_CURSED);
              output.accept(ModItems.GLOW_BERRY_CANDY_MYSTIC);
              output.accept(ModItems.GOLDEN_CANDY_CURSED);
              output.accept(ModItems.GOLDEN_CANDY_MYSTIC);
              output.accept(ModItems.MELON_CANDY_CURSED);
              output.accept(ModItems.MELON_CANDY_MYSTIC);
              output.accept(ModItems.PUMPKIN_CANDY_CURSED);
              output.accept(ModItems.PUMPKIN_CANDY_MYSTIC);
              output.accept(ModItems.SWEET_BERRY_CANDY_CURSED);
              output.accept(ModItems.SWEET_BERRY_CANDY_MYSTIC);
            });
  }
}
