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

package de.markusbordihn.cookiescandyandcakes.item;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class BaseCookie extends Item {

  private static final int STACK_SIZE = 64;

  protected final CookieType cookieType;

  protected BaseCookie(CookieType cookieType) {
    super(new Item.Properties().food(buildFoodProperties(cookieType)).stacksTo(STACK_SIZE));
    this.cookieType = cookieType;
  }

  protected static FoodProperties buildFoodProperties(CookieType cookieType) {
    FoodProperties.Builder builder =
        new FoodProperties.Builder().nutrition(cookieType.getNutrition()).fast();
    if (cookieType.hasEffect()) {
      builder.effect(
          new MobEffectInstance(
              cookieType.getEffect(), cookieType.getEffectDuration(), cookieType.getAmplifier()),
          cookieType.getEffectChance());
    }
    return builder.build();
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    tooltipComponents.add(
        Component.translatable(this.getDescriptionId() + ".desc")
            .withStyle(net.minecraft.ChatFormatting.DARK_GRAY));
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return cookieType.getVariant() == CookieType.CookieVariant.MYSTIC;
  }
}
