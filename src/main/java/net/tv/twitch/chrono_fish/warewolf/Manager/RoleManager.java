package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class RoleManager {

    private final WolfGame wolfGame;
    private final ArrayList<Role> roles;

    public RoleManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.roles = wolfGame.getConfigManager().getRoles();
    }

    public ArrayList<Role> getRoles() {
        Collections.sort(roles);
        return roles;
    }

    public void assignRole(){
        int index = 0;
        Collections.shuffle(roles);
        for(WolfPlayer wolfPlayer : wolfGame.getWolfPlayers()){
            if(index >= roles.size()){
                wolfPlayer.setRole(Role.INNOCENT);
                continue;
            }
            wolfPlayer.setRole(roles.get(index));
            index ++;
        }
    }

    public void addRole(Role role){
        roles.add(role);
        wolfGame.getConfigManager().addConfigRole(role);
    }
    public void removeRole(Role role){
        roles.remove(role);
        wolfGame.getConfigManager().removeConfigRole(role);
    }
}
