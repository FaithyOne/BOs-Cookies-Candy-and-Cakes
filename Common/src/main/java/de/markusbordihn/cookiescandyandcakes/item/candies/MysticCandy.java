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

package de.markusbordihn.cookiescandyandcakes.item.candies;

import de.markusbordihn.cookiescandyandcakes.client.ClientCandyData;
import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.item.BaseCandy;
import de.markusbordihn.cookiescandyandcakes.item.base.IdentifiableCandy;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MysticCandy extends BaseCandy implements IdentifiableCandy {

  public MysticCandy(final CandyType candyType) {
    super(candyType);
  }

  @Override
  public CandyType getCandyType() {
    return candyType;
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
    if (entity instanceof Player && entity.level().isClientSide) {
      ClientCandyData.markAsIdentified(candyType);
    }
    return super.finishUsingItem(stack, level, entity);
  }

  @Override
  public void appendHoverText(
      ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
    if (!ClientCandyData.hasIdentified(candyType)) {
      tooltipComponents.add(
          Component.translatable("item.cookies_candy_and_cakes.unidentified_mystic_candy")
              .withStyle(net.minecraft.ChatFormatting.LIGHT_PURPLE));
      tooltipComponents.add(
          Component.translatable("item.cookies_candy_and_cakes.unidentified_mystic_candy.desc")
              .withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
    } else {
      super.appendHoverText(stack, context, tooltipComponents, flag);
    }
  }

  @Override
  public Component getName(ItemStack stack) {
    if (!ClientCandyData.hasIdentified(candyType)) {
      return Component.translatable("item.cookies_candy_and_cakes.unidentified_mystic_candy");
    }
    return super.getName(stack);
  }
}
