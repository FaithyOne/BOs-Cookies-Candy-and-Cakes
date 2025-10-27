package de.markusbordihn.cookiescandyandcakes.menu;

import de.markusbordihn.cookiescandyandcakes.block.entity.CookieJarBlockEntity;
import de.markusbordihn.cookiescandyandcakes.data.cookiejar.CookieJarData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;

public class FabricMenuOpener implements MenuManager.MenuOpener {

  @Override
  public void openCookieJarBlockEntityMenu(ServerPlayer player, CookieJarBlockEntity blockEntity) {
    player.openMenu(blockEntity);
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
