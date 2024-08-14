package net.tv.twitch.chrono_fish.warewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
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
                            wolfGame.setRoles();
                            wolfGame.assignRole();
                            wolfGame.setRunning(true);
                            wolfGame.setTimeZone(TimeZone.NIGHT);
                        } else {
                            snd.sendMessage("a game is running, you can't start another one");
                        }
                        break;

                    case "time":
                        sender.sendMessage("昼 : "+TimeZone.DAY.getTime()+"秒\n" +
                                "夜 : "+TimeZone.NIGHT.getTime()+"秒\n"+
                                "投票時間 : "+TimeZone.VOTE.getTime()+"秒");
                        break;

                    case "end":
                        wolfGame.setRunning(false);
                        wolfGame.setTimeZone(TimeZone.DAY);
                        break;

                    case "vote":
                        wolfGame.setTimeZone(TimeZone.VOTE);
                        break;

                    case "action":
                        WolfInv wolfInv = new WolfInv(wolfGame);
                        snd.openInventory(wolfInv.voteInv());
                        break;

                    default:
                        sender.sendMessage(Component.text("you send an unknown command").color(TextColor.color(255,100,100)));
                }
            }
        }
        return false;
    }
}
