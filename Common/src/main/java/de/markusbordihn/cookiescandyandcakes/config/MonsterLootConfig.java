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

import java.io.File;
import java.util.Properties;

public class MonsterLootConfig extends Config {

  public static final String CONFIG_FILE_NAME = "monster_loot.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Monster Loot Configuration

 This configuration file allows you to define drop chances for candy crumbs from monsters.
 Drop chances are in percentage (0-100). Set to 0 to disable drops for that item.
 The global drop chance determines if ANY candy crumb will drop (25% default).

""";

  // Global drop chance (percentage 0-100)
  public static int globalDropChance = 25;

  // Individual item drop weights (0 = disabled, higher = more likely)
  public static int appleCookieMysticDropWeight = 1;
  public static int appleCookieCursedDropWeight = 1;
  public static int carrotCookieMysticDropWeight = 1;
  public static int carrotCookieCursedDropWeight = 1;
  public static int glowBerryCookieMysticDropWeight = 1;
  public static int glowBerryCookieCursedDropWeight = 1;
  public static int melonCookieMysticDropWeight = 1;
  public static int melonCookieCursedDropWeight = 1;
  public static int pumpkinCookieMysticDropWeight = 1;
  public static int pumpkinCookieCursedDropWeight = 1;
  public static int sweetBerryCookieMysticDropWeight = 1;
  public static int sweetBerryCookieCursedDropWeight = 1;
  public static int slimeSugarCookieMysticDropWeight = 1;
  public static int slimeSugarCookieCursedDropWeight = 1;

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

    // Individual item drop weights
    appleCookieMysticDropWeight =
        parseConfigValue(properties, "appleCookieMysticDropWeight", appleCookieMysticDropWeight);
    appleCookieCursedDropWeight =
        parseConfigValue(properties, "appleCookieCursedDropWeight", appleCookieCursedDropWeight);
    carrotCookieMysticDropWeight =
        parseConfigValue(properties, "carrotCookieMysticDropWeight", carrotCookieMysticDropWeight);
    carrotCookieCursedDropWeight =
        parseConfigValue(properties, "carrotCookieCursedDropWeight", carrotCookieCursedDropWeight);
    glowBerryCookieMysticDropWeight =
        parseConfigValue(
            properties, "glowBerryCookieMysticDropWeight", glowBerryCookieMysticDropWeight);
    glowBerryCookieCursedDropWeight =
        parseConfigValue(
            properties, "glowBerryCookieCursedDropWeight", glowBerryCookieCursedDropWeight);
    melonCookieMysticDropWeight =
        parseConfigValue(properties, "melonCookieMysticDropWeight", melonCookieMysticDropWeight);
    melonCookieCursedDropWeight =
        parseConfigValue(properties, "melonCookieCursedDropWeight", melonCookieCursedDropWeight);
    pumpkinCookieMysticDropWeight =
        parseConfigValue(
            properties, "pumpkinCookieMysticDropWeight", pumpkinCookieMysticDropWeight);
    pumpkinCookieCursedDropWeight =
        parseConfigValue(
            properties, "pumpkinCookieCursedDropWeight", pumpkinCookieCursedDropWeight);
    sweetBerryCookieMysticDropWeight =
        parseConfigValue(
            properties, "sweetBerryCookieMysticDropWeight", sweetBerryCookieMysticDropWeight);
    sweetBerryCookieCursedDropWeight =
        parseConfigValue(
            properties, "sweetBerryCookieCursedDropWeight", sweetBerryCookieCursedDropWeight);
    slimeSugarCookieMysticDropWeight =
        parseConfigValue(
            properties, "slimeSugarCookieMysticDropWeight", slimeSugarCookieMysticDropWeight);
    slimeSugarCookieCursedDropWeight =
        parseConfigValue(
            properties, "slimeSugarCookieCursedDropWeight", slimeSugarCookieCursedDropWeight);

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }
}
