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

package de.markusbordihn.cookiescandyandcakes;

import de.markusbordihn.cookiescandyandcakes.block.FabricModBlocks;
import de.markusbordihn.cookiescandyandcakes.block.entity.FabricModBlockEntities;
import de.markusbordihn.cookiescandyandcakes.config.Config;
import de.markusbordihn.cookiescandyandcakes.entity.FabricModEntityTypes;
import de.markusbordihn.cookiescandyandcakes.event.FabricCreativeModeTabHandler;
import de.markusbordihn.cookiescandyandcakes.event.FabricMonsterLootHandler;
import de.markusbordihn.cookiescandyandcakes.event.FabricPlayerTickHandler;
import de.markusbordihn.cookiescandyandcakes.item.FabricModBlockItems;
import de.markusbordihn.cookiescandyandcakes.item.FabricModItems;
import de.markusbordihn.cookiescandyandcakes.menu.FabricMenuOpener;
import de.markusbordihn.cookiescandyandcakes.menu.FabricModMenus;
import de.markusbordihn.cookiescandyandcakes.menu.MenuManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CookiesCandyAndCakes implements ModInitializer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitialize() {
    log.info("Initializing {} (Fabric) ...", Constants.MOD_NAME);

    log.info("{} Constants ...", Constants.LOG_REGISTER_PREFIX);
    Constants.GAME_DIR = FabricLoader.getInstance().getGameDir();
    Constants.CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    log.info("{} Configuration ...", Constants.LOG_REGISTER_PREFIX);
    Config.register(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER);

    log.info("{} Menu Manager ...", Constants.LOG_REGISTER_PREFIX);
    MenuManager.setMenuOpener(new FabricMenuOpener());

    log.info("{} Blocks ...", Constants.LOG_REGISTER_PREFIX);
    FabricModBlocks.register();

    log.info("{} Block Entities ...", Constants.LOG_REGISTER_PREFIX);
    FabricModBlockEntities.register();

    log.info("{} Entity Types ...", Constants.LOG_REGISTER_PREFIX);
    FabricModEntityTypes.register();

    log.info("{} Block Items ...", Constants.LOG_REGISTER_PREFIX);
    FabricModBlockItems.register();

    log.info("{} Items ...", Constants.LOG_REGISTER_PREFIX);
    FabricModItems.register();

    log.info("{} Menus ...", Constants.LOG_REGISTER_PREFIX);
    FabricModMenus.register();

    log.info("{} Events ...", Constants.LOG_REGISTER_PREFIX);
    FabricMonsterLootHandler.register();
    FabricPlayerTickHandler.register();
    FabricCreativeModeTabHandler.register();
  }
}
