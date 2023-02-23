package me.mark.electroid.loading.start;

import com.megaboost.Game;
import com.megaboost.loading.Task;
import com.megaboost.world.biome.BiomeManager;
import me.mark.electroid.world.CircuitBiome;

import java.util.Collections;

public class CreateWorld implements Task {
  @Override
  public void execute(Game game) {
    game.createNewGameWorld("CurrentWorld", 4, 4, new BiomeManager(Collections.singletonList(new CircuitBiome())));
  }

  @Override
  public String getName() {
    return "Creating World";
  }
}
