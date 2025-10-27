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

package de.markusbordihn.cookiescandyandcakes.block.entity;

import de.markusbordihn.cookiescandyandcakes.data.cookiejar.CookieJarData;
import de.markusbordihn.cookiescandyandcakes.menu.CookieJarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CookieJarBlockEntity extends BlockEntity implements Container, MenuProvider {

  public static final String COOKIE_JAR_DATA_TAG = "cookiejar_data";
  public static BlockEntityType<CookieJarBlockEntity> TYPE;
  private NonNullList<ItemStack> items =
      NonNullList.withSize(CookieJarData.CONTAINER_SIZE, ItemStack.EMPTY);

  public CookieJarBlockEntity(final BlockPos blockPos, final BlockState blockState) {
    super(TYPE, blockPos, blockState);
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("container.cookies_candy_and_cakes.cookie_jar");
  }

  @Override
  public AbstractContainerMenu createMenu(
      int containerId, Inventory playerInventory, Player player) {
    return new CookieJarMenu(containerId, playerInventory, this);
  }

  @Override
  public int getContainerSize() {
    return CookieJarData.CONTAINER_SIZE;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack item : items) {
      if (!item.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getItem(int slot) {
    return slot >= 0 && slot < items.size() ? items.get(slot) : ItemStack.EMPTY;
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    ItemStack stack = items.get(slot);
    if (!stack.isEmpty()) {
      if (stack.getCount() <= amount) {
        items.set(slot, ItemStack.EMPTY);
        setChanged();
        return stack;
      } else {
        ItemStack result = stack.split(amount);
        setChanged();
        return result;
      }
    }
    return ItemStack.EMPTY;
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    if (slot >= 0 && slot < items.size()) {
      ItemStack stack = items.get(slot);
      items.set(slot, ItemStack.EMPTY);
      return stack;
    }
    return ItemStack.EMPTY;
  }

  @Override
  public void setItem(int slot, ItemStack itemStack) {
    if (slot >= 0 && slot < items.size()) {
      items.set(slot, itemStack);
      if (itemStack.getCount() > getMaxStackSize()) {
        itemStack.setCount(getMaxStackSize());
      }
      setChanged();
    }
  }

  @Override
  public boolean stillValid(Player player) {
    return Container.stillValidBlockEntity(this, player);
  }

  @Override
  public void clearContent() {
    items.clear();
    items = NonNullList.withSize(CookieJarData.CONTAINER_SIZE, ItemStack.EMPTY);
    setChanged();
  }

  @Override
  public void saveToItem(ItemStack itemStack, HolderLookup.Provider registries) {
    super.saveToItem(itemStack, registries);
    CookieJarData data = new CookieJarData(items);
    data.saveToItemStack(itemStack);
  }

  @Override
  protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider registries) {
    super.saveAdditional(compoundTag, registries);
    CookieJarData data = new CookieJarData(items);
    ItemStack tempStack = new ItemStack(this.getBlockState().getBlock());
    data.saveToItemStack(tempStack);
    compoundTag.put(COOKIE_JAR_DATA_TAG, tempStack.save(registries));
  }

  @Override
  protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider registries) {
    super.loadAdditional(compoundTag, registries);
    if (compoundTag.contains(COOKIE_JAR_DATA_TAG)) {
      ItemStack tempStack =
          ItemStack.parseOptional(registries, compoundTag.getCompound(COOKIE_JAR_DATA_TAG));
      CookieJarData data = CookieJarData.fromItemStack(tempStack);
      this.items = data.items();
    }
  }

  public NonNullList<ItemStack> getItems() {
    return items;
  }
}
