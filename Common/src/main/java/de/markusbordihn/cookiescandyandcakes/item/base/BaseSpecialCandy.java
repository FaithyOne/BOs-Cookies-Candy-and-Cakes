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

package de.markusbordihn.cookiescandyandcakes.item.base;

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.effect.candy.CandyClientEffectManager;
import de.markusbordihn.cookiescandyandcakes.effect.candy.CandyServerEffectManager;
import de.markusbordihn.cookiescandyandcakes.item.BaseCandy;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public abstract class BaseSpecialCandy extends BaseCandy
    implements BaseSpecialItem<IdentifiableCandy> {

  protected BaseSpecialCandy(final CandyType candyType) {
    super(candyType);
  }

  @Override
  public IdentifiableCandy getIdentifiable() {
    return (IdentifiableCandy) this;
  }

  @Override
  public void applyEffects(Level level, LivingEntity entity) {
    if (!level.isClientSide) {
      if (entity instanceof ServerPlayer serverPlayer) {
        CandyServerEffectManager.applyCandyEffect(serverPlayer, candyType);
      }
    } else {
      if (entity instanceof LocalPlayer localPlayer) {
        CandyClientEffectManager.applyCandyEffect(localPlayer, candyType);
      }
    }
  }

  @Override
  public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
    BaseSpecialItem.super.finishUsingItem(itemStack, level, entity);
    return super.finishUsingItem(itemStack, level, entity);
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    BaseSpecialItem.super.appendHoverText(itemStack, tooltipComponents, tooltipFlag);
  }

  @Override
  public Component getName(ItemStack itemStack) {
    return BaseSpecialItem.super.getName(itemStack);
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return true;
  }
}
