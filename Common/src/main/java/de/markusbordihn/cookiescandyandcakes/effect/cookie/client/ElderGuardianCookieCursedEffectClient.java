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
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;

public class ElderGuardianCookieCursedEffectClient implements ClientEffectInterface {

  private static final int SPAWN_TICK = 20;
  private static final int GAZE_DURATION = 200;
  private static final int DISSOLVE_TICK = SPAWN_TICK + GAZE_DURATION;
  private static final int TOTAL_DURATION = DISSOLVE_TICK + 40;
  private static final int PULSE_INTERVAL = 20;
  private static final int GUARDIAN_SPAWN_TICK = 60;
  private final List<Guardian> guardians = new ArrayList<>();
  private ElderGuardian elderGuardian;
  private Vec3 elderGuardianBasePos;
  private int breathingTick = 0;
  private int lastPulseTick = 0;

  @Override
  public int getDuration() {
    return TOTAL_DURATION;
  }

  @Override
  public void tick(LocalPlayer localPlayer, int elapsedTicks) {
    if (!(localPlayer.level() instanceof ClientLevel clientLevel)) {
      return;
    }

    if (elapsedTicks == SPAWN_TICK) {
      spawnElderGuardian(clientLevel, localPlayer);
    } else if (elapsedTicks == GUARDIAN_SPAWN_TICK) {
      spawnSideGuardians(clientLevel, localPlayer);
    } else if (elapsedTicks > SPAWN_TICK && elapsedTicks < DISSOLVE_TICK) {
      updateElderGuardian(clientLevel, localPlayer, elapsedTicks);
      breathingTick++;
    } else if (elapsedTicks == DISSOLVE_TICK) {
      dissolveElderGuardian(clientLevel);
      dissolveGuardians(clientLevel);
    }
  }

  @Override
  public void onStart(LocalPlayer localPlayer) {
    elderGuardian = null;
    guardians.clear();
    elderGuardianBasePos = null;
    breathingTick = 0;
    lastPulseTick = 0;
  }

  @Override
  public void onEnd(LocalPlayer localPlayer) {
    if (elderGuardian != null && elderGuardian.isAlive()) {
      elderGuardian.discard();
    }
    elderGuardian = null;

    for (Guardian guardian : guardians) {
      if (guardian != null && guardian.isAlive()) {
        guardian.discard();
      }
    }
    guardians.clear();
  }

  private void spawnElderGuardian(final ClientLevel level, final LocalPlayer player) {
    Vec3 lookDirection = player.getLookAngle();
    elderGuardianBasePos = player.position().add(lookDirection.scale(3.0)).add(0, 2.0, 0);

    elderGuardian = EntityType.ELDER_GUARDIAN.create(level);
    if (elderGuardian != null) {
      elderGuardian.setPos(elderGuardianBasePos.x, elderGuardianBasePos.y, elderGuardianBasePos.z);
      elderGuardian.setNoGravity(true);
      elderGuardian.setInvulnerable(true);
      elderGuardian.setSilent(true);

      float yaw = (float) Math.toDegrees(Math.atan2(-lookDirection.x, lookDirection.z));
      elderGuardian.setYRot(yaw);
      elderGuardian.yRotO = yaw;
      elderGuardian.setYHeadRot(yaw);

      level.addEntity(elderGuardian);

      level.playLocalSound(
          elderGuardianBasePos.x,
          elderGuardianBasePos.y,
          elderGuardianBasePos.z,
          SoundEvents.ELDER_GUARDIAN_CURSE,
          SoundSource.HOSTILE,
          1.5f,
          0.8f,
          false);

      spawnDarkWaterParticles(level, elderGuardianBasePos);
    }
  }

  private void spawnSideGuardians(ClientLevel level, LocalPlayer player) {
    if (elderGuardianBasePos == null) {
      return;
    }

    Vec3 lookDirection = player.getLookAngle();
    Vec3 rightDirection = new Vec3(-lookDirection.z, 0, lookDirection.x).normalize();

    Guardian leftGuardian = EntityType.GUARDIAN.create(level);
    if (leftGuardian != null) {
      Vec3 leftPos = elderGuardianBasePos.add(rightDirection.scale(-4.0)).add(0, 0.5, 0);
      leftGuardian.setPos(leftPos.x, leftPos.y, leftPos.z);
      leftGuardian.setNoGravity(true);
      leftGuardian.setInvulnerable(true);
      leftGuardian.setSilent(true);
      level.addEntity(leftGuardian);
      guardians.add(leftGuardian);
      spawnDarkWaterParticles(level, leftPos);
    }

    Guardian rightGuardian = EntityType.GUARDIAN.create(level);
    if (rightGuardian != null) {
      Vec3 rightPos = elderGuardianBasePos.add(rightDirection.scale(4.0)).add(0, 0.5, 0);
      rightGuardian.setPos(rightPos.x, rightPos.y, rightPos.z);
      rightGuardian.setNoGravity(true);
      rightGuardian.setInvulnerable(true);
      rightGuardian.setSilent(true);
      level.addEntity(rightGuardian);
      guardians.add(rightGuardian);
      spawnDarkWaterParticles(level, rightPos);
    }

    level.playLocalSound(
        elderGuardianBasePos.x,
        elderGuardianBasePos.y,
        elderGuardianBasePos.z,
        SoundEvents.GUARDIAN_AMBIENT,
        SoundSource.HOSTILE,
        0.8f,
        0.7f,
        false);
  }

  private void updateElderGuardian(
      final ClientLevel level, final LocalPlayer player, final int elapsedTicks) {
    if (elderGuardian != null && elderGuardian.isAlive() && elderGuardianBasePos != null) {
      double breathingOffset = Math.sin(breathingTick * 0.05) * 0.15;
      Vec3 newPos = elderGuardianBasePos.add(0, breathingOffset, 0);
      elderGuardian.setPos(newPos.x, newPos.y, newPos.z);

      Vec3 playerPos = player.getEyePosition();
      Vec3 direction = playerPos.subtract(newPos).normalize();
      float yaw = (float) Math.toDegrees(Math.atan2(-direction.x, direction.z));

      elderGuardian.setYRot(yaw);
      elderGuardian.setYHeadRot(yaw);

      if (player.tickCount % 10 == 0) {
        spawnDarkBubbleParticles(level, newPos);
      }

      if (elapsedTicks - lastPulseTick >= PULSE_INTERVAL) {
        level.playLocalSound(
            newPos.x,
            newPos.y,
            newPos.z,
            SoundEvents.ELDER_GUARDIAN_AMBIENT,
            SoundSource.HOSTILE,
            0.3f,
            0.7f,
            false);
        lastPulseTick = elapsedTicks;
      }
    }

    for (Guardian guardian : guardians) {
      if (guardian != null && guardian.isAlive()) {
        Vec3 guardianPos = guardian.position();
        Vec3 playerPos = player.getEyePosition();
        Vec3 direction = playerPos.subtract(guardianPos).normalize();
        float yaw = (float) Math.toDegrees(Math.atan2(-direction.x, direction.z));

        guardian.setYRot(yaw);
        guardian.setYHeadRot(yaw);
      }
    }
  }

  private void dissolveElderGuardian(final ClientLevel level) {
    if (elderGuardian != null && elderGuardian.isAlive()) {
      Vec3 pos = elderGuardian.position();

      for (int i = 0; i < 40; i++) {
        double offsetX = (level.random.nextDouble() - 0.5) * 2.5;
        double offsetY = level.random.nextDouble() * 2.5;
        double offsetZ = (level.random.nextDouble() - 0.5) * 2.5;

        level.addParticle(
            ParticleTypes.SMOKE,
            pos.x + offsetX,
            pos.y + offsetY,
            pos.z + offsetZ,
            (level.random.nextDouble() - 0.5) * 0.05,
            level.random.nextDouble() * 0.05,
            (level.random.nextDouble() - 0.5) * 0.05);

        level.addParticle(
            ParticleTypes.SQUID_INK,
            pos.x + offsetX,
            pos.y + offsetY,
            pos.z + offsetZ,
            (level.random.nextDouble() - 0.5) * 0.1,
            level.random.nextDouble() * 0.05,
            (level.random.nextDouble() - 0.5) * 0.1);
      }

      level.playLocalSound(
          pos.x,
          pos.y,
          pos.z,
          SoundEvents.ELDER_GUARDIAN_DEATH,
          SoundSource.HOSTILE,
          0.8f,
          0.8f,
          false);

      elderGuardian.discard();
    }
  }

  private void dissolveGuardians(final ClientLevel level) {
    for (Guardian guardian : guardians) {
      if (guardian != null && guardian.isAlive()) {
        Vec3 pos = guardian.position();

        for (int i = 0; i < 25; i++) {
          double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
          double offsetY = level.random.nextDouble() * 2.0;
          double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;

          level.addParticle(
              ParticleTypes.SMOKE,
              pos.x + offsetX,
              pos.y + offsetY,
              pos.z + offsetZ,
              (level.random.nextDouble() - 0.5) * 0.05,
              level.random.nextDouble() * 0.05,
              (level.random.nextDouble() - 0.5) * 0.05);

          level.addParticle(
              ParticleTypes.SQUID_INK,
              pos.x + offsetX,
              pos.y + offsetY,
              pos.z + offsetZ,
              (level.random.nextDouble() - 0.5) * 0.1,
              level.random.nextDouble() * 0.05,
              (level.random.nextDouble() - 0.5) * 0.1);
        }

        guardian.discard();
      }
    }
  }

  private void spawnDarkWaterParticles(final ClientLevel level, final Vec3 pos) {
    for (int i = 0; i < 25; i++) {
      double offsetX = (level.random.nextDouble() - 0.5) * 1.5;
      double offsetY = level.random.nextDouble() * 1.5;
      double offsetZ = (level.random.nextDouble() - 0.5) * 1.5;

      level.addParticle(
          ParticleTypes.SQUID_INK,
          pos.x + offsetX,
          pos.y + offsetY,
          pos.z + offsetZ,
          0.0,
          0.02,
          0.0);
    }
  }

  private void spawnDarkBubbleParticles(final ClientLevel level, final Vec3 pos) {
    for (int i = 0; i < 2; i++) {
      double offsetX = (level.random.nextDouble() - 0.5);
      double offsetY = level.random.nextDouble();
      double offsetZ = (level.random.nextDouble() - 0.5);

      level.addParticle(
          ParticleTypes.BUBBLE, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 0.0, 0.02, 0.0);

      if (level.random.nextFloat() < 0.3f) {
        level.addParticle(
            ParticleTypes.SMOKE, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 0.0, 0.01, 0.0);
      }
    }
  }
}
