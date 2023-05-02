package me.mark.electroid.gui.simulationmenu;

import com.megaboost.components.type.Hoverable;
import com.megaboost.gui.button.Button;
import com.megaboost.inputs.MouseClickType;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.gui.CenteredComponent;
import me.mark.electroid.gui.UpdatableComponent;
import me.mark.electroid.simulation.SimulationStatus;
import java.awt.Color;
import java.awt.Graphics;

public class StopSimulationButton extends Button implements UpdatableComponent, Hoverable, CenteredComponent {

  private boolean disabled;
  private final static Color COLOR_ENABLED = new Color(255, 45, 50);
  private final static Color COLOR_DISABLED = new Color(100, 100, 100);
  private boolean hovered;
  private static final int BORDER_WIDTH = 5;

  public StopSimulationButton(Location loc) {
    super(loc, 120, 30, "STOP", SimulationMenu.BUTTON_FONT, Color.BLACK, 5, false);
    this.disabled = true;
    setBoxColor(COLOR_DISABLED);
    centerText();
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {
    if (isDisabled()) return;

    Electroid.getInstance().getSimulationManager().stopSimulator();
  }

  @Override
  public void updateComponent(ElectricalComponent component) {

  }

  @Override
  public void updateComponent() {
    if (Electroid.getInstance().getSimulationManager().getStatus() == SimulationStatus.STOPPED) {
      setBoxColor(COLOR_DISABLED);
      this.disabled = true;
    } else {
      setBoxColor(COLOR_ENABLED);
      this.disabled = false;
    }
  }

  public boolean isDisabled() {
    return disabled;
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
    setColor(Color.WHITE);
  }

  @Override
  public void onUnHover() {
    setColor(Color.BLACK);
  }

  @Override
  public void tick() {
    super.tick();
    checkHoverStatus();
  }

  @Override
  public void render(Graphics g, Camera camera) {
    //stack button so no need to get position in camera
    if (isHovered()) {
      Location location = getBoxLocation();
      g.setColor(Color.WHITE);
      g.fillRoundRect(location.getX() - BORDER_WIDTH, location.getY() - BORDER_WIDTH, getBoxWidth() + (BORDER_WIDTH * 2), getBoxHeight() + (BORDER_WIDTH * 2), 10, 10);
    }
    super.render(g, camera);
  }

}
