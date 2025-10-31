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

package de.markusbordihn.cookiescandyandcakes.event;

import de.markusbordihn.cookiescandyandcakes.config.MonsterLootConfig;
import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MonsterLootHandler {

  private static final int REFRESH_INTERVAL = 100;
  private static List<Item> cachedWeightedItems = null;
  private static int callsSinceLastRefresh = 0;

  private MonsterLootHandler() {}

  public static boolean shouldDropSpecialCookies(final Entity entity) {
    if (!(entity instanceof Monster)) {
      return false;
    }

    float dropChance = MonsterLootConfig.globalDropChance / 100.0f;
    return entity.level().getRandom().nextFloat() < dropChance;
  }

  private static List<Item> buildWeightedItemList() {
    List<Item> weightedItems = new ArrayList<>();

    // Add cookies (only MYSTIC and CURSED)
    for (CookieType cookieType : CookieType.values()) {
      if (cookieType.getVariant() == CookieType.CookieVariant.MYSTIC
          || cookieType.getVariant() == CookieType.CookieVariant.CURSED) {
        int weight = MonsterLootConfig.getCookieDropWeight(cookieType);
        Item item = getCookieItem(cookieType);
        if (item != null) {
          for (int i = 0; i < weight; i++) {
            weightedItems.add(item);
          }
        }
      }
    }

    // Add candies (only MYSTIC and CURSED)
    for (CandyType candyType : CandyType.values()) {
      if (candyType.getVariant() == CandyType.CandyVariant.MYSTIC
          || candyType.getVariant() == CandyType.CandyVariant.CURSED) {
        int weight = MonsterLootConfig.getCandyDropWeight(candyType);
        Item item = getCandyItem(candyType);
        if (item != null) {
          for (int i = 0; i < weight; i++) {
            weightedItems.add(item);
          }
        }
      }
    }

    return weightedItems;
  }

  private static Item getCookieItem(final CookieType cookieType) {
    try {
      String fieldName = cookieType.name();
      Field field = ModItems.class.getDeclaredField(fieldName);
      return (Item) field.get(null);
    } catch (Exception e) {
      return null;
    }
  }

  private static Item getCandyItem(final CandyType candyType) {
    try {
      String fieldName = candyType.name();
      Field field = ModItems.class.getDeclaredField(fieldName);
      return (Item) field.get(null);
    } catch (Exception e) {
      return null;
    }
  }

  public static ItemStack getRandomSpecialCookie(final Entity entity) {
    if (cachedWeightedItems == null || ++callsSinceLastRefresh >= REFRESH_INTERVAL) {
      cachedWeightedItems = buildWeightedItemList();
      callsSinceLastRefresh = 0;
    }

    if (cachedWeightedItems.isEmpty()) {
      return ItemStack.EMPTY;
    }

    Item item =
        cachedWeightedItems.get(entity.level().getRandom().nextInt(cachedWeightedItems.size()));
    return new ItemStack(item);
  }

  public static ItemEntity createSpecialCookieDrop(final Entity entity) {
    return new ItemEntity(
        entity.level(),
        entity.getX(),
        entity.getY(),
        entity.getZ(),
        getRandomSpecialCookie(entity));
  }
}
