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

package de.markusbordihn.cookiescandyandcakes.effect.cookie.client;

import de.markusbordihn.cookiescandyandcakes.effect.ClientEffectInterface;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.Vec3;

public class CreeperCrunchCookieCursedEffectClient implements ClientEffectInterface {

  private static final int CREEPER_SPAWN_DISTANCE = 8;
  private static final int CREEPER_SPAWN_TICK = 20;
  private static final int CREEPER_MOVE_DURATION = 120;
  private static final int CREEPER_EXPLODE_TICK = CREEPER_SPAWN_TICK + CREEPER_MOVE_DURATION;
  private static final int SOUND_INTERVAL = 20;

  private final List<FakeCreeper> fakeCreepers = new ArrayList<>();

  @Override
  public int getDuration() {
    return CREEPER_EXPLODE_TICK + 10;
  }

  @Override
  public void tick(LocalPlayer localPlayer, int elapsedTicks) {
    if (!(localPlayer.level() instanceof ClientLevel clientLevel)) {
      return;
    }

    if (elapsedTicks == CREEPER_SPAWN_TICK) {
      spawnFakeCreepers(clientLevel, localPlayer);
    } else if (elapsedTicks > CREEPER_SPAWN_TICK && elapsedTicks <= CREEPER_EXPLODE_TICK) {
      int progress = elapsedTicks - CREEPER_SPAWN_TICK;
      if (progress % SOUND_INTERVAL == 0) {
        playCreeperSound(clientLevel, localPlayer.position());
      }
      updateFakeCreepers(clientLevel, localPlayer);
    } else {
      explodeFakeCreepers(clientLevel);
    }
  }

  @Override
  public void onStart(LocalPlayer localPlayer) {
    fakeCreepers.clear();
  }

  @Override
  public void onEnd(LocalPlayer localPlayer) {
    if (localPlayer.level() instanceof ClientLevel clientLevel) {
      removeFakeCreepers(clientLevel);
    }
    fakeCreepers.clear();
  }

  private void spawnFakeCreepers(final ClientLevel level, final LocalPlayer player) {
    Vec3 playerPos = player.position();
    double angleStep = Math.PI / 2;

    for (int i = 0; i < 4; i++) {
      double angle = angleStep * i;
      double offsetX = Math.cos(angle) * CREEPER_SPAWN_DISTANCE;
      double offsetZ = Math.sin(angle) * CREEPER_SPAWN_DISTANCE;
      Creeper creeper = EntityType.CREEPER.create(level);
      if (creeper != null) {
        creeper.igniteForTicks(CREEPER_MOVE_DURATION);
        creeper.setPos(playerPos.x + offsetX, playerPos.y, playerPos.z + offsetZ);
        creeper.setYRot((float) Math.toDegrees(angle + Math.PI));
        creeper.yRotO = creeper.getYRot();
        creeper.setYHeadRot(creeper.getYRot());
        creeper.setNoGravity(true);
        creeper.setInvulnerable(true);
        creeper.setSilent(true);
        level.addEntity(creeper);
        fakeCreepers.add(new FakeCreeper(creeper, playerPos.x, playerPos.y, playerPos.z));
      }
    }
    playCreeperSound(level, playerPos);
  }

  private void playCreeperSound(final ClientLevel level, final Vec3 pos) {
    level.playLocalSound(
        pos.x, pos.y, pos.z, SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 2.0f, 1.0f, false);
  }

  private void updateFakeCreepers(final ClientLevel level, final LocalPlayer player) {
    Vec3 playerPos = player.position();

    for (FakeCreeper fakeCreeper : fakeCreepers) {
      Creeper creeper = fakeCreeper.creeper();
      if (creeper.isAlive()) {
        Vec3 creeperPos = creeper.position();
        Vec3 direction = playerPos.subtract(creeperPos).normalize().scale(0.05);
        Vec3 newPos = creeperPos.add(direction);

        creeper.setPos(newPos.x, newPos.y, newPos.z);
        creeper.tick();

        double dx = playerPos.x - newPos.x;
        double dz = playerPos.z - newPos.z;
        float yaw = (float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0f;
        creeper.setYRot(yaw);
        creeper.setYHeadRot(yaw);
      }
    }
  }

  private void explodeFakeCreepers(final ClientLevel level) {
    for (FakeCreeper fakeCreeper : fakeCreepers) {
      Creeper creeper = fakeCreeper.creeper();
      if (creeper.isAlive()) {
        Vec3 pos = creeper.position();

        for (int i = 0; i < 30; i++) {
          double offsetX = (level.random.nextDouble() - 0.5) * 3.0;
          double offsetY = level.random.nextDouble() * 3.0;
          double offsetZ = (level.random.nextDouble() - 0.5) * 3.0;

          level.addParticle(
              ParticleTypes.EXPLOSION,
              pos.x + offsetX,
              pos.y + offsetY,
              pos.z + offsetZ,
              0.0,
              0.0,
              0.0);

          level.addParticle(
              ParticleTypes.LARGE_SMOKE,
              pos.x + offsetX,
              pos.y + offsetY,
              pos.z + offsetZ,
              offsetX * 0.1,
              offsetY * 0.1,
              offsetZ * 0.1);
        }

        level.playLocalSound(
            pos.x,
            pos.y,
            pos.z,
            SoundEvents.GENERIC_EXPLODE.value(),
            SoundSource.HOSTILE,
            2.0f,
            1.0f,
            false);

        creeper.discard();
      }
    }
  }

  private void removeFakeCreepers(final ClientLevel level) {
    for (FakeCreeper fakeCreeper : fakeCreepers) {
      Creeper creeper = fakeCreeper.creeper();
      if (creeper.isAlive()) {
        creeper.discard();
      }
    }
  }

  private record FakeCreeper(Creeper creeper, double startX, double startY, double startZ) {}
}
