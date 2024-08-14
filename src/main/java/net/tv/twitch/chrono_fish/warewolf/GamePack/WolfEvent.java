package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WolfEvent implements Listener {

    private WolfGame wolfGame;

    public WolfEvent(WolfGame wolfGame){
        this.wolfGame = wolfGame;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        wolfGame.getAlivePlayers().add(new WolfPlayer(joinedPlayer,wolfGame));
    }
}
