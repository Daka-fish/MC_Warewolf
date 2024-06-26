package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;

import java.util.HashMap;

public class KillManager {

    private final WareWolfGame wareWolfGame;
    private WareWolfPlayer target;
    private final HashMap<WareWolfPlayer, WareWolfPlayer> wolfSelections;

    public KillManager(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
        target = null;
        wolfSelections = new HashMap<>();
    }

    public boolean setTarget(WareWolfPlayer wolf, WareWolfPlayer wp){
        wolfSelections.put(wolf, wp);
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

    public void killPlayer(){
        if(target != null){
            target.setAlive(false);
        }
    }
}
