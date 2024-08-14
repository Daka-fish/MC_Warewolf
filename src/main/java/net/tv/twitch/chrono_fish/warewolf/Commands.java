package net.tv.twitch.chrono_fish.warewolf;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameManager;
import net.tv.twitch.chrono_fish.warewolf.GamePack.GameState;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WareWolfInv;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZoneTask;
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
            GameManager gameManager = wolfGame.getGameManager();
            if(command.getName().equalsIgnoreCase("ww")){
                switch (args[0]){
                    case "start":
                        if(wolfGame.getGameState().equals(GameState.FINISHED)){
                            gameManager.setAlivePlayers();
                            gameManager.setRoles();
                            gameManager.assignRole();
                            wolfGame.setGameState(GameState.RUNNING);
                            wolfGame.setTimeZone(TimeZone.NIGHT);
                            wolfGame.getBossBarManager().reloadBar();
                            new TimeZoneTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
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
                        wolfGame.setGameState(GameState.FINISHED);
                        wolfGame.setTimeZone(TimeZone.DAY);
                        wolfGame.getAlivePlayers().clear();
                        wolfGame.getBossBarManager().reloadBar();
                        break;

                    case "vote":
                        wolfGame.setTimeZone(TimeZone.VOTE);
                        wolfGame.getBossBarManager().reloadBar();
                        break;

                    case "action":
                        WareWolfInv wareWolfInv = new WareWolfInv(wolfGame);
                        snd.openInventory(wareWolfInv.actionInv());
                        break;

                    case "tstart":
                        new TimeZoneTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                        break;

                    default:
                        sender.sendMessage(Component.text("you send an unknown command").color(TextColor.color(255,100,100)));
                }
            }
        }
        return false;
    }
}
