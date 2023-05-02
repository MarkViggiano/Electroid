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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationManager {

  private final Electroid electroid;
  private final List<ElectricalComponent> deletedNodes;
  private final LinkedHashMap<CircuitPathConnection, List<CircuitPath>> paths;
  private final HashSet<ElectricalComponent> components;
  private CircuitType type;
  private SimulationStatus status;
  private VoltageSource voltageSource;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.deletedNodes = new ArrayList<>();
    this.paths = new LinkedHashMap<>();
    this.components = new HashSet<>();
    this.status = SimulationStatus.STOPPED;
  }

  private Electroid getElectroid() {
    return electroid;
  }

  public void simulateCircuit() {
    this.paths.clear();
    this.deletedNodes.clear();
    this.status = SimulationStatus.PROCESSING;
    for (ElectricalComponent component : getComponents()) component.resetComponent();
    this.components.clear();
    getElectroid().getSimulationMenu().updateMenu();

    ElectricalComponent voltageSource = getVoltageSource();
    if (voltageSource == null) {
      logSimulationError("Missing Voltage Source!");
      return;
    }

    int[] blockMap = voltageSource.getComponentShape().getBlockMap();
    if (blockMap[0] + blockMap[1] + blockMap[2] + blockMap[3] > 2) {
      logSimulationError("Voltage Sources can only have two wires attached!");
      return;
    }

    long start = System.currentTimeMillis();
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
      logSimulationError("Invalid starting block! Failed after: " + (System.currentTimeMillis() - start) + "ms");
      return;
    }

    if (block.getGameObject() == null) {
      logSimulationError("Missing starting component! Failed after: " + (System.currentTimeMillis() - start) + "ms");
      return;
    }

    ((ElectricalComponent) block.getGameObject()).processComponent(-xMod, -yMod, voltageSource.getBlock(), new CircuitPath(voltageSource));

    if (this.paths.size() == 0) {
      logSimulationError("Short circuit detected! Failed after: " + (System.currentTimeMillis() - start) + "ms");
      return;
    }


    double voltage = voltageSource.getVoltage();
    LinkedHashMap<CircuitPathConnection, List<CircuitPath>> pathsMap = getPaths();

    //calculate total resistance
    double totalResistance = 0;
    for (Map.Entry<CircuitPathConnection, List<CircuitPath>> entry : pathsMap.entrySet()) {
      if (entry.getValue().size() > 1) {
        double parallel_resistance = 0;
        for (CircuitPath path : entry.getValue()) {
          parallel_resistance += (1 / path.getPathResistance());
        }
        totalResistance += (1 / parallel_resistance);
      } else {
        for (CircuitPath path : entry.getValue()) {
          totalResistance += path.getPathResistance();
        }
      }

    }

    //calculate current
    double current = voltage / totalResistance;
    boolean isSeries;
    for (Map.Entry<CircuitPathConnection, List<CircuitPath>> entry : pathsMap.entrySet()) {
      isSeries = entry.getValue().size() == 1;

      double total_current = 0;
      double path_current;
      for (CircuitPath path : entry.getValue()) {
        if (isSeries) for (ElectricalComponent component : path.getComponents()) component.setCurrent(current);
        else {
          path_current = voltage / path.getPathResistance();
          total_current += path_current;
          for (ElectricalComponent component : path.getComponents()) component.setCurrent(path_current);
        }
      }

      if (!isSeries) current = total_current;

    }

    //calculate voltage drop
    double voltage_drop = 0;
    for (Map.Entry<CircuitPathConnection, List<CircuitPath>> entry : pathsMap.entrySet()) {

      double parallel_resistance = 0;
      if (entry.getValue().size() > 1) {
        for (CircuitPath path : entry.getValue()) {
          parallel_resistance += (1 / path.getPathResistance());
        }
        parallel_resistance = 1 / parallel_resistance;
      }

      for (CircuitPath path : entry.getValue()) {
        for (ElectricalComponent component : path.getComponents()) {
          if (component instanceof VoltageSource) continue;
          component.setVoltage(voltage - voltage_drop);
          if (entry.getValue().size() == 1) voltage_drop += (current * component.getResistance());
        }
      }

      voltage_drop += (current * parallel_resistance);

    }

    //highlight paths
    CircuitFilter filter;
    for (Map.Entry<CircuitPathConnection, List<CircuitPath>> entry : pathsMap.entrySet()) {
      filter = new CircuitFilter();
      for (CircuitPath path : entry.getValue()) {
        for (ElectricalComponent component : path.getComponents()) component.getBlock().addFilter(filter);
      }
    }

    System.out.println("SIMULATION SUCCESSFULLY COMPLETED IN: " + (System.currentTimeMillis() - start) + "ms");
    setStatus(SimulationStatus.COMPLETED);
    getElectroid().getSimulationMenu().updateMenu();
  }

  public void logSimulationError(String error) {
    ElectroidPlayer player = (ElectroidPlayer) getElectroid().getGame().getPlayer();
    player.sendMessage(Electroid.ERROR_PREFIX + error);
    stopSimulator();
  }

  public void stopSimulator() {
    this.paths.clear();
    this.deletedNodes.clear();
    this.status = SimulationStatus.STOPPED;
    for (ElectricalComponent component : getComponents()) component.resetComponent();
    this.components.clear();
    getElectroid().getSimulationMenu().updateMenu();
  }

  public void addDeletedNode(ElectricalComponent node) {
    this.deletedNodes.add(node);
  }

  public boolean isNodeDeleted(ElectricalComponent node) {
    return this.deletedNodes.contains(node);
  }

  public void addCircuitPath(CircuitPath path) {
    if (path.getPathResistance() == 0) return;
    List<CircuitPath> paths = this.paths.getOrDefault(path.getConnection(), new ArrayList<>());
    paths.add(path);
    this.paths.put(path.getConnection(), paths);
  }

  public LinkedHashMap<CircuitPathConnection, List<CircuitPath>> getPaths() {
    return paths;
  }

  public SimulationStatus getStatus() {
    return status;
  }

  public void setStatus(SimulationStatus status) {
    this.status = status;
  }

  public HashSet<ElectricalComponent> getComponents() {
    return components;
  }

  public void addComponent(ElectricalComponent component) {
    this.components.add(component);
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
