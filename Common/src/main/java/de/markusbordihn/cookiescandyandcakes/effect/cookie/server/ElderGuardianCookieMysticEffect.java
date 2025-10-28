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

package de.markusbordihn.cookiescandyandcakes.effect.cookie.server;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectInterface;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class ElderGuardianCookieMysticEffect implements ServerEffectInterface {

  private static final int DROP_TICK = 220;

  @Override
  public int getDuration() {
    return CookieType.ELDER_GUARDIAN_COOKIE_MYSTIC.getEffectDuration();
  }

  @Override
  public void tick(ServerPlayer serverPlayer, int elapsedTicks) {
    if (elapsedTicks == DROP_TICK && serverPlayer.level() instanceof ServerLevel serverLevel) {
      dropAquamarineShard(serverLevel, serverPlayer);
    }
  }

  @Override
  public void onStart(ServerPlayer serverPlayer) {}

  @Override
  public void onEnd(ServerPlayer serverPlayer) {}

  private void dropAquamarineShard(final ServerLevel serverLevel, final ServerPlayer serverPlayer) {
    Vec3 playerPos = serverPlayer.position();
    ItemStack aquamarineShard = new ItemStack(Items.PRISMARINE_SHARD, 1);
    ItemEntity itemEntity =
        new ItemEntity(serverLevel, playerPos.x, playerPos.y + 0.5, playerPos.z, aquamarineShard);
    itemEntity.setNoPickUpDelay();
    serverLevel.addFreshEntity(itemEntity);
  }
}
