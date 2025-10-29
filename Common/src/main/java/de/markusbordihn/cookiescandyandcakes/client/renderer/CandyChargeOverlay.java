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

package de.markusbordihn.cookiescandyandcakes.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import de.markusbordihn.cookiescandyandcakes.item.BaseCandy;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CandyChargeOverlay implements LayeredDraw.Layer {

  private static final int BAR_WIDTH = 120;
  private static final int BAR_HEIGHT = 10;
  private static final int BAR_COLOR_BG = 0x60000000;
  private static final int BAR_COLOR_BG_BORDER = 0x90000000;
  private static final int BAR_COLOR_WEAK = 0xE0FFAA00;
  private static final int BAR_COLOR_WEAK_SHINE = 0x60FFDD88;
  private static final int BAR_COLOR_STRONG = 0xE0FFCC00;
  private static final int BAR_COLOR_STRONG_SHINE = 0x60FFFF88;
  private static final int TEXT_COLOR = 0xFFFFFFFF;
  private static final int TEXT_SHADOW_COLOR = 0x80000000;

  public static void renderCandyChargeBar(GuiGraphics guiGraphics, Minecraft minecraft) {
    if (minecraft.player == null || !minecraft.player.isUsingItem()) {
      return;
    }

    ItemStack activeStack = minecraft.player.getUseItem();
    if (!(activeStack.getItem() instanceof BaseCandy)) {
      return;
    }

    int usedTicks = activeStack.getUseDuration(minecraft.player) - minecraft.player.getUseItemRemainingTicks();
    if (usedTicks < BaseCandy.getShowBarMinTicks() || usedTicks >= BaseCandy.getEatStartTicks()) {
      return;
    }

    if (shouldEatDirectly(minecraft.player)) {
      return;
    }

    int x = (minecraft.getWindow().getGuiScaledWidth() - BAR_WIDTH) / 2;
    int y = minecraft.getWindow().getGuiScaledHeight() - 80;

    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();

    float progress;
    Component text;
    int barColor = BAR_COLOR_STRONG;
    int shineColor = BAR_COLOR_STRONG_SHINE;

    if (usedTicks >= BaseCandy.getPauseTicks()) {
      progress = 1.0F;
      text = Component.translatable("item.cookies_candy_and_cakes.candy.throw_strong");
    } else if (usedTicks >= BaseCandy.getThrowMaxTicks()) {
      int maxChargeTicks = BaseCandy.getThrowMaxTicks() - BaseCandy.getThrowMinTicks();
      progress = Math.min(1.0F, (float) (Math.min(usedTicks, BaseCandy.getThrowMaxTicks()) - BaseCandy.getThrowMinTicks()) / maxChargeTicks);
      text = Component.translatable("item.cookies_candy_and_cakes.candy.throw_strong");
    } else {
      int maxChargeTicks = BaseCandy.getThrowMaxTicks() - BaseCandy.getThrowMinTicks();
      progress = Math.min(1.0F, (float) (usedTicks - BaseCandy.getThrowMinTicks()) / maxChargeTicks);
      barColor = BAR_COLOR_WEAK;
      shineColor = BAR_COLOR_WEAK_SHINE;
      text = Component.translatable("item.cookies_candy_and_cakes.candy.throw_weak");
    }

    guiGraphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, BAR_COLOR_BG_BORDER);
    guiGraphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, BAR_COLOR_BG);

    int filledWidth = (int) (BAR_WIDTH * progress);
    if (filledWidth > 0) {
      guiGraphics.fill(x, y, x + filledWidth, y + BAR_HEIGHT, barColor);
      guiGraphics.fill(x, y, x + filledWidth, y + BAR_HEIGHT / 3, shineColor);
    }

    Font font = minecraft.font;
    int textX = x + (BAR_WIDTH - font.width(text)) / 2;
    int textY = y - font.lineHeight - 2;
    guiGraphics.drawString(font, text, textX + 1, textY + 1, TEXT_SHADOW_COLOR, false);
    guiGraphics.drawString(font, text, textX, textY, TEXT_COLOR, false);

    RenderSystem.disableBlend();
  }

  private static boolean shouldEatDirectly(net.minecraft.world.entity.player.Player player) {
    if (player.getXRot() < 45.0F) {
      return false;
    }

    Vec3 eyePos = player.getEyePosition();
    Vec3 lookVec = player.getLookAngle();
    Vec3 endPos = eyePos.add(lookVec.scale(2.5));

    BlockHitResult hitResult = player.level().clip(new ClipContext(
        eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

    return hitResult.getType() == HitResult.Type.BLOCK 
        && !player.level().getBlockState(hitResult.getBlockPos()).isAir();
  }

  @Override
  public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    renderCandyChargeBar(guiGraphics, Minecraft.getInstance());
  }
}
