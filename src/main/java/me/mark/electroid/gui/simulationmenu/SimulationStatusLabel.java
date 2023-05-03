package me.mark.electroid.gui.simulationmenu;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.gui.UpdatableComponent;
import me.mark.electroid.simulation.SimulationManager;
import java.awt.Color;
import java.awt.Font;

public class SimulationStatusLabel extends TextComponent implements UpdatableComponent {

  public SimulationStatusLabel(Location loc) {
    super(loc, "", new Font("Times New Roman", Font.BOLD, 20), Color.BLACK, 5, false);

    SimulationManager sm = Electroid.getInstance().getSimulationManager();
    setText(sm.getStatus().getName());
    setColor(sm.getStatus().getColor());
  }

  @Override
  public void updateComponent(ElectricalComponent component) {

  }

  @Override
  public void updateComponent() {
    SimulationManager sm = Electroid.getInstance().getSimulationManager();
    setText(sm.getStatus().getName());
    setColor(sm.getStatus().getColor());
  }
}
