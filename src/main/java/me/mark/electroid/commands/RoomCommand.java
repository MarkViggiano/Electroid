package me.mark.electroid.commands;

import com.megaboost.chat.ChatSender;
import com.megaboost.command.CommandExecutor;
import me.mark.electroid.Electroid;

public class RoomCommand implements CommandExecutor {
  @Override
  public boolean execute(ChatSender chatSender, String[] strings) {

    Electroid.getInstance().getSimulationManager().simulateCircuit();
    return true;
  }
}
