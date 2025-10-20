package de.markusbordihn.cookiescandyandcakes;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {

  public static final String MOD_ID = "cookies_candy_and_cakes";
  public static final String MOD_NAME = "Cookies, Candy and Cakes";
  public static final String MOD_COMMAND = MOD_ID;
  public static final String MOD_PREFIX = MOD_ID + ".";
  public static final String LOG_NAME = MOD_NAME;
  public static final String LOG_REGISTER_PREFIX = "Register " + MOD_NAME;

  public static Path GAME_DIR = Paths.get("").toAbsolutePath();
  public static Path CONFIG_DIR = GAME_DIR.resolve("config");

  private Constants() {}
}
