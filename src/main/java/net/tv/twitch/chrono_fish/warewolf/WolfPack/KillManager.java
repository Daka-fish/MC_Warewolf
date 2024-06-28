package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class KillManager {

    private final WareWolfGame wareWolfGame;
    private final HashMap<WareWolfPlayer, WareWolfPlayer> wolfSelections;
    private final ArrayList<WareWolfPlayer> currentWolfs;
    private WareWolfPlayer target;

    public KillManager(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
        currentWolfs = new ArrayList<>();
        target = null;
        wolfSelections = new HashMap<>();
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            if(wp.getRole().equals(Role.WOLF)){
                currentWolfs.add(wp);
            }
        }
    }

    public int getWolfCount() { return currentWolfs.size(); }
    public HashMap<WareWolfPlayer, WareWolfPlayer> getWolfSelections(){ return wolfSelections; }

    public void setTarget(){
        WareWolfPlayer commonTarget = null;
        for(WareWolfPlayer selectedPayer : wolfSelections.values()){
            if(commonTarget == null){
                commonTarget = selectedPayer;
            } else if (!commonTarget.equals(selectedPayer)) {
                for(WareWolfPlayer wolfPlayer : wareWolfGame.getAlivePlayers()){
                    if(wolfPlayer.getRole().equals(Role.WOLF)){
                        wolfPlayer.getPlayer().sendMessage("you have to chose same target as your buddy do");
                        wolfPlayer.setHasActioned(false);
                    }
                }
            }
        }
        target = commonTarget;
    }

    public void reset(){}

    public void killPlayer(){
        if(target != null){
            target.setAlive(false);
        }
    }
}
