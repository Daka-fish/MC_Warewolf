package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfEvent;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.InvEvent;
import net.tv.twitch.chrono_fish.warewolf.WolfPack.WolfChat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WareWolf extends JavaPlugin {

    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        WolfGame wolfGame = new WolfGame(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WareWolfEvent(wolfGame), this);
        pluginManager.registerEvents(new InvEvent(wolfGame), this);
        pluginManager.registerEvents(new WolfChat(wolfGame), this);
        getCommand("ww").setExecutor(new Commands(this, wolfGame));
    }

    @Override
    public void onDisable() {
        //save game information to file;
    }

    public static void putLog(String message){ logger.info(message);}
}
