package me.mark.electroid.loading.start;

import com.megaboost.Game;
import com.megaboost.loading.Task;
import com.megaboost.position.Location;
import com.megaboost.world.World;
import me.mark.electroid.entity.ElectroidPlayer;

public class CreatePlayer implements Task {
  @Override
  public void execute(Game game) {
    World world = game.getGameWorld();

    Location loc = new Location(20, 20, world);
    ElectroidPlayer player = new ElectroidPlayer(loc, "Player");
    player.spawn();
    game.setPlayer(player);
  }

  @Override
  public String getName() {
    return "Creating Player";
  }
}
