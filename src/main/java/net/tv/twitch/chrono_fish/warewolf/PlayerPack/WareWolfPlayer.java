package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

public class WareWolfPlayer {

    private final Player player;
    private Role role;
    private int votesCount;
    private boolean isAline;
    private boolean hasVote;
    private boolean isProtected;

    public WareWolfPlayer(Player player, Role role){
        this.player = player;
        this.role = role;
        votesCount = 0;
        isAline = true;
        hasVote = false;
        isProtected = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public int getVotesCount() {
        return votesCount;
    }
    public void setVotesCount(int votesCount){
        this.votesCount = votesCount;
    }

    public boolean isAline() {
        return isAline;
    }
    public void setAlive(boolean isAline){
        this.isAline = isAline;
    }

    public boolean isHasVote() {
        return hasVote;
    }
    public void setHasVote(boolean hasVote) {
        this.hasVote = hasVote;
    }

    public boolean isProtected() {
        return isProtected;
    }
    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public void vote(WareWolfPlayer wp){
        if(WareWolf.getWareWolfgame().getTimeZone().equals(TimeZone.VOTE)){
            if(!hasVote){
                hasVote = true;
                wp.setVotesCount(wp.getVotesCount()+1);
            }
        }
    }

    public void kill(WareWolfPlayer wp){
        if(WareWolf.getWareWolfgame().getTimeZone().equals(TimeZone.NIGHT)){
            if(role.equals(Role.WOLF) && !wp.getRole().equals(Role.WOLF)){
                wp.setAlive(false);
            }
        }
    }
}
