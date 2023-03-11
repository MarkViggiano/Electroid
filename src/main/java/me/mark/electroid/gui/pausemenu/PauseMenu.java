package me.mark.electroid.gui.pausemenu;

import com.megaboost.GameState;
import com.megaboost.components.GameComponent;
import com.megaboost.gui.menu.FullScreenMenu;
import me.mark.electroid.Electroid;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class PauseMenu extends FullScreenMenu {

  private final Color color;

  public PauseMenu() {
    super(Collections.emptyList());

    this.color = new Color(0, 0, 0, 150);
  }

  @Override
  public void setShown(boolean shown) {
    if (Electroid.getInstance().getGame().getGameState() == GameState.MAIN_MENU) return;
    super.setShown(shown);
  }

  @Override
  public int getKey() {
    return KeyEvent.VK_ESCAPE;
  }

  @Override
  public boolean isKeyToggled() {
    return true;
  }

  @Override
  public Color getColor() {
    return color;
  }
}
