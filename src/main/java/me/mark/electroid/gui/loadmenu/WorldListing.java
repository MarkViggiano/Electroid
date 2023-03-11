package me.mark.electroid.gui.loadmenu;

import com.megaboost.components.RenderType;
import com.megaboost.components.ShapeComponent;
import com.megaboost.components.type.Clickable;
import com.megaboost.components.type.Hoverable;
import com.megaboost.inputs.MouseClickType;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;

import java.awt.Color;
import java.awt.Graphics;

public class WorldListing extends ShapeComponent implements Clickable, Hoverable {

  private boolean hovered;

  public WorldListing(Location location) {
    super(location, 500, 100, RenderType.TOP, false);

    this.hovered = false;
  }

  @Override
  public Color getColor() {
    return Color.BLACK;
  }

  @Override
  public void tick() {

  }

  @Override
  public int getBoxHeight() {
    return getHeight();
  }

  @Override
  public int getBoxWidth() {
    return getWidth();
  }

  @Override
  public Location getBoxLocation() {
    return getLocation();
  }

  @Override
  public boolean isHovered() {
    return hovered;
  }

  @Override
  public void setHovered(boolean hovered) {
    this.hovered = hovered;
  }

  @Override
  public void onHover() {

  }

  @Override
  public void onUnHover() {

  }

  @Override
  public void onClick(MouseClickType mouseClickType) {

  }

  @Override
  public void render(Graphics g, Camera camera) {
    if (!isShown()) return;

    if (isHovered()) {
      g.setColor(Color.LIGHT_GRAY);
      Location location = getLocation();
      g.fillRoundRect(location.getX() - 10, location.getY() - 10, getWidth() + 20, getHeight() + 20, 15, 15);
    }

    super.render(g, camera);
  }

}
