package me.mark.electroid.listeners;

import com.megaboost.Game;
import com.megaboost.events.EventHandler;
import com.megaboost.events.worldobject.WorldObjectBreakEvent;
import com.megaboost.listeners.ListenerBase;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.simulation.SimulationStatus;

public class WorldObjectBreakListener extends ListenerBase {

  public WorldObjectBreakListener(Game game) {
    super(game);
  }

  @EventHandler
  public void worldObjectBreakEvent(WorldObjectBreakEvent e) {
    if (!(e.getObject() instanceof ElectricalComponent)) return;
    if (Electroid.getInstance().getSimulationManager().getStatus() != SimulationStatus.COMPLETED) return;

    e.setCancelled(true);
    Electroid.getInstance().getAnalysisMenu().open((ElectricalComponent) e.getObject());
  }

}
