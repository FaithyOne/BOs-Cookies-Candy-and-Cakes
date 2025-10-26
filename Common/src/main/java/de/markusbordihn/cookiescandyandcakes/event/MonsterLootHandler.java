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

import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MonsterLootHandler {
  private static final float DROP_CHANCE = 0.25f;

  private static final Item[] CANDY_CRUMB_ITEMS = {
    ModItems.APPLE_COOKIE_MYSTIC,
    ModItems.APPLE_COOKIE_CURSED,
    ModItems.CARROT_COOKIE_MYSTIC,
    ModItems.CARROT_COOKIE_CURSED,
    ModItems.GLOW_BERRY_COOKIE_MYSTIC,
    ModItems.GLOW_BERRY_COOKIE_CURSED,
    ModItems.MELON_COOKIE_MYSTIC,
    ModItems.MELON_COOKIE_CURSED,
    ModItems.PUMPKIN_COOKIE_MYSTIC,
    ModItems.PUMPKIN_COOKIE_CURSED,
    ModItems.SWEET_BERRY_COOKIE_MYSTIC,
    ModItems.SWEET_BERRY_COOKIE_CURSED
  };

  private MonsterLootHandler() {}

  public static boolean shouldDropCandyCrumbs(Entity entity) {
    return entity instanceof Monster && entity.level().getRandom().nextFloat() < DROP_CHANCE;
  }

  public static ItemStack getRandomCandyCrumb(Entity entity) {
    Item item = CANDY_CRUMB_ITEMS[entity.level().getRandom().nextInt(CANDY_CRUMB_ITEMS.length)];
    return new ItemStack(item);
  }

  public static ItemEntity createCandyCrumbDrop(Entity entity) {
    return new ItemEntity(
        entity.level(), entity.getX(), entity.getY(), entity.getZ(), getRandomCandyCrumb(entity));
  }
}
