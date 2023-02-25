package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.Wire;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.world.CircuitBlock;
import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class WireBlock extends WorldObject implements Breakable, Placeable, Wire {

  private final ItemStack drop;

  public WireBlock(Block block) {
    super(ImageUtil.rotate(Material.STRAIGHT_WIRE.getAsset(), ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    this.drop = new ItemStack(Material.STRAIGHT_WIRE);
    //Electroid.getInstance().getGame().getWorldObjectPlaceManager().addObject(this);
    setWidth(50);
    setHeight(50);
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
  public int getRotation() {
    return 0;
  }

  @Override
  public void setVoltage(double voltage) {

  }

  @Override
  public double getVoltage() {
    return 0;
  }

  @Override
  public void setCurrent(double current) {

  }

  @Override
  public double getCurrent() {
    return 0;
  }

  @Override
  public void setResistance(double resistance) {

  }

  @Override
  public boolean isNode() {
    return false;
  }

  @Override
  public void setNode(boolean isNode) {

  }
}
