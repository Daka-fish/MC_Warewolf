package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WareWolfEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
        PlayerScoreboard playerScoreboard = new PlayerScoreboard(joinedPlayer);
        wareWolfGame.getWareWolfPlayers().put(joinedPlayer.getName(), new WareWolfPlayer(joinedPlayer,Role.VILLAGER));
        wareWolfGame.getScoreboardHashMap().put(joinedPlayer, playerScoreboard);
        joinedPlayer.setScoreboard(playerScoreboard.getBoard());
    }
}
