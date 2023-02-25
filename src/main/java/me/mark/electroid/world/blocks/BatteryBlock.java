package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.utils.ImageUtil;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;
import me.mark.electroid.world.CircuitBlock;
import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class BatteryBlock extends WorldObject implements Breakable, Placeable {

  private final ItemStack drop;

  public BatteryBlock(Block block) {
    super(ImageUtil.rotate(Material.VOLTAGE_SOURCE.getAsset(), ((ElectroidPlayer)Electroid.getInstance().getGame().getPlayer()).getCursorRotation()), block);

    this.drop = new ItemStack(Material.VOLTAGE_SOURCE);
    //Electroid.getInstance().getGame().getWorldObjectPlaceManager().addObject(this);
    setWidth(50);
    setHeight(50);
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
}
