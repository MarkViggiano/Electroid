package me.mark.electroid.world;

import com.megaboost.Game;
import com.megaboost.entity.Entity;
import com.megaboost.visuals.Camera;
import com.megaboost.visuals.Filter;
import com.megaboost.world.Block;
import com.megaboost.world.Chunk;
import com.megaboost.world.WorldObject;
import com.megaboost.world.biome.Biome;
import java.awt.Graphics;
import java.awt.Image;

public class CircuitBlock extends Block {

  public CircuitBlock(int pixelX, int pixelY, Chunk chunk, int blockX, int blockY, Biome biome) {
    super(pixelX, pixelY, chunk, blockX, blockY, biome);
  }

  @Override
  public Image getAsset() {
    return null;
  }

  @Override
  public void onWalkOn(Entity entity) {

  }

  @Override
  public void setGameObject(WorldObject gameObject) {
    if (gameObject == null) {
      if (getGameObject() != null) {
        Game.getInstance().getComponentManager().removeComponent(getGameObject());
        getGameObject().destroy();
      }
      overrideGameObject(null);
      return;
    }

    gameObject.setLocation(getLocation());
    overrideGameObject(gameObject);
  }

  @Override
  public void render(Graphics g, Camera camera) {
    int width = Block.BLOCK_WIDTH;
    int height = Block.BLOCK_HEIGHT;
    if (!camera.objectInCamera(getLocation(), width, height)) return;
    int[] cords = camera.getObjectScreenLocation(getLocation());
    g.setColor(getBiome().getBackgroundColor());
    g.fillRect(cords[0], cords[1], width, height);

    for (Filter filter : getFilters()) {
      g.setColor(filter.getColor());
      g.fillRect(cords[0] - 2, cords[1] - 2, width - 4, height - 4);
    }
  }

}
