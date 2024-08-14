package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfEvent;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.InvEvent;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WareWolf extends JavaPlugin {

    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        WolfGame wolfGame = new WolfGame();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WolfEvent(wolfGame), this);
        pluginManager.registerEvents(new InvEvent(wolfGame, new WolfInv(wolfGame)), this);
        getCommand("ww").setExecutor(new Commands(this, wolfGame));
    }

    @Override
    public void onDisable() {
        //save game information to file;
    }

    public static void putLog(String message){logger.info(message);}
}
