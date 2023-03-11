package me.mark.electroid.gui;

import com.megaboost.components.type.Hoverable;
import com.megaboost.gui.button.Button;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;
import me.mark.electroid.Electroid;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * color rgb = (124, 21, 7)
 */
public abstract class ElectroidButton extends Button implements CenteredComponent, Hoverable {

  private boolean hovered;
  private static final int BORDER_WIDTH = 5;

  public ElectroidButton(String text, int yOffset) {
    super(
        new Location(0, Electroid.getInstance().getGame().getGameHeight() - yOffset),
        300,
        50,
        text,
        new Font("Times New Roman", Font.PLAIN, 30),
        Color.WHITE,
        new Color(124, 21, 7),
        5,
        false
    );

    this.hovered = false;
    centerHorizontally();
    getBoxLocation().setX((Electroid.getInstance().getGame().getGameWidth()/2) - (getBoxWidth()/2));
    centerText();

  }

  @Override
  public boolean isHovered() {
    return hovered;
  }

  @Override
  public void setHovered(boolean b) {
    this.hovered = b;
  }

  @Override
  public void onHover() {
  }

  @Override
  public void onUnHover() {

  }

  @Override
  public void tick() {
    checkHoverStatus();
    super.tick();
  }

  @Override
  public void render(Graphics g, Camera camera) {
    //stack button so no need to get position in camera
    if (isHovered()) {
      Location location = getBoxLocation();
      g.setColor(Color.BLACK);
      g.fillRoundRect(location.getX() - BORDER_WIDTH, location.getY() - BORDER_WIDTH, getBoxWidth() + (BORDER_WIDTH * 2), getBoxHeight() + (BORDER_WIDTH * 2), 10, 10);
    }
    super.render(g, camera);
  }

}
