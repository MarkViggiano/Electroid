package me.mark.electroid.gui.mainmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;

public class LoadCircuitButton extends MainMenuButton {

  public LoadCircuitButton() {
    super("Load Circuit", (int) (Electroid.getInstance().getGame().getHeight() * 0.51));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {

  }

}
