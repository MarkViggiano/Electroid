package me.mark.electroid.electrical;

import me.mark.electroid.items.Material;
import java.util.HashMap;

public interface Wire extends ElectricalComponent {

  HashMap<ComponentShape, ComponentState> SHAPE_MAP = new HashMap<>() {{
    //Straight possibilities
    ComponentState straight_vertical = new ComponentState(Material.STRAIGHT_WIRE.getAsset(), 90);
    put(new ComponentShape(new int[] {1, 0, 0, 0}), straight_vertical);
    put(new ComponentShape(new int[] {0, 0, 1, 0}), straight_vertical);
    put(new ComponentShape(new int[] {1, 0, 1, 0}), straight_vertical);

    ComponentState straight_horizontal = new ComponentState(Material.STRAIGHT_WIRE.getAsset(), 0);
    put(new ComponentShape(new int[] {0, 1, 0, 0}), straight_horizontal);
    put(new ComponentShape(new int[] {0, 0, 0, 1}), straight_horizontal);
    put(new ComponentShape(new int[] {0, 1, 0, 1}), straight_horizontal);
    //curved possibilities
    put(new ComponentShape(new int[] {1, 0, 0, 1}), new ComponentState(Material.CURVED_WIRE.getAsset(), 0));
    put(new ComponentShape(new int[] {1, 1, 0, 0}), new ComponentState(Material.CURVED_WIRE.getAsset(), 90));
    put(new ComponentShape(new int[] {0, 1, 1, 0}), new ComponentState(Material.CURVED_WIRE.getAsset(), 180));
    put(new ComponentShape(new int[] {0, 0, 1, 1}), new ComponentState(Material.CURVED_WIRE.getAsset(), 270));
    //3 prong node possibilities
    put(new ComponentShape(new int[] {1, 0, 1, 1}), new ComponentState(Material.THREE_PRONGED_WIRE.getAsset(), 0));
    put(new ComponentShape(new int[] {1, 1, 0, 1}), new ComponentState(Material.THREE_PRONGED_WIRE.getAsset(), 90));
    put(new ComponentShape(new int[] {1, 1, 1, 0}), new ComponentState(Material.THREE_PRONGED_WIRE.getAsset(), 180));
    put(new ComponentShape(new int[] {0, 1, 1, 1}), new ComponentState(Material.THREE_PRONGED_WIRE.getAsset(), 270));
    //4 pong node possibility
    put(new ComponentShape(new int[] {1, 1, 1, 1}), new ComponentState(Material.FOUR_PRONGED_WIRE.getAsset(), 0));
  }};

  @Override
  default double getResistance() {
    return 0;
  }

  boolean isNode();
  void setNode(boolean isNode);

  /**
   * Process the state a wire is in
   * @param shape | The shape of the wire mesh the wire is connecting to, 1 means object is present, 0 means no object is present.
   */
  @Override
  default ComponentState processComponentState(ComponentShape shape) {
    int[] blockMap = shape.getBlockMap();
    int mapSize = blockMap.length;
    if (mapSize != 4) throw new Error("Wire shapes should have a length of 4, provided with length: " + mapSize);
    if (blockMap[0] + blockMap[1] + blockMap[2] + blockMap[3] >= 3) setNode(true);

    return SHAPE_MAP.getOrDefault(shape, new ComponentState(Material.STRAIGHT_WIRE.getAsset(), getRotation()));

  }

}
