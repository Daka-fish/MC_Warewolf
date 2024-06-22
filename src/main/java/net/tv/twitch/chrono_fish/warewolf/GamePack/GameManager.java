package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class GameManager {

    private final WareWolfGame wareWolfGame;

    public GameManager(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
    }

    public void assignRole(){
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            Player player = wp.getPlayer();
            wareWolfGame.getScoreboardHashMap().get(player).updateRole(Role.WOLF);
            wp.setRole(Role.WOLF);
            WareWolf.putLog(player.getName()+" : "+ wp.getRole());
        }
    }

    public void setAlivePlayers(){
        for(Map.Entry<Player, WareWolfPlayer> entry: wareWolfGame.getWareWolfPlayers().entrySet()){
            WareWolfPlayer wareWolfPlayer = entry.getValue();
            if(wareWolfPlayer.isAline()){
                wareWolfGame.getAlivePlayers().add(wareWolfPlayer);
            }
        }
    }
}
