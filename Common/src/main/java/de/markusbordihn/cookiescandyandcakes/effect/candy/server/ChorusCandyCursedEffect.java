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

package de.markusbordihn.cookiescandyandcakes.effect.candy.server;

import de.markusbordihn.cookiescandyandcakes.effect.ServerEffectInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class ChorusCandyCursedEffect implements ServerEffectInterface {

  private static final int MAX_TELEPORT_DISTANCE = 16;
  private static final int MAX_TELEPORT_ATTEMPTS = 16;
  private static final int EFFECT_DURATION = 200;
  private static final int TELEPORT_INTERVAL = 20;
  private static final Map<UUID, Vec3> START_POSITIONS = new HashMap<>();

  @Override
  public int getDuration() {
    return EFFECT_DURATION;
  }

  @Override
  public void tick(ServerPlayer player, int elapsedTicks) {
    if (elapsedTicks % TELEPORT_INTERVAL == 0 && player.level() instanceof ServerLevel serverLevel) {
      teleportRandomly(serverLevel, player);
    }
  }

  @Override
  public void onStart(ServerPlayer player) {
    START_POSITIONS.put(player.getUUID(), player.position());
  }

  @Override
  public void onEnd(ServerPlayer player) {
    Vec3 startPos = START_POSITIONS.remove(player.getUUID());
    if (startPos != null && player.level() instanceof ServerLevel serverLevel) {
      teleportTo(serverLevel, player, startPos.x, startPos.y, startPos.z);
      serverLevel.playSound(
          null,
          player.getX(),
          player.getY(),
          player.getZ(),
          SoundEvents.CHORUS_FRUIT_TELEPORT,
          SoundSource.PLAYERS,
          1.0F,
          1.0F);
    }
  }

  private void teleportRandomly(ServerLevel level, ServerPlayer player) {
    double originalX = player.getX();
    double originalY = player.getY();
    double originalZ = player.getZ();

    for (int attempt = 0; attempt < MAX_TELEPORT_ATTEMPTS; attempt++) {
      double deltaX = (level.random.nextDouble() - 0.5) * 2.0 * MAX_TELEPORT_DISTANCE;
      double deltaY = (level.random.nextInt(MAX_TELEPORT_DISTANCE) - MAX_TELEPORT_DISTANCE / 2);
      double deltaZ = (level.random.nextDouble() - 0.5) * 2.0 * MAX_TELEPORT_DISTANCE;

      double targetX = originalX + deltaX;
      double targetY = originalY + deltaY;
      double targetZ = originalZ + deltaZ;

      if (teleportTo(level, player, targetX, targetY, targetZ)) {
        level.playSound(
            null,
            originalX,
            originalY,
            originalZ,
            SoundEvents.CHORUS_FRUIT_TELEPORT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F);

        level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.CHORUS_FRUIT_TELEPORT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F);

        player.resetFallDistance();
        return;
      }
    }
  }

  private boolean teleportTo(ServerLevel level, ServerPlayer player, double x, double y, double z) {
    BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, y, z);

    while (mutablePos.getY() > level.getMinBuildHeight() 
        && level.getBlockState(mutablePos).isAir()) {
      mutablePos.move(0, -1, 0);
    }

    BlockPos targetPos = mutablePos.immutable();
    BlockPos abovePos = targetPos.above();
    BlockPos abovePos2 = abovePos.above();

    if (level.getBlockState(targetPos).isAir() 
        || !level.getBlockState(abovePos).isAir() 
        || !level.getBlockState(abovePos2).isAir()) {
      return false;
    }

    Vec3 targetVec = Vec3.atBottomCenterOf(abovePos);
    return player.randomTeleport(targetVec.x, targetVec.y, targetVec.z, true);
  }
}
