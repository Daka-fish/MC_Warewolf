package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class WolfManager {

    private final WolfGame wolfGame;
    private final ArrayList<WolfPlayer> wolfs;
    private final ArrayList<WolfPlayer> targetPool;

    public WolfManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.wolfs = new ArrayList<>();
        this.targetPool = new ArrayList<>();
        for(WolfPlayer wp : wolfGame.getWolfPlayers()){
            if(wp.getRole().equals(Role.WOLF)) wolfs.add(wp);
        }
    }

    public ArrayList<WolfPlayer> getWolfs() {return wolfs;}

    public void addTarget(WolfPlayer wolfPlayer){
        targetPool.add(wolfPlayer);
    }

    public void killTarget(){
        if(targetPool.size() != 0){
            Collections.shuffle(targetPool);
            WolfPlayer target = targetPool.get(0);
            if(target != null && !target.isProtected()){
                target.setAlive(false);
                wolfGame.sendMessage(target.getPlayer().getName()+"が何者かに殺された");
                return;
            }
        }
        wolfGame.sendMessage("昨晩の犠牲者はいなかった");

    }

    public void resetPool(){targetPool.clear();}

    public void sendWolfMessage(String message){
        wolfs.forEach(wolfPlayer -> wolfPlayer.getPlayer().sendMessage(message));
    }

    public void sendWolfMessage(Component message){
        wolfs.forEach(wolfPlayer -> wolfPlayer.getPlayer().sendMessage(message));
    }
}
