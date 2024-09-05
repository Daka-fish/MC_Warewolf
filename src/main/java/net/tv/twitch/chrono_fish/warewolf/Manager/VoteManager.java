package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

import java.util.ArrayList;
import java.util.Collections;

public class VoteManager {

    private final WolfGame wolfGame;

    public VoteManager(WolfGame wolfGame){this.wolfGame = wolfGame;}

    public void kickMostVotedPlayer(){
        int vote = 0;
        WolfPlayer target = null;
        ArrayList<WolfPlayer> players = wolfGame.getWolfPlayers();
        Collections.shuffle(players);
        for(WolfPlayer wp : players){
            if(wp.getVotesCount()>vote){
                target = wp;
                vote = wp.getVotesCount();
            }
        }
        if(target != null){
            target.setAlive(false);
            wolfGame.sendMessage("投票によって§c"+target.getPlayer().getName()+"§fを追放しました");
            return;
        }
        wolfGame.sendMessage("追放者はいませんでした");
    }
}
