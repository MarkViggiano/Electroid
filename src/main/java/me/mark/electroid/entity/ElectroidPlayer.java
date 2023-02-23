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
import com.megaboost.inventory.types.PlayerInventory;
import com.megaboost.position.Location;
import me.mark.electroid.Electroid;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class ElectroidPlayer extends Player implements ChatSender, Listener, InventoryHolder {

  private final Inventory inventory;
  private final HotBar hotBar;

  public ElectroidPlayer(Location location, String name) {
    super(location, name);

    this.inventory = new PlayerInventory(this);
    this.hotBar = new HotBar(this.inventory, 60, 60, 5);
    setSpeed(8);
    getNameTag().setShown(false);
    Electroid.getInstance().getGame().getEventManager().registerListener(this);
  }

  @EventHandler
  public void handleKeyTriggers(KeyPressEvent e) {
    if (Game.getInstance().getChat().isChatActive()) return;
    int keycode = e.getKey();

    if (keycode == KeyEvent.VK_E) getInventory().toggleShown();
    if (keycode == KeyEvent.VK_ESCAPE) if (getInventory().isShown()) getInventory().closeInventory();
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
}
