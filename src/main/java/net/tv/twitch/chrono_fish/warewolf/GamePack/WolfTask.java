package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.scheduler.BukkitRunnable;

public class WolfTask extends BukkitRunnable {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;
    private final TimeZone timeZone;
    private final BossBar bossBar;
    private int time;

    public WolfTask(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
        this.timeZone = wolfGame.getTimeZone();
        this.bossBar = BossBar.bossBar(Component.text("§l-"+wolfGame.getTimeZone().getName()+"-"), 1.0F, BossBar.Color.valueOf(wolfGame.getTimeZone().getColor()), BossBar.Overlay.NOTCHED_20);
        this.time = wolfGame.getTimeZone().getTime();
        wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
            wolfPlayer.getPlayer().showBossBar(bossBar);
        });
    }

    @Override
    public void run() {
        if(time == 0){
            cancel();
            wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
                wolfPlayer.getPlayer().closeInventory();
                wolfPlayer.getPlayer().hideBossBar(bossBar);
            });
            wolfGame.getTimeZoneManager().timeZoneEnd();
            return;
        }
        wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
            bossBar.progress((float) time/timeZone.getTime());
            wolfPlayer.getPlayer().showBossBar(bossBar);
        });
        time--;
    }
}
