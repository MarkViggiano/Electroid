package me.mark.electroid.gui.loadmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.BackButton;

public class LoadToMainMenuButton extends BackButton {

  public LoadToMainMenuButton() {
    super(Electroid.getInstance().getMainMenu(), 100);
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    Electroid.getInstance().getLoadMenu().setShown(false);
    getPreviousMenu().setShown(true);
  }
}
