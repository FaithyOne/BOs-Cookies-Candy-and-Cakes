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

import de.markusbordihn.cookiescandyandcakes.client.ClientCookieData;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IdentifiableCookie {

  CookieType getCookieType();

  String getDescriptionId();

  default Component getUnidentifiedName() {
    if (ClientCookieData.hasIdentified(getCookieType())) {
      ChatFormatting nameColor = getCookieType().getVariant() == CookieType.CookieVariant.MYSTIC
          ? ChatFormatting.LIGHT_PURPLE
          : ChatFormatting.DARK_RED;
      return Component.translatable(getDescriptionId()).withStyle(nameColor);
    }
    return Component.translatable(getUnidentifiedKey())
        .withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withObfuscated(true));
  }

  default Component getIdentifiedTooltip() {
    if (ClientCookieData.hasIdentified(getCookieType())) {
      return Component.translatable(getDescriptionId() + ".desc")
          .withStyle(ChatFormatting.DARK_GRAY);
    }
    return Component.translatable(getUnidentifiedDescKey())
        .withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withObfuscated(true));
  }

  default void onConsume(final LivingEntity entity) {
    if (entity instanceof Player && entity.level().isClientSide) {
      ClientCookieData.markAsIdentified(getCookieType());
    }
  }

  default String getUnidentifiedKey() {
    return getCookieType().getVariant() == CookieType.CookieVariant.MYSTIC
        ? "item.cookies_candy_and_cakes.unidentified_mystic_cookie"
        : "item.cookies_candy_and_cakes.unidentified_cursed_cookie";
  }

  default String getUnidentifiedDescKey() {
    return getUnidentifiedKey() + ".desc";
  }
}
