package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class WolfManager {

    private final WolfGame wolfGame;
    private final ArrayList<WolfPlayer> targetPool;

    public WolfManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.targetPool = new ArrayList<>();
    }

    public void addTarget(WolfPlayer wolfPlayer){
        targetPool.add(wolfPlayer);
    }

    public void killTarget(){
        Collections.shuffle(targetPool);
        WolfPlayer target = targetPool.get(0);
        if(target != null && !target.isProtected()){
            target.setAlive(false);
            wolfGame.sendMessage(target.getPlayer().getName()+"が何者かに殺された");
        }else{
            wolfGame.sendMessage("今晩の犠牲者はいなかった");
        }
    }

    public void resetPool(){
        targetPool.clear();
    }
}
