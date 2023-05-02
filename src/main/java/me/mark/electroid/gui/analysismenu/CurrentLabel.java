package me.mark.electroid.gui.analysismenu;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.gui.UpdatableComponent;
import java.awt.Color;
import java.awt.Font;

public class CurrentLabel extends TextComponent implements UpdatableComponent {

  public CurrentLabel(Location loc) {
    super(loc, "0", new Font("Times New Roman", Font.PLAIN, 20), Color.BLACK, 5, false);
  }

  @Override
  public void updateComponent(ElectricalComponent component) {
    setText(component.getCurrent() + " amps");
  }

  @Override
  public void updateComponent() {

  }

}
