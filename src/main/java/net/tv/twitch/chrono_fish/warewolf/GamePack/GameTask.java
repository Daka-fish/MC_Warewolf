package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;
    private final TimeZone timeZone;
    private final BossBar bossBar;
    private int time;

    public GameTask(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
        this.timeZone = wolfGame.getTimeZone();
        this.bossBar = BossBar.bossBar(Component.text("§l-"+timeZone.getName()+"-"), 1.0F, BossBar.Color.valueOf(timeZone.getColor()), BossBar.Overlay.NOTCHED_20);
        this.time = wolfGame.getTimeZone().getTime();
        wolfGame.getPlayers().forEach(wolfPlayer -> wolfPlayer.getPlayer().showBossBar(bossBar));
    }

    @Override
    public void run() {
        if(time == 0){
            cancel();
            wolfGame.getPlayers().forEach(wolfPlayer -> wolfPlayer.getPlayer().hideBossBar(bossBar));
            wolfGame.checkWinner();
            if(wolfGame.getRunning()){
                wolfGame.changeTurn();
                new GameTask(wareWolf, wolfGame).runTaskTimer(wareWolf,100,20);
            }else{
                wolfGame.getPlayers().forEach(wolfPlayer -> wolfPlayer.getPlayer().hideBossBar(bossBar));
            }
            return;
        }
        wolfGame.getPlayers().forEach(wolfPlayer -> {
            bossBar.progress((float) time/timeZone.getTime());
            wolfPlayer.getPlayer().showBossBar(bossBar);
        });
        time--;
    }
}
