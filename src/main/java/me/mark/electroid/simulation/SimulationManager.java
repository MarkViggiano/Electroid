package me.mark.electroid.simulation;

import com.megaboost.position.Location;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.electrical.VoltageSource;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.electrical.circuit.CircuitPathConnection;
import me.mark.electroid.electrical.circuit.CircuitType;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.visual.CircuitFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SimulationManager {

  private final Electroid electroid;
  private final List<ElectricalComponent> shortestPath;
  //private final HashMap<CircuitPathConnection, List<CircuitPath>> circuitNodeConnections;
  private final HashMap<ElectricalComponent, List<CircuitPath>> circuitPaths;
  private CircuitType type;
  private SimulationStatus status;
  private VoltageSource voltageSource;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.shortestPath = new ArrayList<>();
    //this.circuitNodeConnections = new HashMap<>();
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
    //this.circuitNodeConnections.clear();
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

    //optimize circuit
    //determine circuit type
    if (getCircuitPaths().size() == 1) setCircuitType(CircuitType.SERIES);

    //calculate values

  }

  public void stopSimulation(String error) {
    resetShortestPath();
    setStatus(SimulationStatus.STOPPED);
    ElectroidPlayer player = (ElectroidPlayer) getElectroid().getGame().getPlayer();
    player.sendMessage(Electroid.ERROR_PREFIX + error);
    if (getVoltageSource() == null) return;
    player.teleport(getVoltageSource().getBlock().getLocation());
  }

  public void addCircuitPath(CircuitPath path) {
    List<CircuitPath> circuitPaths = this.circuitPaths.getOrDefault(path.getStartNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPaths.put(path.getStartNode(), circuitPaths);

    //circuitPaths = this.circuitNodeConnections.getOrDefault(path.getCircuitPathConnection(), new ArrayList<>());
    //this.circuitNodeConnections.put(path.getCircuitPathConnection(), circuitPaths);
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

  public CircuitType getCircuitType() {
    return type;
  }

  public void setCircuitType(CircuitType type) {
    this.type = type;
  }

  public void setVoltageSource(VoltageSource voltageSource) {
    this.voltageSource = voltageSource;
  }

  public VoltageSource getVoltageSource() {
    return voltageSource;
  }
}
