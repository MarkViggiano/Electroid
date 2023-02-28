package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.World;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ElectricalComponent;
import me.mark.electroid.electrical.Wire;
import me.mark.electroid.electrical.wire.WireState;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.world.CircuitBlock;
import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class WireBlock extends WorldObject implements Breakable, Placeable, Wire {

  private final ItemStack drop;
  private double voltage;
  private double current;
  private boolean isNode;
  private int rotation;

  public WireBlock(Block block) {
    super(ImageUtil.rotate(Material.STRAIGHT_WIRE.getAsset(), ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    this.rotation = ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation();
    this.drop = new ItemStack(Material.STRAIGHT_WIRE);
    //Electroid.getInstance().getGame().getWorldObjectPlaceManager().addObject(this);
    setWidth(50);
    setHeight(50);
    updateComponentState();
  }

  public WireBlock() {
    super();

    this.drop = new ItemStack(Material.STRAIGHT_WIRE);
  }

  @Override
  public void tick() {

  }

  @Override
  public ItemStack getDrop() {
    return drop;
  }

  @Override
  public Color getBreakParticleColor() {
    return Color.BLACK;
  }

  @Override
  public List<Class<? extends Block>> getValidBlocks() {
    return Collections.singletonList(CircuitBlock.class);
  }

  @Override
  public Material getSourceMaterial() {
    return Material.STRAIGHT_WIRE;
  }

  @Override
  public void updateComponentState() {
    Block block = getBlock();
    World world = block.getLocation().getWorld();
    int blockX = block.getBlockX();
    int blockY = block.getBlockY();
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

    WireState state = processWireState(new int[] {upVal, rightVal, downVal, leftVal});
    setRotation(state.getRotation());
    setAsset(ImageUtil.rotate(state.getAsset(), state.getRotation()));

  }

  @Override
  public void setRotation(int rotation) {
    this.rotation = rotation;
  }

  @Override
  public int getRotation() {
    return rotation;
  }

  @Override
  public void setVoltage(double voltage) {
    this.voltage = voltage;
  }

  @Override
  public double getVoltage() {
    return voltage;
  }

  @Override
  public void setCurrent(double current) {
    this.current = current;
  }

  @Override
  public double getCurrent() {
    return current;
  }

  @Override
  public void setResistance(double resistance) {
    //resistance of wire can't change from 0.1 ohms
  }

  @Override
  public boolean isNode() {
    return isNode;
  }

  @Override
  public void setNode(boolean isNode) {
    this.isNode = isNode;
  }
}
