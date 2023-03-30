package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ComponentShape;
import me.mark.electroid.electrical.Resistor;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.world.CircuitBlock;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResistorBlock extends WorldObject implements Placeable, Breakable, Resistor {

  private final ItemStack drop;
  private int rotation;
  private double resistance;
  private double current;
  private double voltage;
  private ComponentShape shape;
  private CircuitPath path;

  public ResistorBlock(Block block) {
    super(ImageUtil.rotate(Material.RESISTOR.getAsset(), ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    setWidth(50);
    setHeight(50);
    this.voltage = 0;
    this.current = 0;
    this.drop = new ItemStack(Material.RESISTOR);
    this.path = path;
    ElectroidPlayer player = (ElectroidPlayer) Electroid.getInstance().getGame().getPlayer();
    this.rotation = player.getCursorRotation();
    String savedResistance = player.getHotBar().getItemInHand().getProperty("resistance").getSaveData();
    if (Objects.equals(savedResistance, "")) this.resistance = 10; //default to 10 ohms
    else this.resistance = Double.parseDouble(savedResistance);
    Electroid.getInstance().getLogger().info(String.format("[SIMULATION] Registered resistor: %s", this.resistance));

    updateComponentState(true);

  }

  public ResistorBlock() {
    super();

    this.drop = new ItemStack(Material.RESISTOR);
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
    return Color.ORANGE;
  }

  @Override
  public List<Class<? extends Block>> getValidBlocks() {
    return Collections.singletonList(CircuitBlock.class);
  }

  @Override
  public Material getSourceMaterial() {
    return Material.RESISTOR;
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
    this.resistance = resistance;
  }

  @Override
  public double getResistance() {
    return resistance;
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
