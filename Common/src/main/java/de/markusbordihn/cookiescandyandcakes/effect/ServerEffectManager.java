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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerPlayer;

public class ServerEffectManager {

  private final Map<UUID, ActiveEffect> activeEffects = new ConcurrentHashMap<>();

  public void applyEffect(final ServerPlayer player, final ServerEffectInterface effect) {
    if (player == null || effect == null) {
      return;
    }

    // Remove any existing effect first to clean up properly
    UUID playerUUID = player.getUUID();
    ActiveEffect existingEffect = activeEffects.get(playerUUID);
    if (existingEffect != null) {
      existingEffect.effect.onEnd(player);
    }

    activeEffects.put(playerUUID, new ActiveEffect(effect, 0, effect.getDuration()));
    effect.onStart(player);
  }

  public void tick(final ServerPlayer player) {
    if (player == null) {
      return;
    }

    // Tick active effect
    UUID playerUUID = player.getUUID();
    ActiveEffect activeEffect = activeEffects.get(playerUUID);
    if (activeEffect != null) {
      activeEffect.tick(player);
      if (activeEffect.isExpired()) {
        activeEffect.effect.onEnd(player);
        activeEffects.remove(playerUUID);
      }
    }
  }

  public void tickAll(final Iterable<ServerPlayer> players) {
    for (ServerPlayer player : players) {
      tick(player);
    }
  }

  public void removeEffect(final ServerPlayer player) {
    if (player != null) {
      UUID playerUUID = player.getUUID();
      ActiveEffect activeEffect = activeEffects.remove(playerUUID);
      if (activeEffect != null) {
        activeEffect.effect.onEnd(player);
      }
    }
  }

  public void clearAll() {
    activeEffects.clear();
  }

  public boolean hasActiveEffect(final ServerPlayer player) {
    return player != null && activeEffects.containsKey(player.getUUID());
  }

  static class ActiveEffect {
    private final ServerEffectInterface effect;
    private final int duration;
    private int elapsedTicks;

    public ActiveEffect(
        final ServerEffectInterface effect, final int elapsedTicks, final int duration) {
      this.effect = effect;
      this.elapsedTicks = elapsedTicks;
      this.duration = duration;
    }

    public void tick(final ServerPlayer player) {
      effect.tick(player, elapsedTicks);
      elapsedTicks++;
    }

    public boolean isExpired() {
      return elapsedTicks >= duration;
    }
  }
}
