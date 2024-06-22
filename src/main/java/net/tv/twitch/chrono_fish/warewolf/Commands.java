package net.tv.twitch.chrono_fish.warewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameManager;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameState;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
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
                WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
                GameManager gameManager = wareWolfGame.getGameManager();
                switch (args[0]){
                    case "start":
                        wareWolfGame.setGameState(GameState.RUNNING);
                        wareWolfGame.setTimeZone(TimeZone.NIGHT);
                        wareWolfGame.getBossBarManager().reloadBar();
                        gameManager.setAlivePlayers();
                        gameManager.assignRole();
                        break;

                    case "time":
                        sender.sendMessage("昼 : "+TimeZone.DAY.getTime()+"秒\n" +
                                "夜 : "+TimeZone.NIGHT.getTime()+"秒\n"+
                                "投票時間 : "+TimeZone.VOTE.getTime()+"秒");
                        break;

                    default:
                        sender.sendMessage(Component.text("you send unknown command").color(TextColor.color(200,0,0)));
                }
            }
        }
        return false;
    }
}
