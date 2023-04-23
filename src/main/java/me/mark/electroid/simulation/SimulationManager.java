package me.mark.electroid.simulation;

import com.megaboost.position.Location;
import com.megaboost.visuals.Filter;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.electrical.VoltageSource;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.electrical.circuit.CircuitType;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.visual.CircuitFilter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

    //optimize circuit
    optimizeCircuit();
    //determine circuit type
    if (getCircuitPaths().size() == 1) setCircuitType(CircuitType.SERIES);

    //calculate values

    for (ElectricalComponent node : getNodes()) node.getBlock().addFilter(new Filter(Color.WHITE));
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

  private void optimizeCircuit() {
    List<ElectricalComponent> nodes = getNodes();
    if (nodes.size() == 1) return;

    HashSet<CircuitPath> paths = new HashSet<>();
    List<ElectricalComponent> deletedNodes = new ArrayList<>();
    boolean nodeOptimized = true;
    ElectricalComponent startNode;
    ElectricalComponent endNode;
    ElectricalComponent deletedNode;

    System.out.println("Starting node count: " + nodes.size());

    while (nodeOptimized) {
      nodeOptimized = false;
      for (ElectricalComponent node : nodes) {
        System.out.println("Looping Nodes");
        if (deletedNodes.contains(node)) continue;
        for (CircuitPath path : getNodePaths(node)) {
          System.out.println("Optimizing paths");
          startNode = path.getStartNode();
          endNode = path.getEndNode();
          if (path.getPathResistance() == 0) {
            System.out.println("Path optimized");
            if (startNode == endNode) continue;

            deletedNode = endNode;
            if (endNode instanceof VoltageSource) deletedNode = startNode;


            deletedNodes.add(deletedNode);
            paths.addAll(getNodePaths(deletedNode));
            for (CircuitPath optimizedPath : paths) {
              if (optimizedPath.getStartNode() == deletedNode) optimizedPath.setStartNode(startNode);
              if (optimizedPath.getEndNode() == endNode) optimizedPath.setEndNode(startNode);
            }
            nodeOptimized = true;

          }

          System.out.println("Path not optimized!");
          paths.add(path);

        }

        overrideNodeRegistration(node, new ArrayList<>(paths));
        paths.clear();

      }

      nodes.removeAll(deletedNodes);
      for (ElectricalComponent node : deletedNodes) deleteNodeRegistration(node);
      deletedNodes.clear();
      System.out.println("Remaining Nodes to Check: " + nodes.size());
    }

    //Temporary logging
    System.out.println("Remaining Nodes: " + getNodes().size());


  }

  public void stopSimulation(String error) {
    setStatus(SimulationStatus.STOPPED);
    ElectroidPlayer player = (ElectroidPlayer) getElectroid().getGame().getPlayer();
    player.sendMessage(Electroid.ERROR_PREFIX + error);
  }

  public void addCircuitPath(CircuitPath path) {
    List<CircuitPath> circuitPaths = this.circuitPathsByStartNode.getOrDefault(path.getStartNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPathsByStartNode.put(path.getStartNode(), circuitPaths);

    circuitPaths = this.circuitPathsByEndNode.getOrDefault(path.getEndNode(), new ArrayList<>());
    circuitPaths.add(path);
    this.circuitPathsByEndNode.put(path.getEndNode(), circuitPaths);
  }

  public void overrideNodeRegistration(ElectricalComponent node, List<CircuitPath> paths) {
    this.circuitPathsByStartNode.put(node, paths);
  }

  public void deleteNodeRegistration(ElectricalComponent node) {
    this.circuitPathsByStartNode.remove(node);
    this.circuitPathsByEndNode.remove(node);
  }

  public HashMap<ElectricalComponent, List<CircuitPath>> getCircuitMap() {
    return circuitPathsByStartNode;
  }

  /**
   * @param component | Component Node that can be a start or end node
   * @return | A list of circuit paths this node is a part of
   */
  public List<CircuitPath> getNodePaths(ElectricalComponent component) {
    Set<CircuitPath> startSet = new HashSet<>(getNodePathsByStartNode(component));
    Set<CircuitPath> endSet = new HashSet<>(getNodePathsByEndNode(component));
    startSet.addAll(endSet);
    return new ArrayList<>(startSet);
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
    Set<ElectricalComponent> startSet = new HashSet<>(this.circuitPathsByStartNode.keySet());
    Set<ElectricalComponent> endSet = new HashSet<>(this.circuitPathsByEndNode.keySet());
    startSet.addAll(endSet);
    return new ArrayList<>(startSet);
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
