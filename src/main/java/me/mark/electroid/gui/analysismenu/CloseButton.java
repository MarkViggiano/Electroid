package me.mark.electroid.gui.analysismenu;

import com.megaboost.inputs.MouseClickType;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.ElectroidButton;

public class CloseButton extends ElectroidButton {

  public CloseButton(String text, int yOffset) {
    super(text, yOffset);
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    Electroid.getInstance().getAnalysisMenu().setShown(false);
  }
}
