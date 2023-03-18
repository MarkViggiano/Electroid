package me.mark.electroid.commands;

import com.megaboost.chat.ChatSender;
import com.megaboost.command.CommandExecutor;
import com.megaboost.items.ItemStack;
import com.megaboost.items.MetaDataValue;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;

public class BatteryCommand implements CommandExecutor {
  @Override
  public boolean execute(ChatSender chatSender, String[] strings) {

    if (!(chatSender instanceof ElectroidPlayer electroidPlayer)) return false;
    if (strings.length != 1) {
      chatSender.sendMessage("Invalid arguments! Example: /battery 100");
      return false;
    }

    int voltage = Integer.parseInt(strings[0]);
    ItemStack battery = new ItemStack(Material.VOLTAGE_SOURCE, 1);
    battery.setProperty("voltage", new MetaDataValue<>(voltage));
    electroidPlayer.getInventory().addItem(battery);
    chatSender.sendMessage("Given battery with a charge of: " + voltage);
    return true;
  }
}
