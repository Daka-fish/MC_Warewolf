package net.tv.twitch.chrono_fish.warewolf.WorldManager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeZoneTask extends BukkitRunnable {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;
    private int time;

    public TimeZoneTask(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
        TimeZone currentTime = wolfGame.getTimeZone();
        time = currentTime.getTime();
    }

    @Override
    public void run() {
        time --;
        if(time < 0){
            cancel();
            wolfGame.getGameManager().timeZoneEndTask();
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> wolfGame.getScoreboardHashMap().get(player).updateTime(time));
    }
}
