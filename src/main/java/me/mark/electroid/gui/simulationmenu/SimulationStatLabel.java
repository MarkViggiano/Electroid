package me.mark.electroid.gui.simulationmenu;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.gui.CenteredComponent;
import java.awt.Color;
import java.awt.Font;

public class SimulationStatLabel extends TextComponent implements CenteredComponent {

  private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 30);

  public SimulationStatLabel(Location loc, String text) {
    super(loc, text, LABEL_FONT, Color.BLACK, 5, false);
  }

}
