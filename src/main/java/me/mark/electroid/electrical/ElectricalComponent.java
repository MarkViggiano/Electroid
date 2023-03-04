package me.mark.electroid.electrical;

import com.megaboost.position.Direction;
import com.megaboost.position.Location;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public interface ElectricalComponent {

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
  default double getPower() {
    return getCurrent() * getVoltage();
  }

  default double calculateVoltage() {
    double voltage = getCurrent() * getResistance();
    setVoltage(voltage);
    return voltage;
  }

  ComponentState processComponentState(ComponentShape shape);

  default void updateComponentState() {
    Block block = getBlock();
    Location blockLoc = block.getLocation();
    World world = blockLoc.getWorld();
    int blockX = blockLoc.getX();
    int blockY = blockLoc.getY();
    int leftVal = 0;
    int rightVal = 0;
    int upVal = 0;
    int downVal = 0;

    Block left = world.getBlockByWorldPosition(blockX - 1, blockY);
    Block right = world.getBlockByWorldPosition(blockX + 1, blockY);
    Block up = world.getBlockByWorldPosition(blockX, blockY - 1);
    Block down = world.getBlockByWorldPosition(blockX, blockY + 1);

    if (left != null) if (left.getGameObject() != null) leftVal = 1;
    if (right != null) if (right.getGameObject() != null) rightVal = 1;
    if (up != null) if (up.getGameObject() != null) upVal = 1;
    if (down != null) if (down.getGameObject() != null) downVal = 1;

    ComponentState state = processComponentState(new ComponentShape(new int[] {upVal, rightVal, downVal, leftVal}));
    setRotation(state.getRotation());
    setAsset(ImageUtil.rotate(state.getAsset(), state.getRotation()));
  }

  default boolean processComponent(Direction from, ElectricalComponent priorComponent) {
    Block block = getBlock();
    if (block == null) return false;
    int blockX = block.getBlockX();
    int blockY = block.getBlockY();
    World world = block.getChunk().getWorld();
    List<Block> availableBlocks = new ArrayList<>();

    Block left = world.getBlockByWorldPosition(blockX - 1, blockY);
    Block right = world.getBlockByWorldPosition(blockX + 1, blockY);
    Block up = world.getBlockByWorldPosition(blockX, blockY - 1);
    Block down = world.getBlockByWorldPosition(blockX, blockY + 1);

    if (left != null && left.getGameObject() != null) availableBlocks.add(left);
    if (right != null && right.getGameObject() != null) availableBlocks.add(right);
    if (up != null && up.getGameObject() != null) availableBlocks.add(up);
    if (down != null && down.getGameObject() != null) availableBlocks.add(down);

    if (availableBlocks.size() == 0) return false;
    if (availableBlocks.size() == 1) {
      if (availableBlocks.get(0).getGameObject() == null) return false;

      //every placable object in this game is an electrical component
      ElectricalComponent component = (ElectricalComponent) availableBlocks.get(0).getGameObject();
      if (component == priorComponent) return false;
      return component.processComponent(from, this);
    }



    return true;
  }


}
