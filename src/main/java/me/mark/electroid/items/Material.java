package me.mark.electroid.items;

import me.mark.electroid.Electroid;
import java.awt.Image;

public class Material extends com.megaboost.items.Material {

  public static final Material STRAIGHT_WIRE = new Material("Straight Wire", 1, Electroid.getInstance().getComponentSheet().getImageFromPosition(3,0), true);
  public static final Material CURVED_WIRE = new Material("Curved Wire", 2, Electroid.getInstance().getComponentSheet().getImageFromPosition(6,0), true);
  public static final Material THREE_PRONGED_WIRE = new Material("Three Pronged Wire", 3, Electroid.getInstance().getComponentSheet().getImageFromPosition(5,0), true);
  public static final Material FOUR_PRONGED_WIRE = new Material("Four Pronged Wire", 4, Electroid.getInstance().getComponentSheet().getImageFromPosition(4,0), true);
  public static final Material VOLTAGE_SOURCE = new Material("Voltage Source", 5, Electroid.getInstance().getComponentSheet().getImageFromPosition(7,0), true);
  public static final Material RESISTOR = new Material("Resistor", 6, Electroid.getInstance().getComponentSheet().getImageFromPosition(2,0), true);

  public Material(String name, int id, Image asset, boolean stackable) {
    super(name, id, asset, stackable);
  }
}
