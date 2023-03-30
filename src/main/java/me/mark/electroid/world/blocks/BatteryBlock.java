package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.electrical.ComponentShape;
import me.mark.electroid.electrical.VoltageSource;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.electrical.circuit.CircuitPath;
import me.mark.electroid.world.CircuitBlock;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BatteryBlock extends WorldObject implements Breakable, Placeable, VoltageSource {

  private final ItemStack drop;
  private double voltage;
  private int rotation;
  private ComponentShape shape;
  private CircuitPath path;

  public BatteryBlock(Block block) {
    super(ImageUtil.rotate(Material.VOLTAGE_SOURCE.getAsset(), ((ElectroidPlayer) Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    setWidth(50);
    setHeight(50);
    this.drop = new ItemStack(Material.VOLTAGE_SOURCE);
    this.path = null;
    Electroid electroid = Electroid.getInstance();
    ElectroidPlayer player = (ElectroidPlayer) electroid.getGame().getPlayer();
    this.rotation = player.getCursorRotation();
    String savedVoltage = player.getHotBar().getItemInHand().getProperty("voltage").getSaveData();
    if (Objects.equals(savedVoltage, "")) this.voltage = 100; //default to 100 volts
    else this.voltage = Integer.parseInt(savedVoltage);
    electroid.getLogger().info(String.format("[SIMULATION] Registered voltage source: %s", this.voltage));
    electroid.getSimulationManager().setVoltageSource(this);
    updateComponentState(true);
  }

  public BatteryBlock() {
    super();

    this.drop = new ItemStack(Material.VOLTAGE_SOURCE);
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
    return Material.VOLTAGE_SOURCE;
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
  public void setComponentShape(ComponentShape shape) {
    this.shape = shape;
  }

  @Override
  public ComponentShape getComponentShape() {
    return shape;
  }

  @Override
  public void destroy() {
    Electroid.getInstance().getSimulationManager().setVoltageSource(null);
    super.destroy();
  }


}
