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

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistryManager {

  private ItemRegistryManager() {}

  @SubscribeEvent
  public static void register(RegisterEvent event) {
    if (event.getRegistryKey().equals(Registries.ITEM)) {
      event.register(
          Registries.ITEM,
          helper -> {
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.APPLE_COOKIE.getId()),
                ModItems.APPLE_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.APPLE_COOKIE_MYSTIC.getId()),
                ModItems.APPLE_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.APPLE_COOKIE_CURSED.getId()),
                ModItems.APPLE_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.CARROT_COOKIE.getId()),
                ModItems.CARROT_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.CARROT_COOKIE_MYSTIC.getId()),
                ModItems.CARROT_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.CARROT_COOKIE_CURSED.getId()),
                ModItems.CARROT_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.GLOW_BERRY_COOKIE.getId()),
                ModItems.GLOW_BERRY_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.GLOW_BERRY_COOKIE_MYSTIC.getId()),
                ModItems.GLOW_BERRY_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.GLOW_BERRY_COOKIE_CURSED.getId()),
                ModItems.GLOW_BERRY_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.MELON_COOKIE.getId()),
                ModItems.MELON_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.MELON_COOKIE_MYSTIC.getId()),
                ModItems.MELON_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.MELON_COOKIE_CURSED.getId()),
                ModItems.MELON_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.PUMPKIN_COOKIE.getId()),
                ModItems.PUMPKIN_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.PUMPKIN_COOKIE_MYSTIC.getId()),
                ModItems.PUMPKIN_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.PUMPKIN_COOKIE_CURSED.getId()),
                ModItems.PUMPKIN_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SWEET_BERRY_COOKIE.getId()),
                ModItems.SWEET_BERRY_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SWEET_BERRY_COOKIE_MYSTIC.getId()),
                ModItems.SWEET_BERRY_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SWEET_BERRY_COOKIE_CURSED.getId()),
                ModItems.SWEET_BERRY_COOKIE_CURSED);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SLIME_SUGAR_COOKIE.getId()),
                ModItems.SLIME_SUGAR_COOKIE);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SLIME_SUGAR_COOKIE_MYSTIC.getId()),
                ModItems.SLIME_SUGAR_COOKIE_MYSTIC);
            helper.register(
                ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID, CookieType.SLIME_SUGAR_COOKIE_CURSED.getId()),
                ModItems.SLIME_SUGAR_COOKIE_CURSED);
          });
    }
  }
}
