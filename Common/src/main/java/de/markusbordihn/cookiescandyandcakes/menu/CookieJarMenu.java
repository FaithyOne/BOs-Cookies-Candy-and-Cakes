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

import de.markusbordihn.cookiescandyandcakes.data.cookiejar.CookieJarData;
import de.markusbordihn.cookiescandyandcakes.registry.ModMenuTypes;
import de.markusbordihn.cookiescandyandcakes.registry.ModTags;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CookieJarMenu extends AbstractContainerMenu {

  private final Container container;
  private Runnable onCloseCallback;

  public CookieJarMenu(int containerId, Inventory playerInventory) {
    this(containerId, playerInventory, new SimpleContainer(CookieJarData.CONTAINER_SIZE));
  }

  public CookieJarMenu(
      int containerId, Inventory playerInventory, RegistryFriendlyByteBuf ignoredBuf) {
    this(containerId, playerInventory, new SimpleContainer(CookieJarData.CONTAINER_SIZE));
  }

  public CookieJarMenu(int containerId, Inventory playerInventory, Container container) {
    super(ModMenuTypes.getCookieJar(), containerId);
    this.container = container;
    container.startOpen(playerInventory.player);

    int startX = 8;
    int startY = 18;

    // Container slots
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        addSlot(new CookieJarSlot(container, col + row * 9, startX + col * 18, startY + row * 18));
      }
    }

    // Player inventory
    int playerInventoryStartY = 135;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        addSlot(
            new Slot(
                playerInventory,
                col + row * 9 + 9,
                startX + col * 18,
                playerInventoryStartY + row * 18));
      }
    }

    // Hotbar
    int hotbarY = 193;
    for (int col = 0; col < 9; col++) {
      addSlot(new Slot(playerInventory, col, startX + col * 18, hotbarY));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack result = ItemStack.EMPTY;
    Slot slot = slots.get(index);

    if (slot.hasItem()) {
      ItemStack stack = slot.getItem();
      result = stack.copy();

      if (isContainerSlot(index)) {
        if (!tryMoveToPlayerInventory(stack)) {
          return ItemStack.EMPTY;
        }
      } else {
        if (!tryMoveToContainer(stack)) {
          return ItemStack.EMPTY;
        }
      }

      updateSlotAfterMove(slot, stack);
    }

    return result;
  }

  private boolean isContainerSlot(int index) {
    return index < CookieJarData.CONTAINER_SIZE;
  }

  private boolean tryMoveToPlayerInventory(ItemStack stack) {
    return moveItemStackTo(stack, CookieJarData.CONTAINER_SIZE, slots.size(), true);
  }

  private boolean tryMoveToContainer(ItemStack stack) {
    if (stack.is(ModTags.COOKIE_JAR_ITEMS)) {
      return moveItemStackTo(stack, 0, CookieJarData.CONTAINER_SIZE, false);
    }
    return false;
  }

  private void updateSlotAfterMove(Slot slot, ItemStack stack) {
    if (stack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }
  }

  @Override
  public boolean stillValid(Player player) {
    return container.stillValid(player);
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    container.stopOpen(player);
    if (onCloseCallback != null) {
      onCloseCallback.run();
    }
  }

  public void setOnCloseCallback(Runnable callback) {
    this.onCloseCallback = callback;
  }

  public Container getContainer() {
    return container;
  }

  private static class CookieJarSlot extends Slot {
    public CookieJarSlot(Container container, int slot, int x, int y) {
      super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
      return stack.is(ModTags.COOKIE_JAR_ITEMS);
    }
  }
}
