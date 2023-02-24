package me.mark.electroid.world.blocks;

import com.megaboost.items.ItemStack;
import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.block.Breakable;
import com.megaboost.world.block.Placeable;
import me.mark.electroid.Electroid;
import me.mark.electroid.items.Material;
import me.mark.electroid.world.CircuitBlock;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class WireBlock extends WorldObject implements Breakable, Placeable {

  public WireBlock(Block block) {
    super(Material.STRAIGHT_WIRE.getAsset(), block);

    Electroid.getInstance().getGame().getWorldObjectPlaceManager().addObject(this);
    setWidth(50);
    setHeight(50);
  }

  public WireBlock() {
    super();
  }

  @Override
  public void tick() {

  }

  @Override
  public ItemStack getDrop() {
    return new ItemStack(Material.STRAIGHT_WIRE);
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
}
