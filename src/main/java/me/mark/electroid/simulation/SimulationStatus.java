package me.mark.electroid.simulation;

import java.awt.Color;

public enum SimulationStatus {

  COMPLETED("Completed", Color.GREEN),
  ERROR("Error", Color.RED),
  PROCESSING("Processing", Color.YELLOW),
  STOPPED("Stopped", Color.DARK_GRAY);

  private final Color color;
  private final String name;

  SimulationStatus(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public String getName() {
    return name;
  }
}
