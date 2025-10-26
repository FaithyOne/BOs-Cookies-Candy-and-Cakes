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

package de.markusbordihn.cookiescandyandcakes;

import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import de.markusbordihn.cookiescandyandcakes.tabs.ModCreativeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class CookiesCandyAndCakes {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @SuppressWarnings("java:S1118")
  public CookiesCandyAndCakes() {
    log.info("Initializing {} (Forge) ...", Constants.MOD_NAME);
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {

    private RegistryEvents() {}

    @SubscribeEvent
    public static void onRegister(final RegisterEvent event) {
      if (event.getRegistryKey().equals(Registries.ITEM)) {
        event.register(
            Registries.ITEM,
            helper -> {
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_cookie"),
                  ModItems.APPLE_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_cookie_mystic"),
                  ModItems.APPLE_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "apple_cookie_cursed"),
                  ModItems.APPLE_COOKIE_CURSED);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "carrot_cookie"),
                  ModItems.CARROT_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "carrot_cookie_mystic"),
                  ModItems.CARROT_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "carrot_cookie_cursed"),
                  ModItems.CARROT_COOKIE_CURSED);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "glow_berry_cookie"),
                  ModItems.GLOW_BERRY_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(
                      Constants.MOD_ID, "glow_berry_cookie_mystic"),
                  ModItems.GLOW_BERRY_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(
                      Constants.MOD_ID, "glow_berry_cookie_cursed"),
                  ModItems.GLOW_BERRY_COOKIE_CURSED);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_cookie"),
                  ModItems.MELON_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_cookie_mystic"),
                  ModItems.MELON_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "melon_cookie_cursed"),
                  ModItems.MELON_COOKIE_CURSED);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_cookie"),
                  ModItems.PUMPKIN_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_cookie_mystic"),
                  ModItems.PUMPKIN_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pumpkin_cookie_cursed"),
                  ModItems.PUMPKIN_COOKIE_CURSED);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sweet_berry_cookie"),
                  ModItems.SWEET_BERRY_COOKIE);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(
                      Constants.MOD_ID, "sweet_berry_cookie_mystic"),
                  ModItems.SWEET_BERRY_COOKIE_MYSTIC);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(
                      Constants.MOD_ID, "sweet_berry_cookie_cursed"),
                  ModItems.SWEET_BERRY_COOKIE_CURSED);
            });
      }

      if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
        event.register(
            Registries.CREATIVE_MODE_TAB,
            helper -> {
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cookies"),
                  ModCreativeTabs.COOKIES_TAB);
              helper.register(
                  ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "spooky_candy_crumb"),
                  ModCreativeTabs.SPOOKY_CANDY_CRUMB_TAB);
            });
      }
    }
  }
}
