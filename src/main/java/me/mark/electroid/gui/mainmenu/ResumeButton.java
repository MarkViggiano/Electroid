package me.mark.electroid.gui.mainmenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;

public class ResumeButton extends MainMenuButton {

  public ResumeButton() {
    super("Resume", (int) (Electroid.getInstance().getGame().getHeight() * 0.65));
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {

  }
}
