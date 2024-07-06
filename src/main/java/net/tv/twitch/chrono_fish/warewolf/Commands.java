package net.tv.twitch.chrono_fish.warewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameManager;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameState;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WareWolfInv;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZoneTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player snd = (Player) sender;
            WareWolf wareWolf = WareWolf.getMain();
            WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
            GameManager gameManager = wareWolfGame.getGameManager();
            if(command.getName().equalsIgnoreCase("ww")){
                switch (args[0]){
                    case "start":
                        if(wareWolfGame.getGameState().equals(GameState.FINISHED)){
                            gameManager.setAlivePlayers();
                            gameManager.setRoles();
                            gameManager.assignRole();
                            wareWolfGame.setGameState(GameState.RUNNING);
                            wareWolfGame.setTimeZone(TimeZone.NIGHT);
                            wareWolfGame.getBossBarManager().reloadBar();
                            wareWolfGame.getWolfChat().setAlivePlayer(wareWolfGame.getAlivePlayers());
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
                        wareWolfGame.setGameState(GameState.FINISHED);
                        wareWolfGame.setTimeZone(TimeZone.DAY);
                        wareWolfGame.getAlivePlayers().clear();
                        wareWolfGame.getBossBarManager().reloadBar();
                        break;

                    case "vote":
                        wareWolfGame.setTimeZone(TimeZone.VOTE);
                        wareWolfGame.getBossBarManager().reloadBar();
                        break;

                    case "action":
                        WareWolfInv wareWolfInv = new WareWolfInv();
                        snd.openInventory(wareWolfInv.actionInv());
                        break;

                    case "tstart":
                        new TimeZoneTask(wareWolf,wareWolfGame).runTaskTimer(wareWolf,0,20);
                        break;

                    default:
                        sender.sendMessage(Component.text("you send an unknown command").color(TextColor.color(255,100,100)));
                }
            }
             if(command.getName().equalsIgnoreCase("wolf")){
                 String message = String.join(" ",args);
                 wareWolfGame.getWolfChat().sendMessageWolf(snd, message);
             }
        }
        return false;
    }
}
