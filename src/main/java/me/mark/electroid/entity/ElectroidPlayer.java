package me.mark.electroid.entity;

import com.megaboost.Game;
import com.megaboost.chat.ChatSender;
import com.megaboost.entity.Player;
import com.megaboost.events.EventHandler;
import com.megaboost.events.Listener;
import com.megaboost.events.entity.EntityMoveEvent;
import com.megaboost.events.input.KeyPressEvent;
import com.megaboost.inputs.MouseClickType;
import com.megaboost.inventory.HotBar;
import com.megaboost.inventory.Inventory;
import com.megaboost.inventory.InventoryHolder;
import com.megaboost.items.Material;
import com.megaboost.position.Location;
import com.megaboost.utils.ImageUtil;
import com.megaboost.visuals.Camera;
import me.mark.electroid.Electroid;
import me.mark.electroid.gui.debugmenu.DebugMenu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class ElectroidPlayer extends Player implements ChatSender, Listener, InventoryHolder {

  private final Inventory inventory;
  private final HotBar hotBar;
  private final DebugMenu debugMenu;
  private Image cursorImage;
  private int cursorRotation;

  public ElectroidPlayer(Location location, String name) {
    super(location, name);

    this.inventory = new Inventory("Player Inventory", 9, 5);
    this.hotBar = new HotBar(this.inventory, 60, 60, 5);
    this.debugMenu = new DebugMenu(this);
    this.cursorImage = null;
    this.cursorRotation = 0;
    setSpeed(8);
    getNameTag().setShown(false);
    Electroid.getInstance().getGame().getEventManager().registerListener(this);
  }

  public Image getCursorImage() {
    return cursorImage;
  }

  public void setCursorImage(Image cursorImage) {
    this.cursorImage = cursorImage;
  }

  public int getCursorRotation() {
    return cursorRotation;
  }

  public void setCursorRotation(int cursorRotation) {
    this.cursorRotation = cursorRotation;
  }

  private void rotateCursorImage() {
    if (getCursorImage() == null) return;
    setCursorRotation(getCursorRotation() + 90);
    setCursorImage(ImageUtil.rotate(getCursorImage(), 90));
  }

  @EventHandler
  public void handleKeyTriggers(KeyPressEvent e) {
    if (Game.getInstance().getChat().isChatActive()) return;
    int keycode = e.getKey();

    if (keycode == KeyEvent.VK_E) getInventory().toggleShown();
    if (keycode == KeyEvent.VK_ESCAPE) if (getInventory().isShown()) getInventory().closeInventory();
    if (keycode == KeyEvent.VK_SPACE) if (!getInventory().isShown()) rotateCursorImage();
  }

  @EventHandler
  public void playerMoveEvent(EntityMoveEvent e) {
    if (!(e.getEntity() instanceof ElectroidPlayer)) return;
    if (e.getEntity() != this) return;
    if (getInventory() == null) return;
    if (getInventory().isShown()) e.setCancelled(true);
    if (Electroid.getInstance().getGame().getChat().isChatActive()) e.setCancelled(true);
  }

  @Override
  public Image getAsset() {
    return null;
  }

  @Override
  public int getBoxHeight() {
    return getHeight();
  }

  @Override
  public int getBoxWidth() {
    return getWidth();
  }

  @Override
  public Location getBoxLocation() {
    return getLocation();
  }

  @Override
  public void onClick(MouseClickType mouseClickType) {

  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  @Override
  public HotBar getHotBar() {
    return hotBar;
  }

  @Override
  public void tick() {
    super.tick();

    if (getHotBar() == null) return;
    if (getHotBar().getItemInHand() == null) return;
    if (getHotBar().getItemInHand().getType() == Material.AIR) {
      setCursorImage(null);
      return;
    }
    setCursorImage(ImageUtil.rotate(getHotBar().getItemInHand().getType().getAsset(), getCursorRotation()));
  }

  @Override
  public void render(Graphics g, Camera camera) {
    if (getCursorImage() == null) return;
    Point mousePoint = Electroid.getInstance().getGame().getMousePoint();
    g.drawImage(getCursorImage(), (int) mousePoint.getX(), (int) mousePoint.getY(), 50, 50, null);
  }

}
