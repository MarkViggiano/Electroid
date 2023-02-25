package me.mark.electroid.gui.debugmenu;

import com.megaboost.Game;
import com.megaboost.components.WrappedTextComponent;
import com.megaboost.gui.menu.Menu;
import com.megaboost.position.Location;
import com.megaboost.visuals.Camera;
import com.megaboost.world.Block;
import me.mark.electroid.Electroid;
import me.mark.electroid.entity.ElectroidPlayer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DebugMenu extends Menu {

  private final ElectroidPlayer player;
  private final Color color;
  private WrappedTextComponent text;

  public DebugMenu(ElectroidPlayer player) {
    super(new Location(0, 0), 100, 200, Collections.emptyList(), false);

    this.player = player;
    this.color = new Color(70, 70, 70, 50);
    setShown(true);
    createText();
  }

  private void createText() {
    ElectroidPlayer player = getPlayer();
    Block block = player.getCurrentBlock();
    Location blockLoc;
    String blockBiome;
    String blockName;
    if (block != null) {
      blockLoc = player.getLocation();
      blockName = block.getClass().getSimpleName();
      blockBiome = block.getBiome().getName();
    } else {
      blockLoc = new Location(0, 0);
      blockName = "Void";
      blockBiome = "None";
    }
    Game game = Electroid.getInstance().getGame();
    String fps = "FPS: " + game.getFps();
    String tps = "TPS: " + game.getTps();
    String componentSize = "Components: " + game.getComponentManager().getComponents().size();
    String coordinateFormat = "X // Y";
    String coordinateData = String.format("%s // %s", blockLoc.getX()/ Block.BLOCK_WIDTH, blockLoc.getY() / Block.BLOCK_HEIGHT);
    String blockTypeData = String.format("Block Type: %s", blockName);
    String biomeData = String.format("Biome: %s", blockBiome);
    String facing = String.format("Facing: %s", player.getFacing().getName());
    List<String> lines =  Arrays.asList(coordinateFormat, coordinateData, fps, tps, componentSize, blockTypeData, biomeData, facing);
    this.text = new WrappedTextComponent(new Location(5, 5), lines, new Font("Times New Roman", Font.PLAIN, 15), 5, false);
  }

  public ElectroidPlayer getPlayer() {
    return player;
  }

  @Override
  public Color getColor() {
    return color;
  }

  public WrappedTextComponent getText() {
    return text;
  }

  @Override
  public boolean isKeyToggled() {
    return false;
  }

  @Override
  public int getKey() {
    return 0;
  }

  @Override
  public void tick() {
    ElectroidPlayer player = getPlayer();
    WrappedTextComponent text = getText();
    if (player == null || text == null) return;
    Block block = player.getCurrentBlock();
    String blockBiome;
    String blockName;
    if (block != null) {
      blockName = block.getClass().getSimpleName();
      blockBiome = block.getBiome().getName();
    } else {
      blockName = "Void";
      blockBiome = "None";
    }

    Game game = Game.getInstance();
    String fps = "FPS: " + game.getFps();
    String tps = "TPS: " + game.getTps();
    String componentSize = "Components: " + game.getComponentManager().getComponents().size();
    String coordinateData = String.format("%s // %s", player.getBlockX(), player.getBlockY());
    String blockTypeData = String.format("Block Type: %s", blockName);
    String biomeData = String.format("Biome: %s", blockBiome);
    String facing = String.format("Facing: %s", player.getFacing().getName());
    text.updateLine(1, coordinateData);
    text.updateLine(2, blockTypeData);
    text.updateLine(3, fps);
    text.updateLine(4, tps);
    text.updateLine(5, componentSize);
    text.updateLine(6, biomeData);
    text.updateLine(7, facing);
  }

  @Override
  public void render(Graphics g, Camera camera) {
    WrappedTextComponent text = getText();
    if (text == null) return;
    text.render(g, camera);
  }
}
