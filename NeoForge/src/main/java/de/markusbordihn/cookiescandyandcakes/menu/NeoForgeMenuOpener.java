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

package de.markusbordihn.cookiescandyandcakes.menu;

import de.markusbordihn.cookiescandyandcakes.block.entity.CookieJarBlockEntity;
import de.markusbordihn.cookiescandyandcakes.data.cookiejar.CookieJarData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;

public class NeoForgeMenuOpener implements MenuManager.MenuOpener {

  @Override
  public void openCookieJarBlockEntityMenu(ServerPlayer player, CookieJarBlockEntity blockEntity) {
    player.openMenu(blockEntity, blockEntity.getBlockPos());
  }

  @Override
  public void openCookieJarItemMenu(
      ServerPlayer player, ItemStack itemStack, InteractionHand hand) {

    CookieJarData data = CookieJarData.fromItemStack(itemStack);
    NonNullList<ItemStack> items = data.items();

    player.openMenu(
        new SimpleMenuProvider(
            (containerId, playerInventory, p) -> {
              net.minecraft.world.SimpleContainer container =
                  new net.minecraft.world.SimpleContainer(items.toArray(new ItemStack[0]));
              CookieJarMenu menu = new CookieJarMenu(containerId, playerInventory, container);

              menu.setOnCloseCallback(
                  () -> {
                    NonNullList<ItemStack> updatedItems =
                        NonNullList.withSize(CookieJarData.CONTAINER_SIZE, ItemStack.EMPTY);
                    for (int i = 0; i < menu.getContainer().getContainerSize(); i++) {
                      updatedItems.set(i, menu.getContainer().getItem(i));
                    }
                    CookieJarData updatedData = new CookieJarData(updatedItems);
                    updatedData.saveToItemStack(itemStack);
                  });

              return menu;
            },
            Component.translatable("container.cookies_candy_and_cakes.cookie_jar")));
  }
}
