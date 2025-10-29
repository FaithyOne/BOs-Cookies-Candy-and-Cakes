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

package de.markusbordihn.cookiescandyandcakes.entity;

import de.markusbordihn.cookiescandyandcakes.Constants;
import de.markusbordihn.cookiescandyandcakes.registry.ModEntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModEntityTypes {

  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
      DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

  public static final DeferredHolder<EntityType<?>, EntityType<ThrownCandy>> THROWN_CANDY =
      ENTITY_TYPES.register(
          "thrown_candy",
          () ->
              EntityType.Builder.<ThrownCandy>of(ThrownCandy::new, MobCategory.MISC)
                  .sized(0.25F, 0.25F)
                  .clientTrackingRange(4)
                  .updateInterval(10)
                  .build("thrown_candy"));

  static {
    ModEntityTypes.THROWN_CANDY = () -> THROWN_CANDY.get();
  }

  private NeoForgeModEntityTypes() {}

  public static void register(final IEventBus eventBus) {
    ENTITY_TYPES.register(eventBus);
  }
}