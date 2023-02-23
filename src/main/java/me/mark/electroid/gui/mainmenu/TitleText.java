package me.mark.electroid.gui.mainmenu;

import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.gui.CenteredComponent;
import java.awt.Color;
import java.awt.Font;

public class TitleText extends TextComponent implements CenteredComponent {
  public TitleText() {
    super(
        new Location(0, 100),
        "Electroid",
        new Font("Times New Roman", Font.BOLD, 80),
        new Color(255, 217, 102),
        0,
        false
    );

    centerHorizontally();
  }

}
