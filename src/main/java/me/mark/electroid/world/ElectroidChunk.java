package me.mark.electroid.world;

import com.megaboost.world.Block;
import com.megaboost.world.Chunk;
import com.megaboost.world.ChunkPosition;
import com.megaboost.world.biome.Biome;
import java.lang.reflect.InvocationTargetException;

public class ElectroidChunk extends Chunk {

  public ElectroidChunk(ChunkPosition chunkPosition) {
    super(chunkPosition);
  }

  @Override
  public void generateContent() {
    int offsetX = getPixelX();
    int offsetY = getPixelY();
    int blockWidth = Block.BLOCK_WIDTH;
    int blockHeight = Block.BLOCK_HEIGHT;
    int posX, posY;

    Block[][] blockMatrix = getBlocks(); //16 by 16 blocks in a chunk
    Biome biome = getChunkPosition().getWorld().getBiomeManager().getBiomeFromName("circuit");
    int pixelX,pixelY; //optimized so we don't declare a new variable in memory on each loop.
    for (posY = 0; posY < 16; posY++) {
      for (posX = 0; posX < 16; posX++) {
        try {
          if (blockMatrix[posY][posX] != null) continue;
          //define vars
          pixelX = offsetX + (blockWidth * posX);
          pixelY = offsetY + (blockHeight * posY);

          Class<? extends Block> blockType = biome.getPossibleBlocks().get(0);
          Block block = blockType.getConstructor(Integer.TYPE, Integer.TYPE, Chunk.class, Integer.TYPE, Integer.TYPE, Biome.class)
              .newInstance(pixelX, pixelY, this, posX, posY, biome);

          block.setGameObject(null);
          setBlockAt(posX, posY, block);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
