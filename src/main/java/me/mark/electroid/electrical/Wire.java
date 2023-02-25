package me.mark.electroid.electrical;

public interface Wire extends ElectricalComponent {

  @Override
  default double getResistance() {
    return 0.1;
  }

  boolean isNode();
  void setNode(boolean isNode);

}
