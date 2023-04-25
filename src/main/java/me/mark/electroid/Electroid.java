package me.mark.electroid;

import com.megaboost.Game;
import com.megaboost.command.Command;
import com.megaboost.command.CommandManager;
import com.megaboost.events.EventManager;
import com.megaboost.world.WorldObjectPlaceManager;
import me.mark.electroid.commands.BatteryCommand;
import me.mark.electroid.commands.ResistorCommand;
import me.mark.electroid.commands.RoomCommand;
import me.mark.electroid.gui.analysismenu.AnalysisMenu;
import me.mark.electroid.gui.loadmenu.LoadMenu;
import me.mark.electroid.gui.mainmenu.MainMenu;
import me.mark.electroid.gui.pausemenu.PauseMenu;
import me.mark.electroid.listeners.WorldObjectBreakListener;
import me.mark.electroid.network.ElectroidServer;
import me.mark.electroid.simulation.SimulationManager;
import me.mark.electroid.utils.ImageUtil;
import me.mark.electroid.world.ElectroidWorld;
import me.mark.electroid.world.blocks.BatteryBlock;
import me.mark.electroid.world.blocks.ResistorBlock;
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
  private final SimulationManager simulationManager;
  private final HashMap<String, ElectroidWorld> worlds;
  private final AnalysisMenu analysisMenu;
  private static Electroid INSTANCE;
  public static final String ERROR_PREFIX = "ERROR> ";
  public static final String SYSTEM_PREFIX = "SYSTEM> ";
  public static final String WARNING_PREFIX = "WARNING> ";

  private Electroid() {
    this.game = new Game("Electroid");
    INSTANCE = this;

    this.simulationManager = new SimulationManager(this);
    this.logger = Logger.getLogger("Electroid");
    this.worlds = new HashMap<>();
    this.server = new ElectroidServer();
    this.componentSheet = new ImageUtil("/components.png", 51, 51);
    this.mainMenu = new MainMenu();
    this.loadMenu = new LoadMenu();
    this.pauseMenu = new PauseMenu();
    this.analysisMenu = new AnalysisMenu();

    start();
  }

  private void start() {
    getMainMenu().setShown(true);
    registerWorldObjects();
    registerSavedWorlds();
    registerCommands();
    registerListeners();
  }

  private void registerWorldObjects() {
    WorldObjectPlaceManager wpm = getGame().getWorldObjectPlaceManager();
    wpm.addObject(new WireBlock());
    wpm.addObject(new BatteryBlock());
    wpm.addObject(new ResistorBlock());

  }

  private void registerSavedWorlds() {
    List<ElectroidWorld> savedWorlds = ElectroidWorld.getSavedElectroidWorlds();
    for (ElectroidWorld world : savedWorlds) {
      this.worlds.put(world.getName(), world);
      getLogger().info("[WORLD] Registered world: " + world.getName());
    }

    this.loadMenu.createWorldList();
  }

  private void registerCommands() {
    CommandManager commandManager = getGame().getCommandManager();
    commandManager.addCommand(new Command("battery", "Give yourself a battery!", "/battery <voltage>", new BatteryCommand()));
    commandManager.addCommand(new Command("resistor", "Give yourself a resistor!", "/resistor <resistance>", new ResistorCommand()));
    commandManager.addCommand(new Command("room", "Interact with a room", "/room", new RoomCommand()));
  }

  private void registerListeners() {
    Game game = getGame();
    EventManager em = game.getEventManager();
    em.registerListener(new WorldObjectBreakListener(game));
  }

  public Logger getLogger() {
    return logger;
  }

  public SimulationManager getSimulationManager() {
    return simulationManager;
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

  public AnalysisMenu getAnalysisMenu() {
    return analysisMenu;
  }

  public static Electroid getInstance() {
    return INSTANCE;
  }

  public static void main(String[] args) {
    new Electroid();
  }

}
