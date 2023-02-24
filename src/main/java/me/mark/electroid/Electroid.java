package me.mark.electroid;

import com.megaboost.Game;
import com.megaboost.world.WorldObjectPlaceManager;
import me.mark.electroid.gui.mainmenu.MainMenu;
import me.mark.electroid.utils.ImageUtil;
import me.mark.electroid.world.blocks.WireBlock;

/**
 * Primary Background Color: rgb(57, 50, 49)
 *
 */
public class Electroid {

  private final Game game;
  private final MainMenu mainMenu;
  private final ImageUtil componentSheet;
  private static Electroid INSTANCE;
  public static final String ERROR_PREFIX = "ERROR> ";
  public static final String SYSTEM_PREFIX = "SYSTEM> ";
  public static final String WARNING_PREFIX = "WARNING> ";

  private Electroid() {
    this.game = new Game("Electroid");
    INSTANCE = this;

    this.componentSheet = new ImageUtil("/components.png", 51, 51);
    this.mainMenu = new MainMenu();

    start();
  }

  private void start() {
    getMainMenu().setShown(true);
    registerWorldObjects();
  }

  private void registerWorldObjects() {
    WorldObjectPlaceManager wpm = getGame().getWorldObjectPlaceManager();
    wpm.addObject(new WireBlock());

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

  public static Electroid getInstance() {
    return INSTANCE;
  }

  public static void main(String[] args) {
    new Electroid();
  }

}
