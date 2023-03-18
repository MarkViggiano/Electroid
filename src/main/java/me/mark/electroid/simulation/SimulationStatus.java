package me.mark.electroid.simulation;

import java.awt.Color;

public enum SimulationStatus {

  COMPLETED(Color.GREEN),
  ERROR(Color.RED),
  PROCESSING(Color.YELLOW),
  STOPPED(Color.DARK_GRAY);

  private final Color color;

  SimulationStatus(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

}
