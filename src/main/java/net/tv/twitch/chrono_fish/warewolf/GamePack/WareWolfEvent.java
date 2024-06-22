package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.InvPack.WareWolfInv;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WareWolfEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();
        wareWolfGame.getWareWolfPlayers().put(joinedPlayer, new WareWolfPlayer(joinedPlayer,Role.INNOCENT));

        PlayerScoreboard playerScoreboard = new PlayerScoreboard(joinedPlayer);
        wareWolfGame.getScoreboardHashMap().put(joinedPlayer, playerScoreboard);
        joinedPlayer.setScoreboard(playerScoreboard.getBoard());
        wareWolfGame.getBossBarManager().getBossBar().addPlayer(joinedPlayer);
    }
}
