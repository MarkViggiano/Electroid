package me.mark.electroid.commands;

import com.megaboost.chat.ChatSender;
import com.megaboost.command.CommandExecutor;
import com.megaboost.items.ItemStack;
import com.megaboost.items.MetaDataValue;
import me.mark.electroid.Electroid;
import me.mark.electroid.entity.ElectroidPlayer;
import me.mark.electroid.items.Material;

public class ResistorCommand implements CommandExecutor {
  @Override
  public boolean execute(ChatSender chatSender, String[] strings) {
    if (!(chatSender instanceof ElectroidPlayer electroidPlayer)) return false;
    if (strings.length != 1) {
      chatSender.sendMessage("Invalid arguments! Example: /resistor 100");
      return false;
    }

    int resistance = 10;

    try {
      resistance = Integer.parseInt(strings[0]);
    } catch (NumberFormatException e) {
      chatSender.sendMessage(Electroid.ERROR_PREFIX + "Invalid resistance number, defaulting to 10.");
    }

    ItemStack resistor = new ItemStack(Material.RESISTOR, 1);
    resistor.setProperty("resistance", new MetaDataValue<>(resistance));
    electroidPlayer.getInventory().addItem(resistor);
    chatSender.sendMessage("Given resistor with a resistance of: " + resistance);
    return true;
  }
}
