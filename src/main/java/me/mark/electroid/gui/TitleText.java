package me.mark.electroid.gui;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import java.awt.Color;
import java.awt.Font;

public class TitleText extends TextComponent implements CenteredComponent {
  public TitleText(String text) {
    super(
        new Location(0, 100),
        text,
        new Font("Times New Roman", Font.BOLD, 80),
        new Color(255, 217, 102),
        0,
        false
    );

    centerHorizontally();
  }

}
