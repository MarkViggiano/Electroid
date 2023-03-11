package me.mark.electroid.gui.loadmenu;

import com.megaboost.gui.menu.FullScreenMenu;
import me.mark.electroid.gui.TitleText;

import java.awt.Color;
import java.util.Arrays;

public class LoadMenu extends FullScreenMenu {

  private final Color backgroundColor;

  public LoadMenu() {
    super(Arrays.asList(
        new TitleText("Load Circuit"),
        new LoadToMainMenuButton()));

    this.backgroundColor = new Color(57, 50, 49);
    createWorldList();
  }

  private void createWorldList() {

  }

  @Override
  public int getKey() {
    return 0;
  }

  @Override
  public boolean isKeyToggled() {
    return false;
  }

  @Override
  public Color getColor() {
    return backgroundColor;
  }
}
