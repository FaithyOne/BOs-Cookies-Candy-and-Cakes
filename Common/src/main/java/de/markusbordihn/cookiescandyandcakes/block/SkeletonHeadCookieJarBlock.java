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
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SkeletonHeadCookieJarBlock extends CookieJarBlock {

  public static final String ID = "skeleton_head_cookie_jar";
  public static final MapCodec<SkeletonHeadCookieJarBlock> CODEC =
      simpleCodec(SkeletonHeadCookieJarBlock::new);

  public SkeletonHeadCookieJarBlock(final BlockBehaviour.Properties properties) {
    super(properties);
  }

  @Override
  protected MapCodec<? extends CookieJarBlock> codec() {
    return CODEC;
  }

  @Override
  public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource random) {
    if (random.nextInt(200) == 0) {
      level.playLocalSound(
          blockPos.getX() + 0.5,
          blockPos.getY() + 0.5,
          blockPos.getZ() + 0.5,
          SoundEvents.SKELETON_AMBIENT,
          SoundSource.BLOCKS,
          0.3F,
          random.nextFloat() * 0.4F + 0.8F,
          false);
    }
  }
}
