package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WolfChat implements Listener {

    WareWolfGame wareWolfGame;

    public WolfChat(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e){
        if(wareWolfGame.getTimeZone().equals(TimeZone.NIGHT)){
            e.setCancelled(true);
            WareWolfPlayer sender = wareWolfGame.getWareWolfPlayers().get(e.getPlayer());
            if(sender.getRole().equals(Role.WOLF) && sender.isAline()){
                Component message = Component
                        .text("["+e.getPlayer().getName()+"] ")
                        .color(TextColor.color(255,50,50))
                        .append(e.message());
                for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
                    if(wp.getRole().equals(Role.WOLF)){
                        wp.getPlayer().sendMessage(message);
                    }
                }
            }
        }
    }
}
