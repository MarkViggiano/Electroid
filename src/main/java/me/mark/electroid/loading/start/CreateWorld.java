package me.mark.electroid.loading.start;

import com.megaboost.Game;
import com.megaboost.loading.Task;
import me.mark.electroid.Electroid;
import me.mark.electroid.world.ElectroidWorld;

public class CreateWorld implements Task {
  @Override
  public void execute(Game game) {

    long start = System.currentTimeMillis();
    ElectroidWorld world = new ElectroidWorld();
    game.addWorld(world);
    game.setGameWorld(world);
    long now = System.currentTimeMillis();
    game.getChat().sendChatMessage(String.format("%s Generating world took %s ms.", Electroid.SYSTEM_PREFIX, now - start));

  }

  @Override
  public String getName() {
    return "Creating World";
  }
}
