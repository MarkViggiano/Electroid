package me.mark.electroid.gui.analysismenu;

import com.megaboost.Game;
import com.megaboost.components.GameComponent;
import com.megaboost.gui.menu.Menu;
import com.megaboost.position.Location;
import me.mark.electroid.electrical.ElectricalComponent;
import java.awt.Color;
import java.util.Arrays;

public class AnalysisMenu extends Menu {

  public AnalysisMenu() {
    super(new Location((Game.getInstance().getGameWidth() / 2) - 300, (Game.getInstance().getGameHeight() / 2) - 300),
        600,
        600,
        Arrays.asList(new ComponentLabel(new Location((Game.getInstance().getGameWidth() / 2) - 300, (Game.getInstance().getGameHeight() / 2) - 200), "COMPONENT"), new CloseButton("Close", 400)),
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
