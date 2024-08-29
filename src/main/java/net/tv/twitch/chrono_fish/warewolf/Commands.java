package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.GameTask;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;

    public Commands(WareWolf wareWolf, WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.wareWolf = wareWolf;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player snd = (Player) sender;
            if(command.getName().equalsIgnoreCase("ww")){
                switch (args[0]){
                    case "start":
                        if(!wolfGame.getRunning()){
                            wolfGame.setRunning(true);
                            wolfGame.setTimeZone(TimeZone.NIGHT);
                            wolfGame.getRoleManager().assignRole();
                            new GameTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                        } else {
                            snd.sendMessage("§c別のゲームが進行中です");
                        }
                        break;

                    case "action":
                        WolfInv wolfInv = new WolfInv(wolfGame);
                        snd.openInventory(wolfInv.voteInv());
                        break;

                    default:
                        sender.sendMessage("§c不明なコマンド");
                }
            }
        }
        return false;
    }
}
