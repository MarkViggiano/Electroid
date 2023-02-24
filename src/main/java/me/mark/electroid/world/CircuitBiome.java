package me.mark.electroid.world;

import com.megaboost.world.Block;
import com.megaboost.world.WorldObject;
import com.megaboost.world.biome.Biome;
import me.mark.electroid.world.blocks.WireBlock;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class CircuitBiome extends Biome {

  public CircuitBiome() {
    super("circuit", new Color(158, 107, 74));
  }

  @Override
  public List<Class<? extends WorldObject>> getBiomeObjects() {
    return Collections.singletonList(WireBlock.class);
  }

  @Override
  public List<Class<? extends Block>> getPossibleBlocks() {
    return Collections.singletonList(CircuitBlock.class);
  }

  @Override
  public double getHeight() {
    return 2;
  }
}
