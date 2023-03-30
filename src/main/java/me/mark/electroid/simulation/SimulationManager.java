package me.mark.electroid.simulation;

import com.megaboost.position.Location;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.electrical.VoltageSource;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.electrical.circuit.CircuitType;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.visual.CircuitFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SimulationManager {

  private final Electroid electroid;
  private final HashMap<ElectricalComponent, List<CircuitPath>> circuitPathsByStartNode;
  private final HashMap<ElectricalComponent, List<CircuitPath>> circuitPathsByEndNode;
  private CircuitType type;
  private SimulationStatus status;
  private VoltageSource voltageSource;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.circuitPathsByStartNode = new HashMap<>();
    this.circuitPathsByEndNode = new HashMap<>();
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
    this.circuitPathsByStartNode.clear();
    this.circuitPathsByEndNode.clear();

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
    System.out.println("IDENTIFIED: " + getNodes().size() + " Nodes");
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
    setStatus(SimulationStatus.STOPPED);
    ElectroidPlayer player = (ElectroidPlayer) getElectroid().getGame().getPlayer();
    player.sendMessage(Electroid.ERROR_PREFIX + error);
    if (getVoltageSource() == null) return;
    player.teleport(getVoltageSource().getBlock().getLocation());
  }

  public void addCircuitPath(CircuitPath path) {
    List<CircuitPath> circuitPaths = this.circuitPathsByStartNode.getOrDefault(path.getStartNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPathsByStartNode.put(path.getStartNode(), circuitPaths);

    circuitPaths = this.circuitPathsByEndNode.getOrDefault(path.getEndNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPathsByEndNode.put(path.getEndNode(), circuitPaths);
  }

  public HashMap<ElectricalComponent, List<CircuitPath>> getCircuitMap() {
    return circuitPathsByStartNode;
  }

  /**
   * NOTE: There may be repeats within this list, this is used for optimizing the circuits.
   * @param component | Component Node that can be a start or end node
   * @return | A list of circuit paths this node is a part of
   */
  public List<CircuitPath> getNodePaths(ElectricalComponent component) {
    List<CircuitPath> paths = getNodePathsByStartNode(component);
    paths.addAll(getNodePathsByEndNode(component));
    return paths;
  }

  public List<CircuitPath> getNodePathsByStartNode(ElectricalComponent component) {
    return this.circuitPathsByStartNode.getOrDefault(component, new ArrayList<>());
  }

  public List<CircuitPath> getNodePathsByEndNode(ElectricalComponent component) {
    return this.circuitPathsByEndNode.getOrDefault(component, new ArrayList<>());
  }

  public Collection<List<CircuitPath>> getCircuitPaths() {
    return this.circuitPathsByStartNode.values();
  }

  public List<ElectricalComponent> getNodes() {
    Set<ElectricalComponent> nodeSet = this.circuitPathsByStartNode.keySet();
    nodeSet.addAll(this.circuitPathsByEndNode.keySet());
    return new ArrayList<>(nodeSet);
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
