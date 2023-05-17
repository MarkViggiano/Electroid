package me.mark.electroid.electrical.circuit;

import me.mark.electroid.electrical.ElectricalComponent;
import java.util.Objects;

public class CircuitPathConnection {

  private ElectricalComponent startNode;
  private ElectricalComponent endNode;

  public CircuitPathConnection() {

  }

  public CircuitPathConnection(ElectricalComponent startNode) {
    this.startNode = startNode;
  }

  public CircuitPathConnection(ElectricalComponent startNode, ElectricalComponent endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public CircuitPathConnection(CircuitPathConnection connection) {
    this.startNode = connection.getStartNode();
    this.endNode = connection.getEndNode();
  }

  public ElectricalComponent getStartNode() {
    return startNode;
  }

  public void setStartNode(ElectricalComponent startNode) {
    this.startNode = startNode;
  }

  public ElectricalComponent getEndNode() {
    return endNode;
  }

  public void setEndNode(ElectricalComponent endNode) {
    this.endNode = endNode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CircuitPathConnection connection = (CircuitPathConnection) o;
    if (getStartNode() == connection.getStartNode() && getEndNode() == connection.getEndNode()) return true;
    return getEndNode() == connection.getStartNode() && getStartNode() == connection.getEndNode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(1);
  }
}
