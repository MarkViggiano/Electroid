package me.mark.electroid.gui.loadmenu;

import com.megaboost.gui.menu.FullScreenMenu;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.TitleText;
import me.mark.electroid.world.ElectroidWorld;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadMenu extends FullScreenMenu {

  private final Color backgroundColor;
  private final List<WorldListing> worldListings;

  public LoadMenu() {
    super(Arrays.asList(
        new TitleText("Load Circuit"),
        new LoadToMainMenuButton()));

    this.backgroundColor = new Color(57, 50, 49);
    this.worldListings = new ArrayList<>();
  }

  public void createWorldList() {
    List<ElectroidWorld> worlds = Electroid.getInstance().getWorlds();
    this.worldListings.clear();
    int i = 0;
    for (ElectroidWorld world : worlds) {
      this.worldListings.add(new WorldListing(world, new Location(100, 100 + (110 * i))));
      i++;
    }
  }

  @Override
  public int getKey() {
    return 0;
  }

  @Override
  public boolean isKeyToggled() {
    return false;
  }

  @Override
  public Color getColor() {
    return backgroundColor;
  }

  @Override
  public void setShown(boolean shown) {
    super.setShown(shown);
    if (this.worldListings == null) return;
    for (WorldListing listing : this.worldListings) listing.setShown(shown);
  }

  @Override
  public void tick() {
    super.tick();
    if (this.worldListings == null) return;
    for (WorldListing listing : this.worldListings) listing.tick();
  }

  @Override
  public void render(Graphics g, Camera camera) {
    super.render(g, camera);
    if (this.worldListings == null) return;
    for (WorldListing listing : this.worldListings) listing.render(g, camera);
  }

}
