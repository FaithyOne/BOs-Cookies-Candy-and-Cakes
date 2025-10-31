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

package de.markusbordihn.cookiescandyandcakes.config;

import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class MonsterLootConfig extends Config {

  public static final String CONFIG_FILE_NAME = "monster_loot.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Monster Loot Configuration

 This configuration file allows you to define drop chances for special cookies and candies from monsters.
 Drop chances are in percentage (0-100). Set to 0 to disable drops for that item.
 The global drop chance determines if ANY special item will drop (25% default).

 Cookie Drops: Special MYSTIC and CURSED cookie variants
 Candy Drops: Special MYSTIC and CURSED candy variants

 Drop weights determine the relative probability of each item dropping.
 Higher weight = more likely to drop. Set to 0 to disable that specific item.

""";
  // Maps to store drop weights for each type
  private static final Map<CookieType, Integer> cookieDropWeights = new EnumMap<>(CookieType.class);
  private static final Map<CandyType, Integer> candyDropWeights = new EnumMap<>(CandyType.class);
  // Global drop chance (percentage 0-100)
  public static int globalDropChance = 25;

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Global settings
    globalDropChance = parseConfigValue(properties, "globalDropChance", globalDropChance);

    // Clear existing weights
    cookieDropWeights.clear();
    candyDropWeights.clear();

    // Parse cookie drop weights - only for MYSTIC and CURSED variants
    for (CookieType type : CookieType.values()) {
      if (type.getVariant() == CookieType.CookieVariant.MYSTIC
          || type.getVariant() == CookieType.CookieVariant.CURSED) {
        // Convert apple_cookie_mystic -> appleCookieMystic
        String camelCaseName = toCamelCase(type.getId());
        String key = "cookie." + camelCaseName + ".dropWeight";
        int defaultWeight = 1;
        int weight = parseConfigValue(properties, key, defaultWeight);
        cookieDropWeights.put(type, weight);
      }
    }

    // Parse candy drop weights - only for MYSTIC and CURSED variants
    for (CandyType type : CandyType.values()) {
      if (type.getVariant() == CandyType.CandyVariant.MYSTIC
          || type.getVariant() == CandyType.CandyVariant.CURSED) {
        // Convert apple_candy_cursed -> appleCandyCursed
        String camelCaseName = toCamelCase(type.getId());
        String key = "candy." + camelCaseName + ".dropWeight";
        int defaultWeight = 1;
        int weight = parseConfigValue(properties, key, defaultWeight);
        candyDropWeights.put(type, weight);
      }
    }

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static int getCookieDropWeight(final CookieType type) {
    return cookieDropWeights.getOrDefault(type, 0);
  }

  public static int getCandyDropWeight(final CandyType type) {
    return candyDropWeights.getOrDefault(type, 0);
  }
}
