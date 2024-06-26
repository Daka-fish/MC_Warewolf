package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WolfPack.KillManager;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

public class WareWolfPlayer {

    private final Player player;
    private Role role;
    private int votesCount;
    private boolean isAline;
    private boolean hasVote;
    private boolean hasActioned;
    private boolean isProtected;

    public WareWolfPlayer(Player player, Role role){
        this.player = player;
        this.role = role;
        votesCount = 0;
        isAline = true;
        hasVote = false;
        hasActioned = false;
        isProtected = false;
    }

    public Player getPlayer() { return player; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public int getVotesCount() { return votesCount; }
    public void setVotesCount(int votesCount){ this.votesCount = votesCount; }

    public boolean isAline() { return isAline; }
    public void setAlive(boolean isAline){ this.isAline = isAline; }

    public boolean isHasVote() { return hasVote; }
    public void setHasVote(boolean hasVote) { this.hasVote = hasVote; }

    public boolean isHasActioned() { return hasActioned; }
    public void setHasActioned(boolean hasActioned) { this.hasActioned = hasActioned; }

    public boolean isProtected() { return isProtected; }
    public void setProtected(boolean isProtected) { this.isProtected = isProtected; }

    public void vote(WareWolfPlayer wp){
        if(WareWolf.getWareWolfgame().getTimeZone().equals(TimeZone.VOTE)){
            if(!player.equals(wp.getPlayer())){
                if(!hasVote){
                    hasVote = true;
                    wp.setVotesCount(wp.getVotesCount()+1);
                    player.sendMessage("you vote to "+wp.getPlayer().getName());
                } else {
                    player.sendMessage("you have already voted");
                }
            } else {
                player.sendMessage("you can't vote to yourself");
            }
        } else {
            player.sendMessage("It's not time yet");
        }
    }

    public void kill(WareWolfPlayer wp){
        if(WareWolf.getWareWolfgame().getTimeZone().equals(TimeZone.NIGHT)){
            if(role.equals(Role.WOLF)){
                if(!player.equals(wp.getPlayer())){
                    if(!wp.getRole().equals(Role.WOLF)){
                        if(WareWolf.getWareWolfgame().getKillManager().setTarget(this, wp)){
                            hasActioned = true;
                        }
                    } else {
                        player.sendMessage("you can't kill your buddy");
                    }
                } else {
                    player.sendMessage("you can't kill yourself");
                }
            }
        }
    }

    public void protect(WareWolfPlayer wp){
        player.sendMessage("you protect "+wp.getPlayer().getName());
        wp.setProtected(true);
    }
}
