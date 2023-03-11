package me.mark.electroid.gui.loadmenu;

import com.megaboost.Game;
import com.megaboost.components.RenderType;
import com.megaboost.components.ShapeComponent;
import com.megaboost.components.type.Clickable;
import com.megaboost.components.type.Hoverable;
import com.megaboost.inputs.MouseClickType;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;
import me.mark.electroid.world.ElectroidWorld;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class WorldListing extends ShapeComponent implements Clickable, Hoverable {

  private static final Font FONT = new Font("Times New Roman", Font.BOLD, 30);
  private boolean hovered;
  private final ElectroidWorld world;
  private int pageIndex;

  public WorldListing(ElectroidWorld world, Location location) {
    super(location, 500, 100, RenderType.TOP, false);

    this.hovered = false;
    this.world = world;
    this.pageIndex = 0;
    setShown(false);
    Game.getInstance().getComponentManager().removeComponent(this);
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public ElectroidWorld getWorld() {
    return world;
  }

  @Override
  public Color getColor() {
    return Color.BLACK;
  }

  @Override
  public void tick() {
    checkHoverStatus();
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
    if (isHovered()) {
      g.setColor(Color.LIGHT_GRAY);
      Location location = getBoxLocation();
      g.fillRoundRect(location.getX() - 20, location.getY() - 20, getWidth() + 40, getHeight() + 40, 15, 15);
    }

    super.render(g, camera);

    String worldName = getWorld().getName();
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setFont(FONT);
    g2d.setColor(Color.WHITE);
    g2d.drawString(worldName, getLocation().getX() + 20, getLocation().getY() + 20);

  }

}
