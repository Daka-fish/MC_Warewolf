package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfEvent;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WareWolf extends JavaPlugin {

    @Override
    public void onEnable() {
        WolfGame wolfGame = new WolfGame(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WolfEvent(wolfGame), this);
        getCommand("ww").setExecutor(new Commands(this, wolfGame));
    }

    @Override
    public void onDisable() {
        //save game information to file;
    }

    public void putLogger(String message){getLogger().info(message);}
}
