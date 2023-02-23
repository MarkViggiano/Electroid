package me.mark.electroid.gui.mainmenu;

import com.megaboost.gui.menu.FullScreenMenu;
import java.awt.Color;
import java.util.Arrays;

public class MainMenu extends FullScreenMenu {

  private final Color backgroundColor;

  public MainMenu() {
    super(Arrays.asList(
        new TitleText(),
        new ResumeButton(),
        new NewCircuitButton(),
        new LoadCircuitButton(),
        new QuitButton()));

    this.backgroundColor = new Color(57, 50, 49);
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
