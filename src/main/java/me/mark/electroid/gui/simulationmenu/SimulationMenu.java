package me.mark.electroid.gui.simulationmenu;

import com.megaboost.Game;
import com.megaboost.components.GameComponent;
import com.megaboost.gui.menu.Menu;
import com.megaboost.position.Location;
import me.mark.electroid.gui.UpdatableComponent;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

public class SimulationMenu extends Menu {

  public final static Font BUTTON_FONT = new Font("Times New Roman", Font.BOLD, 20);
  private final static Location START_LOC = new Location(Game.getInstance().getGameWidth() - 300, (Game.getInstance().getGameHeight() / 2) - 300);
  private final Color backgroundColor;

  public SimulationMenu() {
    super(START_LOC, 300, 600, Arrays.asList(
        new SimulationStatLabel(new Location(START_LOC.getX() + 20, START_LOC.getY() + 25), "STATUS:"),
        new SimulationStatusLabel(new Location(START_LOC.getX() + 20, START_LOC.getY() + 65)),
        new StartSimulationButton(new Location(START_LOC.getX() + 20, START_LOC.getY() + 550)),
        new StopSimulationButton(new Location(START_LOC.getX() + 170, START_LOC.getY() + 550))
    ), false);

    this.backgroundColor = new Color(57, 50, 49, 100);
    setShown(false);
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
    return backgroundColor;
  }

  public void updateMenu() {
    for (GameComponent component : getComponents()) {
      if (component instanceof UpdatableComponent) ((UpdatableComponent) component).updateComponent();
    }
  }

}
