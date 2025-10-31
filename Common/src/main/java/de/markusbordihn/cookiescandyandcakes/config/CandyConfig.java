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

import de.markusbordihn.cookiescandyandcakes.data.SpecialItemEffect;
import de.markusbordihn.cookiescandyandcakes.data.candies.CandyType;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class CandyConfig extends Config {

  public static final String CONFIG_FILE_NAME = "candies.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Candy Configuration

 This configuration file allows you to customize candy properties per type.

 General Settings:
 - nutrition: Food points restored (default: 2)
 - effectDuration: Duration of potion effects in ticks (default: 200 = 10 seconds, 20 ticks = 1 second)
 - effectChance: Chance for potion effects to apply (0.0-1.0, default: 1.0 = 100%)

 Special Candy Effects (for MYSTIC and CURSED variants):
 - specialEffect.lightningChance: Chance for lightning effect (0.0-1.0, default: 0.5 = 50%)
 - specialEffect.enableSounds: Enable dramatic sound effects (default: true)
 - specialEffect.soundVolume: Volume of sound effects (MYSTIC default: 1.0, CURSED default: 4.0)
 - specialEffect.enableDarknessEffect: Enable darkness effect for CURSED candies (default: true, disable for epilepsy/photosensitivity)
 - specialEffect.darknessEffectDuration: Duration of darkness effect in ticks for CURSED candies (default: 60 = 3 seconds)
 - specialEffect.mysticSoundType: Sound type for MYSTIC candies (levelup, enchant, amethyst, bell, none; default: levelup)
 - specialEffect.cursedSoundType: Sound type for CURSED candies (ender_dragon, wither, ambient_cave, sculk, none; default: ender_dragon)

 Available Sound Types:
 - MYSTIC: levelup, enchant, amethyst, bell, none
 - CURSED: ender_dragon, wither, ambient_cave, sculk, none

 Accessibility Note:
 - Set specialEffect.enableDarknessEffect to false if you have epilepsy or are sensitive to screen flashing/darkening effects

 You can configure these values globally or per candy type by prefixing with the candy type ID.
 Example: apple_candy_mystic.nutrition = 3

""";
  private static final Map<CandyType, Integer> nutritionOverrides = new EnumMap<>(CandyType.class);
  private static final Map<CandyType, Integer> effectDurationOverrides =
      new EnumMap<>(CandyType.class);
  private static final Map<CandyType, Float> effectChanceOverrides = new EnumMap<>(CandyType.class);

  private static int defaultNutrition = 2;
  private static int defaultEffectDuration = 200;
  private static float defaultEffectChance = 1.0f;

  private static float specialEffectLightningChance = 0.5f;
  private static boolean specialEffectEnableSounds = true;
  private static boolean specialEffectEnableDarknessEffect = true;
  private static float specialEffectMysticSoundVolume = 1.0f;
  private static float specialEffectCursedSoundVolume = 4.0f;
  private static int specialEffectDarknessEffectDuration = 60;
  private static String specialEffectMysticSoundType = "levelup";
  private static String specialEffectCursedSoundType = "ender_dragon";

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    defaultNutrition = parseConfigValue(properties, "nutrition", defaultNutrition);
    defaultEffectDuration = parseConfigValue(properties, "effectDuration", defaultEffectDuration);
    defaultEffectChance = parseConfigValue(properties, "effectChance", defaultEffectChance);

    specialEffectLightningChance =
        parseConfigValue(properties, "specialEffect.lightningChance", specialEffectLightningChance);
    specialEffectEnableSounds =
        parseConfigValue(properties, "specialEffect.enableSounds", specialEffectEnableSounds);
    specialEffectEnableDarknessEffect =
        parseConfigValue(
            properties, "specialEffect.enableDarknessEffect", specialEffectEnableDarknessEffect);
    specialEffectMysticSoundVolume =
        parseConfigValue(
            properties, "specialEffect.mysticSoundVolume", specialEffectMysticSoundVolume);
    specialEffectCursedSoundVolume =
        parseConfigValue(
            properties, "specialEffect.cursedSoundVolume", specialEffectCursedSoundVolume);
    specialEffectDarknessEffectDuration =
        parseConfigValue(
            properties,
            "specialEffect.darknessEffectDuration",
            specialEffectDarknessEffectDuration);
    specialEffectMysticSoundType =
        parseConfigValue(properties, "specialEffect.mysticSoundType", specialEffectMysticSoundType);
    specialEffectCursedSoundType =
        parseConfigValue(properties, "specialEffect.cursedSoundType", specialEffectCursedSoundType);

    nutritionOverrides.clear();
    effectDurationOverrides.clear();
    effectChanceOverrides.clear();

    for (CandyType type : CandyType.values()) {
      String camelCaseName = toCamelCase(type.getId());
      String prefix = "candy." + camelCaseName + ".";

      nutritionOverrides.put(
          type, parseConfigValue(properties, prefix + "nutrition", defaultNutrition));
      effectDurationOverrides.put(
          type, parseConfigValue(properties, prefix + "effectDuration", defaultEffectDuration));
      effectChanceOverrides.put(
          type, parseConfigValue(properties, prefix + "effectChance", defaultEffectChance));
    }

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static int getNutrition(final CandyType type) {
    return nutritionOverrides.getOrDefault(type, defaultNutrition);
  }

  public static int getEffectDuration(final CandyType type) {
    return effectDurationOverrides.getOrDefault(type, defaultEffectDuration);
  }

  public static float getEffectChance(final CandyType type) {
    return effectChanceOverrides.getOrDefault(type, defaultEffectChance);
  }

  public static SpecialItemEffect getSpecialCandyEffect(final CandyType type) {
    if (type.getVariant() == CandyType.CandyVariant.MYSTIC) {
      return new SpecialItemEffect(
          specialEffectLightningChance,
          specialEffectEnableSounds,
          specialEffectMysticSoundVolume,
          0,
          specialEffectMysticSoundType,
          "none");
    } else if (type.getVariant() == CandyType.CandyVariant.CURSED) {
      return new SpecialItemEffect(
          specialEffectLightningChance,
          specialEffectEnableSounds,
          specialEffectCursedSoundVolume,
          specialEffectEnableDarknessEffect ? specialEffectDarknessEffectDuration : 0,
          "none",
          specialEffectCursedSoundType);
    }
    return SpecialItemEffect.NONE;
  }
}
