package me.mark.electroid.gui.mainmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.ElectroidButton;

public class ResumeButton extends ElectroidButton {

  public ResumeButton() {
    super("Resume", (int) (Electroid.getInstance().getGame().getHeight() * 0.65));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {

  }
}
