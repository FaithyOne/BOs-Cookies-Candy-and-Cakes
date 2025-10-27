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

import de.markusbordihn.cookiescandyandcakes.menu.MenuManager;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CookieJarItem extends BlockItem {

  public CookieJarItem(Block block, Item.Properties properties) {
    super(block, properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    return super.useOn(context);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack itemStack = player.getItemInHand(hand);

    if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
      MenuManager.openCookieJarMenu(serverPlayer, itemStack, hand);
      return InteractionResultHolder.success(itemStack);
    }

    return InteractionResultHolder.consume(itemStack);
  }

  @Override
  public boolean overrideStackedOnOther(
      ItemStack cookieJarStack, Slot slot, ClickAction action, Player player) {
    if (action == ClickAction.SECONDARY && !cookieJarStack.isEmpty()) {
      if (player instanceof ServerPlayer serverPlayer) {
        MenuManager.openCookieJarMenu(serverPlayer, cookieJarStack, InteractionHand.MAIN_HAND);
      }
      return true;
    }
    return false;
  }

  @Override
  public void appendHoverText(
      ItemStack stack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag flag) {
    super.appendHoverText(stack, context, tooltipComponents, flag);

    String blockDescKey = this.getBlock().getDescriptionId() + ".desc";
    tooltipComponents.add(
        Component.translatable(blockDescKey).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

    tooltipComponents.add(
        Component.translatable("item.cookies_candy_and_cakes.cookie_jar.open_hint")
            .withStyle(ChatFormatting.AQUA));

    ItemContainerContents contents =
        stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    int itemCount = 0;
    int slotCount = 0;

    for (ItemStack item : contents.nonEmptyItems()) {
      if (!item.isEmpty()) {
        itemCount += item.getCount();
        slotCount++;
      }
    }

    if (itemCount > 0) {
      tooltipComponents.add(
          Component.translatable(
                  "item.cookies_candy_and_cakes.cookie_jar.contents", itemCount, slotCount)
              .withStyle(ChatFormatting.GOLD));
    } else {
      tooltipComponents.add(
          Component.translatable("item.cookies_candy_and_cakes.cookie_jar.empty")
              .withStyle(ChatFormatting.DARK_GRAY));
    }
  }

  @Override
  public boolean canFitInsideContainerItems() {
    return false;
  }
}
