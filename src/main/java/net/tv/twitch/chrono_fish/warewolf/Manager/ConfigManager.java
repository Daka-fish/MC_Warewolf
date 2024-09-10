package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager {

    private final WareWolf wareWolf;
    private FileConfiguration config;

    public ConfigManager(WareWolf wareWolf){
        this.wareWolf = wareWolf;
        File configFile = new File(wareWolf.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            wareWolf.saveDefaultConfig();
        }
        this.config = wareWolf.getConfig();
        wareWolf.saveConfig();
    }

    public ArrayList<Role> getRoles(){
        ArrayList<Role> configRole = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("wareWolf.roles");
        if(section != null){
            for(String roleName : section.getKeys(false)){
                int count = section.getInt(roleName);
                for(int i = 0; i<count; i++){
                    configRole.add(Role.valueOf(roleName));
                }
            }
        }
        return configRole;
    }

    public void addConfigRole(Role role){
        ConfigurationSection section = config.getConfigurationSection("wareWolf.roles");
        if(section != null){
            for(String roleName : section.getKeys(false)){
                if(roleName.equalsIgnoreCase(role.name())){
                    section.set(roleName, section.getInt(roleName)+1);
                }
            }
        }
        wareWolf.saveConfig();
    }

    public void removeConfigRole(Role role){
        ConfigurationSection section = config.getConfigurationSection("wareWolf.roles");
        if(section != null){
            for(String roleName : section.getKeys(false)){
                if(roleName.equalsIgnoreCase(role.name())){
                    section.set(roleName, section.getInt(roleName)-1);
                }
            }
        }
        wareWolf.saveConfig();
    }

}
