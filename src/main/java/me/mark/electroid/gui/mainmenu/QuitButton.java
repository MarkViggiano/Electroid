package me.mark.electroid.gui.mainmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.ElectroidButton;

/**
 * color rgb = (124, 21, 7)
 */
public class QuitButton extends ElectroidButton {


  public QuitButton() {
    super("Quit", (int) (Electroid.getInstance().getGame().getHeight() * 0.44));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    if (!isShown()) return;
    Electroid electroid = Electroid.getInstance();
    electroid.getGame().stop();
  }


}
