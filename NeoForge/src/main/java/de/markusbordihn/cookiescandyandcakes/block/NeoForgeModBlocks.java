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

package de.markusbordihn.cookiescandyandcakes.block;

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModBlocks {

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(Registries.BLOCK, Constants.MOD_ID);

  public static final DeferredHolder<Block, Block> PUMPKIN_HEAD_COOKIE_JAR =
      BLOCKS.register(PumpkinHeadCookieJarBlock.ID, () -> ModBlocks.PUMPKIN_HEAD_COOKIE_JAR);

  public static final DeferredHolder<Block, Block> SHULKER_BOX_COOKIE_JAR =
      BLOCKS.register(ShulkerBoxCookieJarBlock.ID, () -> ModBlocks.SHULKER_BOX_COOKIE_JAR);

  public static final DeferredHolder<Block, Block> SKELETON_HEAD_COOKIE_JAR =
      BLOCKS.register(SkeletonHeadCookieJarBlock.ID, () -> ModBlocks.SKELETON_HEAD_COOKIE_JAR);

  public static final DeferredHolder<Block, Block> TNT_COOKIE_JAR =
      BLOCKS.register(TntCookieJarBlock.ID, () -> ModBlocks.TNT_COOKIE_JAR);

  public static final DeferredHolder<Block, Block> VANILLA_BEAN_CROP =
      BLOCKS.register("vanilla_bean_crop", () -> ModBlocks.VANILLA_BEAN_CROP);

  public static final DeferredHolder<Block, Block> GINGER_CROP =
      BLOCKS.register("ginger_crop", () -> ModBlocks.GINGER_CROP);

  public static final DeferredHolder<Block, Block> GINGERBREAD_BLOCK =
      BLOCKS.register("gingerbread_block", () -> ModBlocks.GINGERBREAD_BLOCK);

  private NeoForgeModBlocks() {}

  public static void register(final IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
