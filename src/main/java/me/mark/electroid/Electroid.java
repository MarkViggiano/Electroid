package me.mark.electroid;

import com.megaboost.Game;
import me.mark.electroid.gui.menus.MainMenu;

public class Electroid {

  private final Game game;
  private final MainMenu mainMenu;
  private static Electroid INSTANCE;

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
