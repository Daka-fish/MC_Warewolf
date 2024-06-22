package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfEvent;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.InvEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WareWolf extends JavaPlugin {

    private static WareWolfGame wareWolfGame;

    private static Logger logger;

    @Override
    public void onEnable() {
        wareWolfGame = new WareWolfGame();
        logger = getLogger();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WareWolfEvent(), this);
        pluginManager.registerEvents(new InvEvent(), this);
        getCommand("ww").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        //save game information to file;
    }

    public static WareWolfGame getWareWolfgame(){ return wareWolfGame; }
    public static void putLog(String message){ logger.info(message);}
}
