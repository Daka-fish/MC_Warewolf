package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import org.bukkit.plugin.java.JavaPlugin;

public final class WareWolf extends JavaPlugin {

    private static WareWolfGame wareWolfGame;

    @Override
    public void onEnable() {
        wareWolfGame = new WareWolfGame();
        getCommand("ww").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        //save game information to file;
    }

    public static WareWolfGame getWareWolfgame(){
        return wareWolfGame;
    }
}
