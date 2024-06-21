package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;

public class GameManager {

    public GameManager(Player sender, String[] args){
        WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
        switch (args[0]){
            case "start":
                wareWolfGame.setGameState(GameState.RUNNING);
                break;

            case "time":
                sender.sendMessage("you run time command");
                break;

            default:
                sender.sendMessage(Component.text("you send unknown command").color(TextColor.color(255,0,0)));
        }
    }
}
