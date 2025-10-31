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
import de.markusbordihn.cookiescandyandcakes.data.loot.ItemWeight;
import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MonsterLootHandler {

  private static final ItemWeight[] SPECIAL_COOKIE_ITEMS_WITH_WEIGHTS = {
    new ItemWeight(
        ModItems.APPLE_COOKIE_MYSTIC, () -> MonsterLootConfig.appleCookieMysticDropWeight),
    new ItemWeight(
        ModItems.APPLE_COOKIE_CURSED, () -> MonsterLootConfig.appleCookieCursedDropWeight),
    new ItemWeight(
        ModItems.CARROT_COOKIE_MYSTIC, () -> MonsterLootConfig.carrotCookieMysticDropWeight),
    new ItemWeight(
        ModItems.CARROT_COOKIE_CURSED, () -> MonsterLootConfig.carrotCookieCursedDropWeight),
    new ItemWeight(
        ModItems.GLOW_BERRY_COOKIE_MYSTIC, () -> MonsterLootConfig.glowBerryCookieMysticDropWeight),
    new ItemWeight(
        ModItems.GLOW_BERRY_COOKIE_CURSED, () -> MonsterLootConfig.glowBerryCookieCursedDropWeight),
    new ItemWeight(
        ModItems.MELON_COOKIE_MYSTIC, () -> MonsterLootConfig.melonCookieMysticDropWeight),
    new ItemWeight(
        ModItems.MELON_COOKIE_CURSED, () -> MonsterLootConfig.melonCookieCursedDropWeight),
    new ItemWeight(
        ModItems.PUMPKIN_COOKIE_MYSTIC, () -> MonsterLootConfig.pumpkinCookieMysticDropWeight),
    new ItemWeight(
        ModItems.PUMPKIN_COOKIE_CURSED, () -> MonsterLootConfig.pumpkinCookieCursedDropWeight),
    new ItemWeight(
        ModItems.SWEET_BERRY_COOKIE_MYSTIC,
        () -> MonsterLootConfig.sweetBerryCookieMysticDropWeight),
    new ItemWeight(
        ModItems.SWEET_BERRY_COOKIE_CURSED,
        () -> MonsterLootConfig.sweetBerryCookieCursedDropWeight),
    new ItemWeight(
        ModItems.SLIME_SUGAR_COOKIE_MYSTIC,
        () -> MonsterLootConfig.slimeSugarCookieMysticDropWeight),
    new ItemWeight(
        ModItems.SLIME_SUGAR_COOKIE_CURSED,
        () -> MonsterLootConfig.slimeSugarCookieCursedDropWeight),
    new ItemWeight(
        ModItems.CREEPER_CRUNCH_COOKIE_MYSTIC,
        () -> MonsterLootConfig.creeperCrunchCookieMysticDropWeight),
    new ItemWeight(
        ModItems.CREEPER_CRUNCH_COOKIE_CURSED,
        () -> MonsterLootConfig.creeperCrunchCookieCursedDropWeight),
    new ItemWeight(
        ModItems.ELDER_GUARDIAN_COOKIE_MYSTIC,
        () -> MonsterLootConfig.elderGuardianCookieMysticDropWeight),
    new ItemWeight(
        ModItems.ELDER_GUARDIAN_COOKIE_CURSED,
        () -> MonsterLootConfig.elderGuardianCookieCursedDropWeight)
  };

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
    for (ItemWeight itemWeight : SPECIAL_COOKIE_ITEMS_WITH_WEIGHTS) {
      int weight = itemWeight.weightSupplier().getAsInt();
      for (int i = 0; i < weight; i++) {
        weightedItems.add(itemWeight.item());
      }
    }
    return weightedItems;
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
