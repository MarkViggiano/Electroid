package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ComponentShape;
import me.mark.electroid.electrical.Wire;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.electrical.circuit.CircuitPath;
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
  private ComponentShape shape;
  private CircuitPath path;

  public WireBlock(Block block) {
    super(ImageUtil.rotate(Material.STRAIGHT_WIRE.getAsset(), ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    this.rotation = ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation();
    this.drop = new ItemStack(Material.STRAIGHT_WIRE);
    this.path = null;

    setWidth(50);
    setHeight(50);
    updateComponentState(true);
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
  public CircuitPath getPath() {
    return path;
  }

  @Override
  public void setPath(CircuitPath path) {
    this.path = path;
  }

  @Override
  public boolean isNode() {
    return isNode;
  }

  @Override
  public void setNode(boolean isNode) {
    this.isNode = isNode;
  }

  @Override
  public void setComponentShape(ComponentShape shape) {
    this.shape = shape;
  }

  @Override
  public ComponentShape getComponentShape() {
    return shape;
  }

}
