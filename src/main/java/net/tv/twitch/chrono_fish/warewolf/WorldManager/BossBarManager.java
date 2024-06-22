package net.tv.twitch.chrono_fish.warewolf.WorldManager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BossBarManager {

    private final WareWolfGame wareWolfGame;
    private final BossBar bossBar;

    public BossBarManager(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
        TimeZone timeZone = wareWolfGame.getTimeZone();
        this.bossBar = Bukkit.createBossBar(timeZone.getTimeName(), BarColor.valueOf(timeZone.getColor()), BarStyle.SEGMENTED_20);
    }

    public BossBar getBossBar() { return bossBar; }

    public void reloadBar(){
        bossBar.setTitle(wareWolfGame.getTimeZone().getTimeName());
        bossBar.setColor(BarColor.valueOf(wareWolfGame.getTimeZone().getColor()));
    }
}
