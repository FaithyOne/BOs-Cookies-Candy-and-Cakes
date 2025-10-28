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
import de.markusbordihn.cookiescandyandcakes.block.ForgeModBlocks;
import de.markusbordihn.cookiescandyandcakes.block.PumpkinHeadCookieJarBlock;
import de.markusbordihn.cookiescandyandcakes.block.SkeletonHeadCookieJarBlock;
import de.markusbordihn.cookiescandyandcakes.registry.ModBlockItems;
import java.util.function.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeModBlockItems {

  public static final DeferredRegister<Item> BLOCK_ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  private ForgeModBlockItems() {}

  public static void register(final IEventBus eventBus) {
    BLOCK_ITEMS.register(eventBus);
    ModBlockItems.PUMPKIN_HEAD_COOKIE_JAR =
        registerBlockItem(
            PumpkinHeadCookieJarBlock.ID,
            () ->
                new CookieJarItem(
                    ForgeModBlocks.PUMPKIN_HEAD_COOKIE_JAR.get(), new Item.Properties()));
    ModBlockItems.SKELETON_HEAD_COOKIE_JAR =
        registerBlockItem(
            SkeletonHeadCookieJarBlock.ID,
            () ->
                new CookieJarItem(
                    ForgeModBlocks.SKELETON_HEAD_COOKIE_JAR.get(), new Item.Properties()));
  }

  private static Supplier<BlockItem> registerBlockItem(
      final String name, final Supplier<BlockItem> blockItemSupplier) {
    return BLOCK_ITEMS.register(name, blockItemSupplier);
  }
}
