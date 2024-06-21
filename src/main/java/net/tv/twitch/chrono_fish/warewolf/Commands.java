package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player snd = (Player) sender;
            if(command.getName().equalsIgnoreCase("ww")){
                new GameManager(snd, args);
            }
        }
        return false;
    }
}
