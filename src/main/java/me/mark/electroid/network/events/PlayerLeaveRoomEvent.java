package me.mark.electroid.network.events;

import com.megaboost.networking.NetworkEvent;
import org.json.JSONObject;

public class PlayerLeaveRoomEvent implements NetworkEvent {
  @Override
  public JSONObject getPacketData() {
    return null;
  }
}
