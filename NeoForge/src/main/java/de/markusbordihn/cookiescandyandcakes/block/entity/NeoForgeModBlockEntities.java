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

package de.markusbordihn.cookiescandyandcakes.block.entity;

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.block.NeoForgeModBlocks;
import de.markusbordihn.cookiescandyandcakes.block.PumpkinHeadCookieJarBlock;
import de.markusbordihn.cookiescandyandcakes.registry.ModBlockEntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public class NeoForgeModBlockEntities {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
      DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CookieJarBlockEntity>>
      COOKIE_JAR_BLOCK_ENTITY =
          BLOCK_ENTITY_TYPES.register(
              PumpkinHeadCookieJarBlock.ID,
              () ->
                  BlockEntityType.Builder.of(
                          CookieJarBlockEntity::new,
                          NeoForgeModBlocks.PUMPKIN_HEAD_COOKIE_JAR.get(),
                          NeoForgeModBlocks.SHULKER_BOX_COOKIE_JAR.get(),
                          NeoForgeModBlocks.SKELETON_HEAD_COOKIE_JAR.get(),
                          NeoForgeModBlocks.TNT_COOKIE_JAR.get())
                      .build(null));

  private NeoForgeModBlockEntities() {}

  public static void register(final IEventBus eventBus) {
    BLOCK_ENTITY_TYPES.register(eventBus);

    eventBus.addListener(
        (RegisterEvent event) -> {
          if (event.getRegistryKey().equals(Registries.BLOCK_ENTITY_TYPE)) {
            ModBlockEntityTypes.setCookieJar(COOKIE_JAR_BLOCK_ENTITY.get());
          }
        });
  }
}
