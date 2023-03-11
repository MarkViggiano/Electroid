package me.mark.electroid.gui.mainmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.ElectroidButton;

public class LoadCircuitButton extends ElectroidButton {

  public LoadCircuitButton() {
    super("Load Circuit", (int) (Electroid.getInstance().getGame().getHeight() * 0.51));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    Electroid electroid = Electroid.getInstance();
    electroid.getMainMenu().setShown(false);
    electroid.getLoadMenu().setShown(true);
  }

}
