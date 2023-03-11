package me.mark.electroid.world;

import com.megaboost.Game;
import com.megaboost.utils.FileUtil;
import com.megaboost.world.ChunkPosition;
import com.megaboost.world.World;
import com.megaboost.world.biome.BiomeManager;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  public static List<ElectroidWorld> getSavedElectroidWorlds() {
    List<ElectroidWorld> worlds = new ArrayList<>();

    URL url = Game.class.getProtectionDomain().getCodeSource().getLocation();
    String jarPath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
    jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
    File worldDirectory = new File(jarPath + "\\worlds");
    if (!worldDirectory.exists()) worldDirectory.mkdirs();
    if (!worldDirectory.isDirectory()) return worlds;
    if (worldDirectory.listFiles() == null) return worlds;

    for (File worldFolder : worldDirectory.listFiles()) {
      if (!worldFolder.isDirectory()) continue;
      if (worldFolder.listFiles() == null) continue;
      for (File file : worldFolder.listFiles()) {
        if (!file.getName().contains(".mbwf")) continue;

        String worldName = worldFolder.getName();
        List<String> output = FileUtil.getFileOutput(file);

        ElectroidWorld world = new ElectroidWorld();
        world.setName(worldName);
        worlds.add(world);
      }
    }

    return worlds;

  }

}
