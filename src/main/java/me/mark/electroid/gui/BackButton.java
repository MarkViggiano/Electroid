package me.mark.electroid.gui;

import com.megaboost.gui.menu.Menu;

public abstract class BackButton extends ElectroidButton {

  private final Menu previousMenu;

  public BackButton(Menu previousMenu, int yOffset) {
    super("Back", yOffset);

    this.previousMenu = previousMenu;
  }

  public Menu getPreviousMenu() {
    return previousMenu;
  }

}
