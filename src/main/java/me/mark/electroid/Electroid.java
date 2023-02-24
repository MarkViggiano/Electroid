package me.mark.electroid;

import com.megaboost.Game;
import me.mark.electroid.gui.mainmenu.MainMenu;

/**
 * Primary Background Color: rgb(57, 50, 49)
 *
 */
public class Electroid {

  private final Game game;
  private final MainMenu mainMenu;
  private static Electroid INSTANCE;
  public static final String ERROR_PREFIX = "ERROR> ";
  public static final String SYSTEM_PREFIX = "SYSTEM> ";
  public static final String WARNING_PREFIX = "WARNING> ";

  private Electroid() {
    this.game = new Game("Electroid");
    INSTANCE = this;

    this.mainMenu = new MainMenu();

    start();
  }

  private void start() {
    getMainMenu().setShown(true);
  }

  public Game getGame() {
    return game;
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
