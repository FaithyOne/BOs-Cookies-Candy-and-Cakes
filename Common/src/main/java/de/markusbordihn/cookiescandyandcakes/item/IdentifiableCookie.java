package de.markusbordihn.cookiescandyandcakes.item;

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
      return Component.translatable(getDescriptionId());
    }
    return Component.translatable(getUnidentifiedKey())
        .withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withObfuscated(true));
  }

  default Component getIdentifiedTooltip() {
    if (ClientCookieData.hasIdentified(getCookieType())) {
      return Component.translatable(getDescriptionId() + ".desc");
    }
    return Component.translatable(getUnidentifiedDescKey())
        .withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withObfuscated(true));
  }

  default void onConsume(LivingEntity entity) {
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
