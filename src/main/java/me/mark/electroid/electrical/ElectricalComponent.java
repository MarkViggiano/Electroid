package me.mark.electroid.electrical;

import com.megaboost.position.Location;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import me.mark.electroid.simulation.CircuitPath;

import java.awt.Image;

public interface ElectricalComponent {

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

  default void processComponent(int xPrior, int yPrior, ElectricalComponent priorComponent, CircuitPath path) {
    Block block = getBlock();
    if (block == null) return;
    Location location = block.getLocation();
    int blockX = location.getX();
    int blockY = location.getY();
    World world = location.getWorld();
    int[] shape = getComponentShape().blockMap();
    int up = shape[0];
    int right = shape[1];
    int down = shape[2];
    int left = shape[3];

    if (!isNode()) {
      int nextBlockX = blockX + ((right - left - xPrior) * 50);
      int nextBlockY = blockY - ((up + down - yPrior) * 50);
      Block nextBlock = world.getBlockByWorldPosition(nextBlockX, nextBlockY);

      //The shape of the block tells us we KNOW the block is not null and has an electrical component
      path.addComponent(this);
      ((ElectricalComponent) nextBlock.getGameObject()).processComponent(right - left - xPrior, up - down - yPrior, this, path);
      return;
    }
    //set end node in path
    //new paths from node in new directions
    path.setEndNode(this);

    if (up + down + left + right == 4) {
      //4 way interchange
      if (xPrior == 1) right = 0;
      if (xPrior == -1) left = 0;
      if (yPrior == -1) up = 0;
      if (yPrior == 1) down = 0;

      Block rightBlock = world.getBlockByWorldPosition(blockX + (50 * right), blockY);
      Block leftBlock = world.getBlockByWorldPosition(blockX - (50 * left), blockY);
      Block upBlock = world.getBlockByWorldPosition(blockX, blockY - (50 * up));
      Block downBlock = world.getBlockByWorldPosition(blockX, blockY + (50 * down));



      return;
    }

    //3 way interchange

  }


}
