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

package de.markusbordihn.cookiescandyandcakes.data.cookiejar;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public record CookieJarData(NonNullList<ItemStack> items) {

  public static final int CONTAINER_SIZE = 27;

  public static CookieJarData fromItemStack(ItemStack stack) {
    ItemContainerContents contents =
        stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    contents.copyInto(items);
    return new CookieJarData(items);
  }

  public void saveToItemStack(ItemStack stack) {
    if (!isEmpty()) {
      stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    } else {
      stack.remove(DataComponents.CONTAINER);
    }
  }

  public boolean isEmpty() {
    for (ItemStack item : items) {
      if (!item.isEmpty()) {
        return false;
      }
    }
    return true;
  }
}
