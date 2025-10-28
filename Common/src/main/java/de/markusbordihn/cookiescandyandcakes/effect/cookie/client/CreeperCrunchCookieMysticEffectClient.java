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

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import de.markusbordihn.cookiescandyandcakes.effect.ClientEffectInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class CreeperCrunchCookieMysticEffectClient implements ClientEffectInterface {

  private static final int PARTICLE_INTERVAL = 5;

  @Override
  public int getDuration() {
    return CookieType.CREEPER_CRUNCH_COOKIE_MYSTIC.getEffectDuration();
  }

  @Override
  public void tick(LocalPlayer localPlayer, int elapsedTicks) {
    if (localPlayer.level() instanceof ClientLevel clientLevel) {
      if (elapsedTicks % PARTICLE_INTERVAL == 0) {
        spawnCreeperParticles(clientLevel, localPlayer);
      }
    }
  }

  @Override
  public void onStart(LocalPlayer localPlayer) {
    if (localPlayer.level() instanceof ClientLevel clientLevel) {
      clientLevel.playLocalSound(
          localPlayer.getX(),
          localPlayer.getY(),
          localPlayer.getZ(),
          SoundEvents.CREEPER_PRIMED,
          SoundSource.PLAYERS,
          1.0f,
          1.5f,
          false);
    }
  }

  @Override
  public void onEnd(LocalPlayer localPlayer) {}

  private void spawnCreeperParticles(ClientLevel level, LocalPlayer player) {
    for (int i = 0; i < 3; i++) {
      double offsetX = (level.random.nextDouble() - 0.5) * 0.8;
      double offsetY = level.random.nextDouble() * 1.2;
      double offsetZ = (level.random.nextDouble() - 0.5) * 0.8;

      level.addParticle(
          ParticleTypes.ELECTRIC_SPARK,
          player.getX() + offsetX,
          player.getY() + offsetY,
          player.getZ() + offsetZ,
          0.0,
          0.0,
          0.0);

      level.addParticle(
          ParticleTypes.SMOKE,
          player.getX() + offsetX,
          player.getY() + offsetY,
          player.getZ() + offsetZ,
          0.0,
          0.02,
          0.0);
    }
  }
}
