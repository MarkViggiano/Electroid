package me.mark.electroid.world;

import com.megaboost.world.ChunkPosition;
import com.megaboost.world.World;
import com.megaboost.world.biome.BiomeManager;
import java.util.Collections;

public class ElectroidWorld extends World {

  public ElectroidWorld() {
    super("CurrentWorld", new BiomeManager(Collections.singletonList(new CircuitBiome())));

    generateNewWorld(4, 4);
  }

  /**
   * Use ElectroidChunk because there is only one biome type
   * ElectroidChunks have a much more optimized generation process
   *
   * @param xChunkCount | How many chunks along x
   * @param yChunkCount | How many chunks along y
   */
  @Override
  public void generateNewWorld(int xChunkCount, int yChunkCount) {
    setXChunkSize(xChunkCount);
    setYChunkSize(yChunkCount);

    ElectroidChunk[][] chunks = new ElectroidChunk[yChunkCount][xChunkCount];

    for (int y = 0; y < yChunkCount; y++) {
      for (int x = 0; x < xChunkCount; x++) {
        ElectroidChunk chunk = chunks[y][x];
        if (chunk != null) continue;
        ChunkPosition chunkPosition = new ChunkPosition(x, y, this);
        chunk = new ElectroidChunk(chunkPosition);
        chunk.generateContent();
        chunks[y][x] = chunk;
      }
    }

    setChunks(chunks);
  }

}
