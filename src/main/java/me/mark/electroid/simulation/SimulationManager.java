package me.mark.electroid.simulation;

import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {

  private final Electroid electroid;
  private final List<ElectricalComponent> shortestPath;
  private SimulationStatus status;

  public SimulationManager(Electroid electroid) {
    this.electroid = electroid;
    this.shortestPath = new ArrayList<>();
    this.status = SimulationStatus.STOPPED;
  }

  private Electroid getElectroid() {
    return electroid;
  }

  public void startSimulation() {

  }

  public void stopSimulation() {
    resetShortestPath();
    setStatus(SimulationStatus.STOPPED);
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

}
