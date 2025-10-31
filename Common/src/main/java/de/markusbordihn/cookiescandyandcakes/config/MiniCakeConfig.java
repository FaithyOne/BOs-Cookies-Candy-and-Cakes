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

import de.markusbordihn.cookiescandyandcakes.data.minicakes.MiniCakeType;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class MiniCakeConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mini_cakes.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mini Cake Configuration

 This configuration file allows you to customize mini cake properties per type.

 General Settings:
 - nutrition: Food points restored (default: 3)

 You can configure these values globally or per mini cake type by prefixing with the mini cake type ID.
 Example: mini_apple_cake.nutrition = 4

""";
  private static final Map<MiniCakeType, Integer> nutritionOverrides =
      new EnumMap<>(MiniCakeType.class);

  private static int defaultNutrition = 3;

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    defaultNutrition = parseConfigValue(properties, "nutrition", defaultNutrition);

    nutritionOverrides.clear();

    for (MiniCakeType type : MiniCakeType.values()) {
      String camelCaseName = toCamelCase(type.getId());
      String prefix = "miniCake." + camelCaseName + ".";

      nutritionOverrides.put(
          type, parseConfigValue(properties, prefix + "nutrition", defaultNutrition));
    }

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static int getNutrition(final MiniCakeType type) {
    return nutritionOverrides.getOrDefault(type, defaultNutrition);
  }
}
