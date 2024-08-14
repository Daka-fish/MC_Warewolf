package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class WolfGame {

    private boolean isRunning;
    private TimeZone timeZone;
    private final ArrayList<WolfPlayer> alivePlayers;
    private final ArrayList<Role> roles;

    public WolfGame(){
        isRunning = false;
        timeZone = TimeZone.DAY;
        alivePlayers = new ArrayList<>();
        roles = new ArrayList<>();
    }

    public boolean getRunning() { return isRunning; }
    public void setRunning(boolean running) { this.isRunning = running; }

    public TimeZone getTimeZone() { return timeZone; }
    public void setTimeZone(TimeZone timeZone) { this.timeZone = timeZone; }

    public ArrayList<WolfPlayer> getAlivePlayers() { return alivePlayers; }

    public ArrayList<Role> getRoles() { return roles; }

    public WolfPlayer findPlayer(Player player){
        for(WolfPlayer wp : alivePlayers){
            if(wp.getPlayer().equals(player)){
                return wp;
            }
        }
        return new WolfPlayer(player, this);
    }

    public void setRoles(){
        roles.add(Role.INNOCENT);
        roles.add(Role.WOLF);
    }

    public void assignRole(){
        int index = 0;
        Collections.shuffle(roles);
        for(WolfPlayer alivePlayer : alivePlayers){
            alivePlayer.setRole(roles.get(index));
            index++;
        }
    }

    public void changeTurn(){
        if(isRunning){
            switch (timeZone){
                case DAY:
                    timeZone = TimeZone.VOTE;
                    break;

                case VOTE:
                    timeZone = TimeZone.NIGHT;
                    break;

                case NIGHT:
                    timeZone = TimeZone.DAY;
                    break;
            }
        }
    }

    public WolfPlayer getMostVotedPlayer(){
        int maxVoted = 0;
        WolfPlayer target = null;
        for(WolfPlayer wp : alivePlayers){
            if(wp.getVotesCount() >= maxVoted){
                target = wp;
            }
        }
        return target;
    }


}
