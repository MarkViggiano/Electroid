package me.mark.electroid.gui.analysismenu;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.gui.CenteredComponent;
import java.awt.Color;
import java.awt.Font;

public class FieldLabel extends TextComponent implements CenteredComponent {
  public FieldLabel(Location loc, String text) {
    super(loc, text, new Font("Times New Roman", Font.BOLD, 30), Color.BLACK, 5, false);
  }

}
