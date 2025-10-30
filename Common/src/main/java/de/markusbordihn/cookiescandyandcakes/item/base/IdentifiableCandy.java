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

import de.markusbordihn.cookiescandyandcakes.client.ClientCandyData;
import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import net.minecraft.ChatFormatting;

public interface IdentifiableCandy extends IdentifiableItem<CandyType> {

  CandyType getCandyType();

  @Override
  default CandyType getItemType() {
    return getCandyType();
  }

  @Override
  default boolean hasIdentified(CandyType type) {
    return ClientCandyData.hasIdentified(type);
  }

  @Override
  default void markAsIdentified(CandyType type) {
    ClientCandyData.markAsIdentified(type);
  }

  @Override
  default ChatFormatting getVariantColor(CandyType type) {
    return type.getVariant() == CandyType.CandyVariant.MYSTIC
        ? ChatFormatting.LIGHT_PURPLE
        : ChatFormatting.DARK_RED;
  }

  @Override
  default String getUnidentifiedBaseKey() {
    return getCandyType().getVariant() == CandyType.CandyVariant.MYSTIC
        ? "item.cookies_candy_and_cakes.unidentified_mystic_candy"
        : "item.cookies_candy_and_cakes.unidentified_cursed_candy";
  }
}
