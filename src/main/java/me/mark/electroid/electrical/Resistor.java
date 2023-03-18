package me.mark.electroid.electrical;

import me.mark.electroid.items.Material;

public interface Resistor extends ElectricalComponent {

  /**
   * Resistors are inherently not nodes as they are basically a wire with extra resistance.
   * @return true
   */
  @Override
  default boolean isNode() {
    return false;
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
    return new ComponentState(Material.RESISTOR.getAsset(), getRotation());
  }

}
