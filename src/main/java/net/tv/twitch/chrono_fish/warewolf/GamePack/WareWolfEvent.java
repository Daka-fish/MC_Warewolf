package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WareWolfEvent implements Listener {

    private WolfGame wolfGame;

    public WareWolfEvent(WolfGame wolfGame){
        this.wolfGame = wolfGame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        wolfGame.getWareWolfPlayers().put(joinedPlayer, new WareWolfPlayer(joinedPlayer,Role.INNOCENT,wolfGame));

        PlayerScoreboard playerScoreboard = new PlayerScoreboard(wolfGame, joinedPlayer);
        wolfGame.getScoreboardHashMap().put(joinedPlayer, playerScoreboard);
        joinedPlayer.setScoreboard(playerScoreboard.getBoard());
        wolfGame.getBossBarManager().getBossBar().addPlayer(joinedPlayer);
    }
}
