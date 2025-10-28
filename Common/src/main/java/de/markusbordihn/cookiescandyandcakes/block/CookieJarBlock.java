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

package de.markusbordihn.cookiescandyandcakes.block;

import com.mojang.serialization.MapCodec;
import de.markusbordihn.cookiescandyandcakes.block.entity.CookieJarBlockEntity;
import de.markusbordihn.cookiescandyandcakes.data.cookiejar.CookieJarData;
import de.markusbordihn.cookiescandyandcakes.menu.MenuManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class CookieJarBlock extends BaseEntityBlock {

  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

  protected CookieJarBlock(final Properties properties) {
    super(properties);
    registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  private void openCookieJarMenu(
      final ServerPlayer serverPlayer, final CookieJarBlockEntity cookieJarBlockEntity) {
    MenuManager.openCookieJarMenu(serverPlayer, cookieJarBlockEntity);
  }

  @Override
  protected abstract MapCodec<? extends CookieJarBlock> codec();

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  protected VoxelShape getShape(
      BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new CookieJarBlockEntity(pos, state);
  }

  @Override
  protected InteractionResult useWithoutItem(
      BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    if (!level.isClientSide
        && player instanceof ServerPlayer serverPlayer
        && level.getBlockEntity(pos) instanceof CookieJarBlockEntity cookieJarBlockEntity) {
      openCookieJarMenu(serverPlayer, cookieJarBlockEntity);
    }

    return InteractionResult.SUCCESS;
  }

  @Override
  protected ItemInteractionResult useItemOn(
      ItemStack itemStack,
      BlockState blockState,
      Level level,
      BlockPos blockPos,
      Player player,
      InteractionHand interactionHand,
      BlockHitResult hitResult) {
    if (!level.isClientSide
        && player instanceof ServerPlayer serverPlayer
        && level.getBlockEntity(blockPos) instanceof CookieJarBlockEntity cookieJarBlockEntity) {
      openCookieJarMenu(serverPlayer, cookieJarBlockEntity);
    }
    return ItemInteractionResult.SUCCESS;
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Override
  protected void onRemove(
      BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity blockEntity = level.getBlockEntity(pos);
      if (blockEntity != null) {
        level.removeBlockEntity(pos);
      }
      level.updateNeighbourForOutputSignal(pos, this);
    }
  }

  @Override
  public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
    List<ItemStack> drops = new ArrayList<>();
    BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
    if (blockEntity instanceof CookieJarBlockEntity cookieJarBlockEntity) {
      ItemStack itemStack = new ItemStack(this);
      cookieJarBlockEntity.saveToItem(itemStack, cookieJarBlockEntity.getLevel().registryAccess());
      if (!itemStack.isEmpty()) {
        drops.add(itemStack);
      }
    } else {
      drops.add(new ItemStack(this));
    }
    return drops;
  }

  @Override
  public void setPlacedBy(
      Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    if (!level.isClientSide && level.getBlockEntity(pos) instanceof CookieJarBlockEntity entity) {
      CookieJarData data = CookieJarData.fromItemStack(stack);
      for (int i = 0; i < data.items().size(); i++) {
        entity.setItem(i, data.items().get(i));
      }
      entity.setChanged();
    }
  }
}
