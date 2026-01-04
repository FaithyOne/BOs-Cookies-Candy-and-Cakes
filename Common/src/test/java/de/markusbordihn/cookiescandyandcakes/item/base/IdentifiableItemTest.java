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

package de.markusbordihn.cookiescandyandcakes.item.base;

import static org.junit.jupiter.api.Assertions.*;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("IdentifiableItem Interface Tests")
class IdentifiableItemTest {

  private TestIdentifiableItem unidentifiedItem;
  private TestIdentifiableItem identifiedItem;

  @BeforeEach
  void setUp() {
    unidentifiedItem = new TestIdentifiableItem(false);
    identifiedItem = new TestIdentifiableItem(true);
  }

  @Test
  @DisplayName("Unidentified name should be obfuscated")
  void testUnidentifiedNameIsObfuscated() {
    Component name = unidentifiedItem.getUnidentifiedName();
    assertNotNull(name, "Name should not be null");
    
    String nameString = name.getString();
    assertTrue(nameString.contains("unidentified"), 
        "Unidentified name should contain base key");
  }

  @Test
  @DisplayName("Identified name should use normal translation key")
  void testIdentifiedNameUsesNormalKey() {
    Component name = identifiedItem.getUnidentifiedName();
    assertNotNull(name, "Name should not be null");
    
    String nameString = name.getString();
    assertTrue(nameString.contains("test.item"), 
        "Identified name should use description id");
  }

  @Test
  @DisplayName("Unidentified tooltip should be obfuscated")
  void testUnidentifiedTooltipIsObfuscated() {
    Component tooltip = unidentifiedItem.getIdentifiedTooltip();
    assertNotNull(tooltip, "Tooltip should not be null");
    
    String tooltipString = tooltip.getString();
    assertTrue(tooltipString.contains("unidentified") && tooltipString.contains("desc"),
        "Unidentified tooltip should use unidentified desc key");
  }

  @Test
  @DisplayName("Identified tooltip should show description")
  void testIdentifiedTooltipShowsDescription() {
    Component tooltip = identifiedItem.getIdentifiedTooltip();
    assertNotNull(tooltip, "Tooltip should not be null");
    
    String tooltipString = tooltip.getString();
    assertTrue(tooltipString.contains("test.item") && tooltipString.contains("desc"),
        "Identified tooltip should use normal desc key");
  }

  @Test
  @DisplayName("Unidentified key should follow naming convention")
  void testUnidentifiedKeyConvention() {
    String baseKey = unidentifiedItem.getUnidentifiedBaseKey();
    String unidentifiedKey = unidentifiedItem.getUnidentifiedKey();
    String descKey = unidentifiedItem.getUnidentifiedDescKey();
    
    assertEquals(baseKey, unidentifiedKey, "Base key should match unidentified key");
    assertEquals(baseKey + ".desc", descKey, "Desc key should be base + .desc");
  }

  @Test
  @DisplayName("Variant color should be applied to identified items")
  void testVariantColorIsApplied() {
    ChatFormatting color = identifiedItem.getVariantColor(identifiedItem.getItemType());
    assertNotNull(color, "Variant color should not be null");
    assertEquals(ChatFormatting.GOLD, color, "Test item should have GOLD color");
  }

  @Test
  @DisplayName("Identification state should be persistent")
  void testIdentificationStatePersistence() {
    TestIdentifiableItem item = new TestIdentifiableItem(false);
    
    assertFalse(item.hasIdentified(item.getItemType()), 
        "Item should start unidentified");
    
    item.markAsIdentified(item.getItemType());
    
    assertTrue(item.hasIdentified(item.getItemType()), 
        "Item should be identified after marking");
  }

  // Test implementation of IdentifiableItem
  private static class TestIdentifiableItem implements IdentifiableItem<String> {
    private final String itemType = "TEST_TYPE";
    private boolean identified;

    public TestIdentifiableItem(boolean identified) {
      this.identified = identified;
    }

    @Override
    public String getItemType() {
      return itemType;
    }

    @Override
    public String getDescriptionId() {
      return "test.item";
    }

    @Override
    public boolean hasIdentified(String type) {
      return identified;
    }

    @Override
    public void markAsIdentified(String type) {
      this.identified = true;
    }

    @Override
    public ChatFormatting getVariantColor(String type) {
      return ChatFormatting.GOLD;
    }

    @Override
    public String getUnidentifiedBaseKey() {
      return "unidentified.test";
    }
  }
}
