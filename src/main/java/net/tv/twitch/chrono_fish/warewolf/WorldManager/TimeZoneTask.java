package net.tv.twitch.chrono_fish.warewolf.WorldManager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.GameState;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeZoneTask extends BukkitRunnable {

    private final WareWolf wareWolf;
    private final WareWolfGame wareWolfGame;
    private int time;

    public TimeZoneTask(WareWolf wareWolf, WareWolfGame wareWolfGame){
        this.wareWolf = wareWolf;
        this.wareWolfGame = wareWolfGame;
        //this.currentTimeZone = wareWolfGame.getTimeZone()
        TimeZone currentTime = wareWolfGame.getTimeZone();
        time = currentTime.getTime();
    }

    @Override
    public void run() {
        time --;
        if(time < 0){
            cancel();
            wareWolfGame.getGameManager().timeZoneEndTask();
            if(wareWolfGame.getGameState().equals(GameState.RUNNING)){
                new TimeZoneTask(wareWolf, wareWolfGame).runTaskTimer(wareWolf,0,20);
            }
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> wareWolfGame.getScoreboardHashMap().get(player).updateTime(time));
    }
}
