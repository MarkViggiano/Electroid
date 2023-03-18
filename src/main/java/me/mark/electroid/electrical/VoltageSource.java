package me.mark.electroid.electrical;

import me.mark.electroid.items.Material;

public interface VoltageSource extends ElectricalComponent {

  /**
   * Voltage sources inherently don't have a current or resistance so these methods are defined in the interface
   * @param current |
   */
  @Override
  default void setCurrent(double current) {

  }

  @Override
  default double getCurrent() {
    return 0;
  }

  @Override
  default void setResistance(double resistance) {

  }

  @Override
  default double getResistance() {
    return 0;
  }

  /**
   * Voltage sources are inherently nodes as they connect portions of wires to other nodes.
   * @return true
   */
  @Override
  default boolean isNode() {
    return true;
  }

  @Override
  default void setNode(boolean isNode) {

  }

  /**
   * Voltage sources should always follow the rotation of the player cursor
   * @param shape | The shape of the wire mesh the voltage source is connecting to, 1 means object is present, 0 means no object is present.
   */
  @Override
  default ComponentState processComponentState(ComponentShape shape) {
    int mapSize = shape.getBlockMap().length;
    if (mapSize != 4) throw new Error("Wire shapes should have a length of 4, provided with length: " + mapSize);

    setComponentShape(shape);
    return new ComponentState(Material.VOLTAGE_SOURCE.getAsset(), getRotation());
  }

}
