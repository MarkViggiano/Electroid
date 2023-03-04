package me.mark.electroid.electrical;

import me.mark.electroid.items.Material;
import java.util.HashMap;

public interface Wire extends ElectricalComponent {

  HashMap<ComponentShape, ComponentState> SHAPE_MAP = new HashMap<>() {{
    put(new ComponentShape(new int[] {1, 1, 1, 1}), new ComponentState(Material.FOUR_PRONGED_WIRE.getAsset(), 0));
  }};

  @Override
  default double getResistance() {
    return 0.1;
  }

  boolean isNode();
  void setNode(boolean isNode);

  /**
   * Process the state a wire is in
   * @param shape | The shape of the wire mesh the wire is connecting to, 1 means object is present, 0 means no object is present.
   */
  @Override
  default ComponentState processComponentState(ComponentShape shape) {
    if (shape.getBlockMap().length != 4) throw new Error("Wire shapes should have a length of 4, provided with length: " + shape.getBlockMap().length);

    return SHAPE_MAP.getOrDefault(shape, new ComponentState(Material.STRAIGHT_WIRE.getAsset(), getRotation()));

  }

}
