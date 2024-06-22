package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

public class GameManager {

    public GameManager(Player sender, String[] args){
        WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
        switch (args[0]){
            case "start":
                wareWolfGame.setGameState(GameState.RUNNING);
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
