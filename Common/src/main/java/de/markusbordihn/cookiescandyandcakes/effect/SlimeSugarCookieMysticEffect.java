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

package de.markusbordihn.cookiescandyandcakes.effect;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class SlimeSugarCookieMysticEffect implements CookieEffectInterface {

  private static final int SLIMEBALL_DROP_INTERVAL = 40;
  private static final int SOUND_INTERVAL = 20;
  private int lastSoundTick = 0;

  @Override
  public int getDuration() {
    return CookieType.SLIME_SUGAR_COOKIE_MYSTIC.getEffectDuration();
  }

  @Override
  public void tick(ServerPlayer serverPlayer, int elapsedTicks) {
    if (serverPlayer.level() instanceof ServerLevel serverLevel) {
      Vec3 deltaMovement = serverPlayer.getDeltaMovement();
      if (deltaMovement.lengthSqr() > 0.01) {
        spawnSlimeParticles(serverLevel, serverPlayer);

        if (elapsedTicks - lastSoundTick >= SOUND_INTERVAL) {
          serverLevel.playSound(
              null,
              serverPlayer.getX(),
              serverPlayer.getY(),
              serverPlayer.getZ(),
              SoundEvents.SLIME_BLOCK_STEP,
              SoundSource.PLAYERS,
              0.5f,
              1.0f + (serverLevel.random.nextFloat() - 0.5f) * 0.2f);
          lastSoundTick = elapsedTicks;
        }
      }

      if (elapsedTicks > 0 && elapsedTicks % SLIMEBALL_DROP_INTERVAL == 0) {
        dropSlimeballBehindPlayer(serverLevel, serverPlayer, deltaMovement);
      }
    }
  }

  @Override
  public void onStart(ServerPlayer serverPlayer) {
    lastSoundTick = 0;
  }

  @Override
  public void onEnd(ServerPlayer serverPlayer) {}

  private void spawnSlimeParticles(ServerLevel level, ServerPlayer player) {
    for (int i = 0; i < 3; i++) {
      double offsetX = (level.random.nextDouble() - 0.5) * 0.5;
      double offsetY = level.random.nextDouble() * 0.3;
      double offsetZ = (level.random.nextDouble() - 0.5) * 0.5;

      level.sendParticles(
          ParticleTypes.ITEM_SLIME,
          player.getX() + offsetX,
          player.getY() + offsetY,
          player.getZ() + offsetZ,
          1,
          0.0,
          0.0,
          0.0,
          0.0);
    }
  }

  private void dropSlimeballBehindPlayer(
      ServerLevel serverLevel, ServerPlayer serverPlayer, Vec3 deltaMovement) {
    Vec3 dropPosition;

    if (deltaMovement.lengthSqr() > 0.01) {
      Vec3 normalizedMovement = deltaMovement.normalize();
      dropPosition =
          new Vec3(
              serverPlayer.getX() - normalizedMovement.x * 1.0,
              serverPlayer.getY(),
              serverPlayer.getZ() - normalizedMovement.z * 1.0);
    } else {
      Vec3 lookDirection = serverPlayer.getLookAngle();
      dropPosition =
          new Vec3(
              serverPlayer.getX() - lookDirection.x * 1.0,
              serverPlayer.getY(),
              serverPlayer.getZ() - lookDirection.z * 1.0);
    }

    ItemStack slimeball = new ItemStack(Items.SLIME_BALL, 1);
    ItemEntity itemEntity =
        new ItemEntity(serverLevel, dropPosition.x, dropPosition.y, dropPosition.z, slimeball);
    itemEntity.setDefaultPickUpDelay();
    serverLevel.addFreshEntity(itemEntity);
  }
}
