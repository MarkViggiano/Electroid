package me.mark.electroid.visual;

import com.megaboost.visuals.Filter;
import java.awt.Color;
import java.util.Random;

public class CircuitFilter extends Filter {

  private static final Random R = new Random();

  public CircuitFilter() {
    super(new Color(R.nextInt(255), R.nextInt(255), R.nextInt(255)), 120);
  }
}
