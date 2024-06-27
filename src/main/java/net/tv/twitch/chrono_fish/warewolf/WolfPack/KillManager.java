package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;

import java.util.HashMap;

public class KillManager {

    private WareWolfPlayer target;
    private final HashMap<WareWolfPlayer, WareWolfPlayer> wolfSelections;

    public KillManager(){
        target = null;
        wolfSelections = new HashMap<>();
    }

    public HashMap<WareWolfPlayer, WareWolfPlayer> getWolfSelections(){ return wolfSelections; }

    public boolean setTarget(WareWolfPlayer wolf, WareWolfPlayer wp){
        WareWolfPlayer commonTarget = null;

        for(WareWolfPlayer selectedPayer : wolfSelections.values()){
            if(commonTarget == null){
                commonTarget = selectedPayer;
            } else if (!commonTarget.equals(selectedPayer)) {
                wolf.getPlayer().sendMessage("you have to chose same target as your buddy do");
                return false;
            }
        }
        target = commonTarget;
        return true;
    }

    public void reset(){}

    public void killPlayer(){
        if(target != null){
            target.setAlive(false);
        }
    }
}
