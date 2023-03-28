package me.mark.electroid.simulation;

import com.megaboost.chat.ChatSender;
import com.megaboost.position.Location;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.electrical.VoltageSource;
import me.mark.electroid.visual.CircuitFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SimulationManager {

  private final Electroid electroid;
  private final List<ElectricalComponent> shortestPath;
  private final HashMap<ElectricalComponent, List<CircuitPath>> circuitPaths;
  private SimulationStatus status;
  private VoltageSource voltageSource;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.shortestPath = new ArrayList<>();
    this.circuitPaths = new HashMap<>();
    this.status = SimulationStatus.STOPPED;
  }

  private Electroid getElectroid() {
    return electroid;
  }

  public void simulateCircuit() {

    for (List<CircuitPath> nodePaths : getCircuitPaths()) {
      for (CircuitPath path : nodePaths) {
        for (ElectricalComponent component : path.getComponents()) {
          component.getBlock().clearFilters();
        }
      }
    }
    this.circuitPaths.clear();
    this.shortestPath.clear();

    ElectricalComponent voltageSource = getVoltageSource();
    if (voltageSource == null) {
      stopSimulation("Missing Voltage Source!");
      return;
    }

    int rotation = voltageSource.getRotation();
    rotation = rotation % 360;
    if (rotation < 0) rotation += 360;

    int xMod = 0;
    int yMod = 0;
    if (rotation == 0 || rotation == 360) {
      xMod = 1;
    } else if (rotation == 90) {
      yMod = 1;
    } else if (rotation == 180) {
      xMod = -1;
    } else if (rotation == 270) {
      yMod = -1;
    }

    Location startLoc = voltageSource.getBlock().getLocation();
    World world = startLoc.getWorld();
    Block block = world.getBlockByWorldPosition(startLoc.getX() + (xMod * 50), startLoc.getY() + (yMod * 50));
    if (block == null) {
      stopSimulation("Invalid starting block!");
      return;
    }

    if (block.getGameObject() == null) {
      stopSimulation("Missing starting component!");
      return;
    }

    long start = System.currentTimeMillis();
    ((ElectricalComponent) block.getGameObject()).processComponent(-xMod, -yMod, voltageSource.getBlock(), new CircuitPath(voltageSource));
    System.out.println("SIMULATION SUCCESSFULLY COMPLETED IN: " + (System.currentTimeMillis() - start) + "ms");
    System.out.println("CREATED: " + getCircuitPaths().size() + " Paths");
    for (List<CircuitPath> nodePaths : getCircuitPaths()) {
      for (CircuitPath path : nodePaths) {
        CircuitFilter filter = new CircuitFilter();
        for (ElectricalComponent component : path.getComponents()) {
          component.getBlock().addFilter(filter);
          component.resetComponent();
        }
      }
    }
  }

  public void stopSimulation(String error) {
    resetShortestPath();
    setStatus(SimulationStatus.STOPPED);
    ((ChatSender)getElectroid().getGame().getPlayer()).sendMessage(Electroid.ERROR_PREFIX + error);
  }

  public void addCircuitPath(CircuitPath path) {
    List<CircuitPath> circuitPaths = this.circuitPaths.getOrDefault(path.getStartNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPaths.put(path.getStartNode(), circuitPaths);
  }

  public HashMap<ElectricalComponent, List<CircuitPath>> getCircuitMap() {
    return circuitPaths;
  }

  public List<CircuitPath> getNodePaths(ElectricalComponent component) {
    return this.circuitPaths.getOrDefault(component, new ArrayList<>());
  }

  public Collection<List<CircuitPath>> getCircuitPaths() {
    return this.circuitPaths.values();
  }

  public List<ElectricalComponent> getShortestPath() {
    return shortestPath;
  }

  private void resetShortestPath() {
    this.shortestPath.clear();
  }

  public SimulationStatus getStatus() {
    return status;
  }

  public void setStatus(SimulationStatus status) {
    this.status = status;
  }

  public void setVoltageSource(VoltageSource voltageSource) {
    this.voltageSource = voltageSource;
  }

  public VoltageSource getVoltageSource() {
    return voltageSource;
  }
}
