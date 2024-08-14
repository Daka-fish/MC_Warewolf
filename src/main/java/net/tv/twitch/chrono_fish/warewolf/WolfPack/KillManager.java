package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class KillManager {

    private final WolfGame wolfGame;
    private final HashMap<WareWolfPlayer, WareWolfPlayer> wolfSelections;
    private final ArrayList<WareWolfPlayer> currentWolfs;

    public KillManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        currentWolfs = new ArrayList<>();
        wolfSelections = new HashMap<>();
    }

    public void setCurrentWolfs(){
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            if(wp.getRole().equals(Role.WOLF)){
                currentWolfs.add(wp);
            }
        }
    }
    public void selectTarget(WareWolfPlayer wolfPlayer, WareWolfPlayer target){
        wolfSelections.put(wolfPlayer, target);
        if(wolfSelections.size() == currentWolfs.size()){
            if(isCommonTarget()){
                target.setAlive(false);
            }
        }
    }

    public boolean isCommonTarget(){
        WareWolfPlayer commonTarget = null;
        for(WareWolfPlayer selectedPayer : wolfSelections.values()){
            if(commonTarget == null){
                commonTarget = selectedPayer;
            } else if (!commonTarget.equals(selectedPayer)) {
                for(WareWolfPlayer wolfPlayer : wolfGame.getAlivePlayers()){
                    if(wolfPlayer.getRole().equals(Role.WOLF)){
                        wolfPlayer.getPlayer().sendMessage("you have to chose same target as your buddy do");
                        wolfPlayer.setHasActioned(false);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void reset(){
        currentWolfs.clear();
        wolfSelections.clear();
    }

    public void killPlayer(){
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            if(!wp.isAline()){
                wp.getPlayer().setHealth(0);
            }
        }
    }
}
