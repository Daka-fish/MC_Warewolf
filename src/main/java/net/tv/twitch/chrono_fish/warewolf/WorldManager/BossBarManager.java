package net.tv.twitch.chrono_fish.warewolf.WorldManager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class BossBarManager {

    private final WolfGame wolfGame;
    private final BossBar bossBar;

    public BossBarManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        TimeZone timeZone = wolfGame.getTimeZone();
        this.bossBar = Bukkit.createBossBar(timeZone.getTimeName(), BarColor.valueOf(timeZone.getColor()), BarStyle.SEGMENTED_20);
    }

    public BossBar getBossBar() { return bossBar; }

    public void reloadBar(){
        bossBar.setTitle(wolfGame.getTimeZone().getTimeName());
        bossBar.setColor(BarColor.valueOf(wolfGame.getTimeZone().getColor()));
    }
}
