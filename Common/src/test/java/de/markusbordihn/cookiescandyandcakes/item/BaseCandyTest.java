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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("BaseCandy Tests")
class BaseCandyTest {

  @Test
  @DisplayName("Throw timing constants should be in correct order")
  void testThrowTimingConstants() {
    int throwMin = BaseCandy.getThrowMinTicks();
    int showBar = BaseCandy.getShowBarMinTicks();
    int throwMax = BaseCandy.getThrowMaxTicks();
    int pause = BaseCandy.getPauseTicks();
    int eatStart = BaseCandy.getEatStartTicks();

    // Verify logical progression: min < showBar < max < pause < eatStart
    assertTrue(throwMin < showBar, "THROW_MIN_TICKS should be less than SHOW_BAR_MIN_TICKS");
    assertTrue(showBar < throwMax, "SHOW_BAR_MIN_TICKS should be less than THROW_MAX_TICKS");
    assertTrue(throwMax < pause, "THROW_MAX_TICKS should be less than PAUSE_TICKS");
    assertTrue(pause < eatStart, "PAUSE_TICKS should be less than EAT_START_TICKS");
  }

  @Test
  @DisplayName("Throw min ticks should be at least 2 for input detection")
  void testThrowMinTicksMinimum() {
    assertTrue(BaseCandy.getThrowMinTicks() >= 2, "Minimum throw ticks should be at least 2");
  }

  @Test
  @DisplayName("Timing values should be positive")
  void testTimingValuesArePositive() {
    assertTrue(BaseCandy.getThrowMinTicks() > 0, "THROW_MIN_TICKS must be positive");
    assertTrue(BaseCandy.getShowBarMinTicks() > 0, "SHOW_BAR_MIN_TICKS must be positive");
    assertTrue(BaseCandy.getThrowMaxTicks() > 0, "THROW_MAX_TICKS must be positive");
    assertTrue(BaseCandy.getPauseTicks() > 0, "PAUSE_TICKS must be positive");
    assertTrue(BaseCandy.getEatStartTicks() > 0, "EAT_START_TICKS must be positive");
  }

  @ParameterizedTest
  @CsvSource({
    "0, false", // Below THROW_MIN_TICKS
    "1, false", // Below THROW_MIN_TICKS
    "2, true", // At THROW_MIN_TICKS
    "5, true", // Between min and max
    "20, true", // At THROW_MAX_TICKS
    "21, false", // In PAUSE window
    "26, false", // Just before EAT_START
    "27, false", // At EAT_START_TICKS
    "30, false" // After EAT_START
  })
  @DisplayName("Used ticks should determine valid throw window")
  void testThrowWindowValidation(int usedTicks, boolean shouldBeValid) {
    boolean isInThrowWindow =
        usedTicks >= BaseCandy.getThrowMinTicks() && usedTicks <= BaseCandy.getThrowMaxTicks();

    assertEquals(
        shouldBeValid,
        isInThrowWindow,
        String.format(
            "Ticks %d should %s in throw window", usedTicks, shouldBeValid ? "be" : "not be"));
  }

  @Test
  @DisplayName("Throw max should provide enough time for charge bar display")
  void testThrowMaxAllowsBarDisplay() {
    int throwMax = BaseCandy.getThrowMaxTicks();
    int showBar = BaseCandy.getShowBarMinTicks();

    assertTrue(
        throwMax - showBar >= 10,
        "Should have sufficient ticks between bar display and max charge");
  }

  @Test
  @DisplayName("Pause window should exist between throw and eat")
  void testPauseWindowExists() {
    int throwMax = BaseCandy.getThrowMaxTicks();
    int pause = BaseCandy.getPauseTicks();
    int eatStart = BaseCandy.getEatStartTicks();

    assertTrue(pause > throwMax, "Pause should start after max throw");
    assertTrue(eatStart > pause, "Eat should start after pause");
    assertTrue(eatStart - throwMax >= 5, "Should have at least 5 tick pause window");
  }
}
