package me.mark.electroid.electrical;

import me.mark.electroid.electrical.wire.WireState;
import me.mark.electroid.items.Material;

public interface Wire extends ElectricalComponent {

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
  default WireState processWireState(int[] shape) {
    if (shape.length != 4) throw new Error("Wire shapes should have a length of 4, provided with length: " + shape.length);

    int top = shape[0];
    int right = shape[1];
    int down = shape[2];
    int left = shape[3];
    int combinedValue = top + right + down + left;

    //quick check if it's a node
    if (combinedValue >= 3) setNode(true);

    //Quad connection
    if (combinedValue == 4) {
      System.out.println("FOUR");
      return new WireState(Material.FOUR_PRONGED_WIRE.getAsset(), 0);
    }
    else if (combinedValue == 3) {
      //todo triple connection
    }
    else if (combinedValue == 2) {
      //todo corner value
    }
    else if (combinedValue == 1) return new WireState(Material.STRAIGHT_WIRE.getAsset(), getRotation());
    //Triple connection


    //corner



    return new WireState(Material.STRAIGHT_WIRE.getAsset(), getRotation());
  }

}
