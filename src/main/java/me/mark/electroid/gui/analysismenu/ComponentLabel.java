package me.mark.electroid.gui.analysismenu;

import com.megaboost.Game;
import com.megaboost.components.TextComponent;
import com.megaboost.position.Location;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.gui.CenteredComponent;
import java.awt.Color;
import java.awt.Font;

public class ComponentLabel extends TextComponent implements CenteredComponent, AnalysisComponent {

  private static final Font LABEL_FONT = new Font("Times New Roman", Font.BOLD, 40);

  public ComponentLabel(Location loc, String text) {
    super(loc, text, LABEL_FONT, Color.BLACK, 5, false);

  }

  @Override
  public void updateComponent(ElectricalComponent component) {
    setText(component.getClass().getSimpleName());
    setWidth(Game.getInstance().getGraphics().getFontMetrics(getFont()).stringWidth(getText()) + getOffsetMargin());
    centerHorizontally();
  }
}
