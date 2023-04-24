package me.mark.electroid.electrical;

import com.megaboost.position.Location;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.simulation.SimulationManager;
import java.awt.Image;

public interface ElectricalComponent {

  CircuitPath getPath();
  void setPath(CircuitPath path);
  boolean isNode();
  void setNode(boolean isNode);
  Block getBlock();
  void setRotation(int rotation);
  int getRotation();
  void setVoltage(double voltage);
  double getVoltage();
  void setCurrent(double current);
  double getCurrent();
  void setResistance(double resistance);
  double getResistance();
  void setAsset(Image image);
  void setComponentShape(ComponentShape shape);
  ComponentShape getComponentShape();
  ComponentState processComponentState(ComponentShape shape);

  /**
   * Just a more clear way of telling the user what is going on. Use this at the end of the circuit simulation
   */
  default void resetComponent() {
    setPath(null);
    updateComponentState(false);
  }

  default void updateComponentState(boolean updateNeighbors) {
    Block block = getBlock();
    Location blockLoc = block.getLocation();
    World world = blockLoc.getWorld();
    int blockX = blockLoc.getX();
    int blockY = blockLoc.getY();
    int leftVal = 0;
    int rightVal = 0;
    int upVal = 0;
    int downVal = 0;

    Block left = world.getBlockByWorldPosition(blockX - 50, blockY);
    Block right = world.getBlockByWorldPosition(blockX + 50, blockY);
    Block up = world.getBlockByWorldPosition(blockX, blockY - 50);
    Block down = world.getBlockByWorldPosition(blockX, blockY + 50);

    if (left != null)
      if (left.getGameObject() != null && left.getGameObject() instanceof ElectricalComponent) {
        leftVal = 1;
        if (updateNeighbors) ((ElectricalComponent) left.getGameObject()).updateComponentState(false);
      }

    if (right != null)
      if (right.getGameObject() != null && right.getGameObject() instanceof ElectricalComponent) {
        rightVal = 1;
        if (updateNeighbors) ((ElectricalComponent) right.getGameObject()).updateComponentState(false);
      }

    if (up != null)
      if (up.getGameObject() != null && up.getGameObject() instanceof ElectricalComponent) {
        upVal = 1;
        if (updateNeighbors) ((ElectricalComponent) up.getGameObject()).updateComponentState(false);
      }

    if (down != null)
      if (down.getGameObject() != null && down.getGameObject() instanceof ElectricalComponent) {
        downVal = 1;
        if (updateNeighbors) ((ElectricalComponent) down.getGameObject()).updateComponentState(false);
      }

    ComponentState state = processComponentState(new ComponentShape(new int[] {upVal, rightVal, downVal, leftVal}));
    setRotation(state.getRotation());
    setAsset(ImageUtil.rotate(state.getAsset(), state.getRotation()));
  }

  default void processComponent(int xPrior, int yPrior, Block priorBlock, CircuitPath path) {
    Block block = getBlock();
    if (block == null) return;
    block.clearFilters();
    Location location = block.getLocation();
    int blockX = location.getX();
    int blockY = location.getY();
    World world = location.getWorld();
    int[] shape = getComponentShape().blockMap();
    int up = shape[0];
    int right = shape[1];
    int down = shape[2];
    int left = shape[3];

    if (getPath() != null) return;
    SimulationManager simulationManager = Electroid.getInstance().getSimulationManager();
    if (!isNode()) {
      int nextBlockX = blockX + (-(-right + left + xPrior) * 50);
      int nextBlockY = blockY - ((up - down + yPrior) * 50);
      Block nextBlock = world.getBlockByWorldPosition(nextBlockX, nextBlockY);
      //The shape of the block tells us we KNOW the block is not null and has an electrical component
      path.addComponent(this);
      if (nextBlock.getGameObject() == null) {
        simulationManager.logSimulationError("Incomplete circuit!");
        return;
      }
      ((ElectricalComponent) nextBlock.getGameObject()).processComponent(-right + left + xPrior, up - down + yPrior, block, path);
      return;
    }
    //set end node in path
    //new paths from node in new directions
    path.setEndNode(this);
    simulationManager.addCircuitPath(path);
    if (this instanceof VoltageSource) return;

    Block upBlock = world.getBlockByWorldPosition(blockX, blockY - 50);
    if (upBlock != priorBlock)
      if (upBlock.getGameObject() != null)
        ((ElectricalComponent) upBlock.getGameObject()).processComponent(0, 1, block, new CircuitPath(this));

    Block rightBlock = world.getBlockByWorldPosition(blockX + 50, blockY);
    if (rightBlock != priorBlock)
      if (rightBlock.getGameObject() != null)
        ((ElectricalComponent) rightBlock.getGameObject()).processComponent(-1, 0, block, new CircuitPath(this));

    Block downBlock = world.getBlockByWorldPosition(blockX, blockY + 50);
    if (downBlock != priorBlock)
      if (downBlock.getGameObject() != null)
        ((ElectricalComponent) downBlock.getGameObject()).processComponent(0, -1, block, new CircuitPath(this));

    Block leftBlock = world.getBlockByWorldPosition(blockX - 50, blockY);
    if (leftBlock != priorBlock)
      if (leftBlock.getGameObject() != null)
        ((ElectricalComponent) leftBlock.getGameObject()).processComponent(1, 0, block, new CircuitPath(this));

  }


}
