package me.mark.electroid.electrical.circuit;

import me.mark.electroid.electrical.ElectricalComponent;
import java.util.ArrayList;
import java.util.List;

public class CircuitPath {

  private final List<ElectricalComponent> components;
  private final CircuitPathConnection connection;
  private double pathResistance;

  public CircuitPath(ElectricalComponent startNode) {
    this.components = new ArrayList<>();
    this.connection = new CircuitPathConnection(startNode);
  }

  public ElectricalComponent getStartNode() {
    return getConnection().getStartNode();
  }

  public CircuitPathConnection getConnection() {
    return connection;
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
    this.connection.setStartNode(startNode);
  }

  public void setEndNode(ElectricalComponent endNode) {
    this.connection.setEndNode(endNode);
  }

  public ElectricalComponent getEndNode() {
    return getConnection().getEndNode();
  }

  public double getPathResistance() {
    return pathResistance;
  }

  public boolean isShorted() {
    return getPathResistance() == 0;
  }

}
