package me.mark.electroid.loading.start;

import com.megaboost.Game;
import com.megaboost.items.ItemStack;
import com.megaboost.loading.Task;
import com.megaboost.position.Location;
import com.megaboost.world.World;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;

public class CreatePlayer implements Task {
  @Override
  public void execute(Game game) {
    World world = game.getGameWorld();

    Location loc = new Location(20, 20, world);
    ElectroidPlayer player = new ElectroidPlayer(loc, "Player");
    player.spawn();
    game.setPlayer(player);
    player.getInventory().addItem(new ItemStack(Material.STRAIGHT_WIRE, 10));
    player.getInventory().addItem(new ItemStack(Material.CURVED_WIRE, 10));
    player.getInventory().addItem(new ItemStack(Material.THREE_PRONGED_WIRE, 10));
    player.getInventory().addItem(new ItemStack(Material.FOUR_PRONGED_WIRE, 10));
    player.getInventory().addItem(new ItemStack(Material.VOLTAGE_SOURCE, 10));
    player.getInventory().addItem(new ItemStack(Material.RESISTOR, 10));
  }

  @Override
  public String getName() {
    return "Creating Player";
  }
}
