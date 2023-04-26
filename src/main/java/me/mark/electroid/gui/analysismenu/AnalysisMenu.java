package me.mark.electroid.gui.analysismenu;

import com.megaboost.Game;
import com.megaboost.components.GameComponent;
import com.megaboost.gui.menu.Menu;
import com.megaboost.position.Location;
import me.mark.electroid.electrical.ElectricalComponent;
import java.awt.Color;
import java.util.Arrays;

public class AnalysisMenu extends Menu {

  private static final Location START_LOC = new Location((Game.getInstance().getGameWidth() / 2) - 300, (Game.getInstance().getGameHeight() / 2) - 300);

  public AnalysisMenu() {
    super(START_LOC,
        600,
        600,
        Arrays.asList(
            new ComponentLabel(new Location(START_LOC.getX(), START_LOC.getY() + 50), "COMPONENT"),
            new FieldLabel(new Location(START_LOC.getX() + 100, START_LOC.getY() + 200), "Voltage: "),
            new VoltageLabel(new Location(START_LOC.getX() + 250, START_LOC.getY() + 210)),
            new FieldLabel(new Location(START_LOC.getX() + 100, START_LOC.getY() + 250), "Current: "),
            new CurrentLabel(new Location(START_LOC.getX() + 250, START_LOC.getY() + 260)),
            new FieldLabel(new Location(START_LOC.getX() + 100, START_LOC.getY() + 300), "Resistance: "),
            new ResistanceLabel(new Location(START_LOC.getX() + 250, START_LOC.getY() + 310)),
            new CloseButton("Close", 300)),
        false);

  }

  public void open(ElectricalComponent electricalComponent) {
    for (GameComponent component : getComponents()) {
      if (component instanceof AnalysisComponent) ((AnalysisComponent) component).updateComponent(electricalComponent);
    }

    setShown(true);

  }

  @Override
  public int getKey() {
    return 0;
  }

  @Override
  public boolean isKeyToggled() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.WHITE;
  }

}
