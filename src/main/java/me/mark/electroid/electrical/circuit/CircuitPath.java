package me.mark.electroid.electrical.circuit;

import me.mark.electroid.electrical.ElectricalComponent;
import java.util.ArrayList;
import java.util.List;

public class CircuitPath {

  private ElectricalComponent startNode;
  private final List<ElectricalComponent> components;
  private ElectricalComponent endNode;
  private double pathResistance;

  public CircuitPath(ElectricalComponent startNode) {
    this.startNode = startNode;
    this.components = new ArrayList<>();
  }

  public ElectricalComponent getStartNode() {
    return startNode;
  }

  public List<ElectricalComponent> getComponents() {
    return components;
  }

  public void addComponent(ElectricalComponent component) {
    this.components.add(component);
    component.setPath(this);

    this.pathResistance = 0;
    for (ElectricalComponent electricalComponent : getComponents()) this.pathResistance += electricalComponent.getResistance();
  }

  public void setStartNode(ElectricalComponent startNode) {
    this.startNode = startNode;
  }

  public void setEndNode(ElectricalComponent endNode) {
    this.endNode = endNode;
  }

  public ElectricalComponent getEndNode() {
    return endNode;
  }

  public double getPathResistance() {
    return pathResistance;
  }

  public boolean isShorted() {
    return getPathResistance() == 0;
  }

}
