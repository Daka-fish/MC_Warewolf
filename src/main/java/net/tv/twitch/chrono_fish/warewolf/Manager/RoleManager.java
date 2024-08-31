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
        roles = new ArrayList<>();
        roles.add(Role.INNOCENT);
        roles.add(Role.INNOCENT);
        roles.add(Role.INNOCENT);
        roles.add(Role.WOLF);
        roles.add(Role.KNIGHT);
    }

    public ArrayList<Role> getRoles() {return roles;}

    public void assignRole(){
        int index = 0;
        Collections.shuffle(roles);
        for(WolfPlayer wolfPlayer : wolfGame.getPlayers()){
            if(index >= roles.size()){
                wolfPlayer.setRole(Role.INNOCENT);
                continue;
            }
            wolfPlayer.setRole(roles.get(index));
            index ++;
        }
    }

    public void addRole(Role role){}
    public void removeRole(Role role){}
}
