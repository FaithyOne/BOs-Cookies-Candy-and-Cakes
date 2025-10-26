package de.markusbordihn.cookiescandyandcakes.client;

import de.markusbordihn.cookiescandyandcakes.data.cookies.CookieType;
import java.util.HashSet;
import java.util.Set;

public class ClientCookieData {
  private static final Set<String> identifiedCookies = new HashSet<>();

  private ClientCookieData() {}

  public static void markAsIdentified(CookieType cookieType) {
    identifiedCookies.add(cookieType.getId());
  }

  public static boolean hasIdentified(CookieType cookieType) {
    return identifiedCookies.contains(cookieType.getId());
  }

  public static void clear() {
    identifiedCookies.clear();
  }
}
