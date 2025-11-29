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

import de.markusbordihn.cookiescandyandcakes.registry.ModItems;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CinnamonKnifeItem extends Item {

  public CinnamonKnifeItem() {
    super(new Item.Properties().durability(64));
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = level.getBlockState(blockPos);
    Optional<BlockState> strippedState = getStripped(blockState);

    if (strippedState.isPresent()) {
      level.playSound(
          context.getPlayer(),
          blockPos,
          SoundEvents.AXE_STRIP,
          SoundSource.BLOCKS,
          1.0F,
          1.0F);
      if (!level.isClientSide) {
        level.setBlock(blockPos, strippedState.get(), 11);
        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, context.getItemInHand());
        }

        // Drop cinnamon sticks only from birch logs
        if (blockState.is(Blocks.BIRCH_LOG)) {
          int drops = level.random.nextInt(4) + 1; // 1-4 drops
          if (level.random.nextFloat() < 0.25f) { // 25% chance
            for (int i = 0; i < drops; i++) {
              ItemEntity itemEntity = new ItemEntity(
                  level,
                  blockPos.getX() + 0.5,
                  blockPos.getY() + 0.5,
                  blockPos.getZ() + 0.5,
                  new ItemStack(ModItems.CINNAMON_STICK));
              level.addFreshEntity(itemEntity);
            }
          }
        }

        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), context.getPlayer().getEquipmentSlotForItem(context.getItemInHand()));
      }
      level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(context.getPlayer(), strippedState.get()));
      return InteractionResult.sidedSuccess(level.isClientSide);
    }
    return InteractionResult.PASS;
  }

  private Optional<BlockState> getStripped(BlockState state) {
    Block block = state.getBlock();
    Block strippedBlock = null;

    if (block == Blocks.BIRCH_LOG) {
      strippedBlock = Blocks.STRIPPED_BIRCH_LOG;
    } else if (block == Blocks.BIRCH_WOOD) {
      strippedBlock = Blocks.STRIPPED_BIRCH_WOOD;
    }

    if (strippedBlock != null) {
      return Optional.of(strippedBlock.defaultBlockState()
          .setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
    }
    return Optional.empty();
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext context,
      List<Component> tooltipComponents,
      TooltipFlag tooltipFlag) {
    tooltipComponents.add(
        Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
  }
}
