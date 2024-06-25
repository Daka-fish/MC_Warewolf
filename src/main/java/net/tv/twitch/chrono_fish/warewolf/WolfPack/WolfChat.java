package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WolfChat {

    private ArrayList<WareWolfPlayer> alivePlayer;

    public WolfChat(ArrayList<WareWolfPlayer> alivePlayers){
        this.alivePlayer = alivePlayers;
    }

    public void setAlivePlayer(ArrayList<WareWolfPlayer> alivePlayer) {
        this.alivePlayer = alivePlayer;
    }

    public void sendMessageWolf(Player player, String message){
        Component component = Component.text("["+player.getName()+"] "+message).color(TextColor.color(255,120,120));
        for(WareWolfPlayer wp : alivePlayer){
            if(wp.getRole().equals(Role.WOLF)){
                wp.getPlayer().sendMessage(component);
            }
        }
    }
}
