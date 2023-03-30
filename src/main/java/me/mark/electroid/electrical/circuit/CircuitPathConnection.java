package me.mark.electroid.electrical.circuit;

import me.mark.electroid.electrical.ElectricalComponent;

/**
 * May be necessary in the future but for now we're keeping it out.
 */
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
    if (connection.getStartNode() == getStartNode() && connection.getEndNode() == getEndNode()) return true;
    return connection.getStartNode() == getEndNode() && connection.getEndNode() == getStartNode();
  }

}
