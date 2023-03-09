package me.mark.electroid.network;

import com.megaboost.networking.NetworkEvent;
import com.megaboost.networking.NetworkManager;
import me.mark.electroid.Electroid;

public class ElectroidServer {

  private final NetworkManager networkManager;

  public ElectroidServer() {
    this.networkManager = new NetworkManager(Electroid.getInstance().getGame(), "http://108.50.203.110:8080/");
    this.networkManager.getSocket().io().reconnectionAttempts(0);
  }

  public void callEvent(String name, NetworkEvent event) {
    this.networkManager.getSocket().emit(name, event);
  }

  public void disconnect() {
    this.networkManager.getSocket().disconnect();
  }

  public boolean isConnected() {
    return this.networkManager.getSocket().isActive();
  }

}
