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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SimulationManager {

  private final Electroid electroid;
  private final LinkedHashMap<CircuitPathConnection, List<CircuitPath>> paths;
  private CircuitType type;
  private SimulationStatus status;
  private VoltageSource voltageSource;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.paths = new LinkedHashMap<>();
    this.status = SimulationStatus.STOPPED;
  }

  private Electroid getElectroid() {
    return electroid;
  }

  public void simulateCircuit() {
    this.paths.clear();
    this.status = SimulationStatus.PROCESSING;

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


    AtomicReference<Double> voltage = new AtomicReference<>(voltageSource.getVoltage());
    LinkedHashMap<CircuitPathConnection, List<CircuitPath>> pathsMap = getPaths();
    pathsMap.forEach((connection, paths) -> {
      double voltage_drop = 0.0;
      for (CircuitPath path : paths) {
        voltage_drop += (1/path.getPathResistance());
      }

      double finalVoltage_drop = 1 / voltage_drop;
      voltage.updateAndGet(v -> v - finalVoltage_drop);

    });

    System.out.println("SIMULATION SUCCESSFULLY COMPLETED IN: " + (System.currentTimeMillis() - start) + "ms");

  }

  public void logSimulationError(String error) {
    setStatus(SimulationStatus.STOPPED);
    ElectroidPlayer player = (ElectroidPlayer) getElectroid().getGame().getPlayer();
    player.sendMessage(Electroid.ERROR_PREFIX + error);
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
