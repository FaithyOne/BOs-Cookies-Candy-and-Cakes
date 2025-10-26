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

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

public class CookieConfig extends Config {

  public static final String CONFIG_FILE_NAME = "cookies.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Cookie Configuration

 This configuration file allows you to customize cookie properties per type.

 General Settings:
 - nutrition: Food points restored (default: 2)
 - effectDuration: Duration of potion effects in ticks (default: 600 = 30 seconds, 20 ticks = 1 second)
 - effectChance: Chance for potion effects to apply (0.0-1.0, default: 1.0 = 100%)

 Special Cookie Effects (for MYSTIC and CURSED variants):
 - specialEffect.lightningChance: Chance for lightning effect (0.0-1.0, default: 0.5 = 50%)
 - specialEffect.enableSounds: Enable dramatic sound effects (default: true)
 - specialEffect.soundVolume: Volume of sound effects (MYSTIC default: 1.0, CURSED default: 4.0)
 - specialEffect.enableDarknessEffect: Enable darkness effect for CURSED cookies (default: true, disable for epilepsy/photosensitivity)
 - specialEffect.darknessEffectDuration: Duration of darkness effect in ticks for CURSED cookies (default: 60 = 3 seconds)
 - specialEffect.mysticSoundType: Sound type for MYSTIC cookies (levelup, enchant, amethyst, bell, none; default: levelup)
 - specialEffect.cursedSoundType: Sound type for CURSED cookies (ender_dragon, wither, ambient_cave, sculk, none; default: ender_dragon)

 Available Sound Types:
 - MYSTIC: levelup, enchant, amethyst, bell, none
 - CURSED: ender_dragon, wither, ambient_cave, sculk, none

 Accessibility Note:
 - Set specialEffect.enableDarknessEffect to false if you have epilepsy or are sensitive to screen flashing/darkening effects

 You can configure these values globally or per cookie type by prefixing with the cookie type ID.
 Example: apple_cookie_mystic.nutrition = 3

""";
  private static final Map<CookieType, Integer> nutritionOverrides =
      new EnumMap<>(CookieType.class);
  private static final Map<CookieType, Integer> effectDurationOverrides =
      new EnumMap<>(CookieType.class);
  private static final Map<CookieType, Float> effectChanceOverrides =
      new EnumMap<>(CookieType.class);

  private static int defaultNutrition = 2;
  private static int defaultEffectDuration = 600;
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

    for (CookieType type : CookieType.values()) {
      String prefix = type.getId() + ".";

      if (properties.containsKey(prefix + "nutrition")) {
        nutritionOverrides.put(
            type, parseConfigValue(properties, prefix + "nutrition", defaultNutrition));
      }

      if (properties.containsKey(prefix + "effectDuration")) {
        effectDurationOverrides.put(
            type, parseConfigValue(properties, prefix + "effectDuration", defaultEffectDuration));
      }

      if (properties.containsKey(prefix + "effectChance")) {
        effectChanceOverrides.put(
            type, parseConfigValue(properties, prefix + "effectChance", defaultEffectChance));
      }
    }

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static int getNutrition(final CookieType type) {
    return nutritionOverrides.getOrDefault(type, defaultNutrition);
  }

  public static int getEffectDuration(final CookieType type) {
    return effectDurationOverrides.getOrDefault(type, defaultEffectDuration);
  }

  public static float getEffectChance(final CookieType type) {
    return effectChanceOverrides.getOrDefault(type, defaultEffectChance);
  }

  public static CookieType.SpecialCookieEffect getSpecialCookieEffect(final CookieType type) {
    if (type.getVariant() == CookieType.CookieVariant.MYSTIC) {
      return new CookieType.SpecialCookieEffect(
          specialEffectLightningChance,
          specialEffectEnableSounds,
          specialEffectMysticSoundVolume,
          0,
          specialEffectMysticSoundType,
          "none");
    } else if (type.getVariant() == CookieType.CookieVariant.CURSED) {
      return new CookieType.SpecialCookieEffect(
          specialEffectLightningChance,
          specialEffectEnableSounds,
          specialEffectCursedSoundVolume,
          specialEffectEnableDarknessEffect ? specialEffectDarknessEffectDuration : 0,
          "none",
          specialEffectCursedSoundType);
    }
    return CookieType.SpecialCookieEffect.NONE;
  }
}
