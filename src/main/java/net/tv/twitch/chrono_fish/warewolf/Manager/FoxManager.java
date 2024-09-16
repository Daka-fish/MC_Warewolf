package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

public class FoxManager {

    private final WolfGame wolfGame;

    public FoxManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
    }

    public WolfPlayer getFoxPlayer(){
        WolfPlayer foxPlayer = null;
        for(WolfPlayer wolfPlayer : wolfGame.getWolfPlayers()){
            if(wolfPlayer.getRole().equals(Role.FOX)) foxPlayer = wolfPlayer;
        }
        return foxPlayer;
    }
}
