package me.mark.electroid;

import com.megaboost.Game;
import com.megaboost.world.WorldObjectPlaceManager;
import me.mark.electroid.gui.loadmenu.LoadMenu;
import me.mark.electroid.gui.mainmenu.MainMenu;
import me.mark.electroid.gui.pausemenu.PauseMenu;
import me.mark.electroid.network.ElectroidServer;
import me.mark.electroid.utils.ImageUtil;
import me.mark.electroid.world.ElectroidWorld;
import me.mark.electroid.world.blocks.BatteryBlock;
import me.mark.electroid.world.blocks.WireBlock;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Primary Background Color: rgb(57, 50, 49)
 *
 */
public class Electroid {

  private final Game game;
  private final MainMenu mainMenu;
  private final LoadMenu loadMenu;
  private final PauseMenu pauseMenu;
  private final ImageUtil componentSheet;
  private final ElectroidServer server;
  private final Logger logger;
  private final HashMap<String, ElectroidWorld> worlds;
  private static Electroid INSTANCE;
  public static final String ERROR_PREFIX = "ERROR> ";
  public static final String SYSTEM_PREFIX = "SYSTEM> ";
  public static final String WARNING_PREFIX = "WARNING> ";

  private Electroid() {
    this.game = new Game("Electroid");
    INSTANCE = this;

    this.logger = Logger.getLogger("Electroid");
    this.worlds = new HashMap<>();
    this.server = new ElectroidServer();
    this.componentSheet = new ImageUtil("/components.png", 51, 51);
    this.mainMenu = new MainMenu();
    this.loadMenu = new LoadMenu();
    this.pauseMenu = new PauseMenu();

    start();
  }

  private void start() {
    getMainMenu().setShown(true);
    registerWorldObjects();
    registerSavedWorlds();
  }

  private void registerWorldObjects() {
    WorldObjectPlaceManager wpm = getGame().getWorldObjectPlaceManager();
    wpm.addObject(new WireBlock());
    wpm.addObject(new BatteryBlock());

  }

  private void registerSavedWorlds() {
    List<ElectroidWorld> savedWorlds = ElectroidWorld.getSavedElectroidWorlds();
    for (ElectroidWorld world : savedWorlds) {
      this.worlds.put(world.getName(), world);
      getLogger().info("[WORLD] Registered world: " + world.getName());
    }

    this.loadMenu.createWorldList();
  }

  public Logger getLogger() {
    return logger;
  }

  public ElectroidWorld getWorldByName(String name) {
    return this.worlds.getOrDefault(name, null);
  }

  public List<ElectroidWorld> getWorlds() {
    return this.worlds.values().stream().toList();
  }

  public ElectroidServer getServer() {
    return server;
  }

  public Game getGame() {
    return game;
  }

  public ImageUtil getComponentSheet() {
    return componentSheet;
  }

  public MainMenu getMainMenu() {
    return mainMenu;
  }

  public LoadMenu getLoadMenu() {
    return loadMenu;
  }

  public static Electroid getInstance() {
    return INSTANCE;
  }

  public static void main(String[] args) {
    new Electroid();
  }

}
