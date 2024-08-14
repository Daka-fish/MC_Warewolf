package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import org.bukkit.entity.Player;

public class WolfPlayer {

    private final WolfGame wolfGame;
    private final Player player;
    private Role role;
    private int votesCount;
    private boolean isAline;
    private boolean hasVote;
    private boolean hasActioned;
    private boolean isProtected;

    public WolfPlayer(Player player, WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.player = player;
        this.role = Role.INNOCENT;
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

    public void reset(){
        hasVote = false;
        hasActioned = false;
        votesCount = 0;
        isProtected = false;
    }

    public void vote(WolfPlayer voteTarget){
        if(!player.equals(voteTarget.getPlayer())){
            if(!hasVote){
                hasVote = true;
                voteTarget.setVotesCount(voteTarget.getVotesCount()+1);
                player.sendMessage("§a"+voteTarget.getPlayer().getName()+"§f に投票しました");
            }else{
                player.sendMessage("§c既に投票してます");
            }
        }else{
            player.sendMessage("§c自分自身には投票できません");
        }
    }

    public void kill(WolfPlayer wp){
        if(role.equals(Role.WOLF)){
            if(!player.equals(wp.getPlayer())){
                if(!wp.getRole().equals(Role.WOLF)){
                    hasActioned = true;
                } else {
                    player.sendMessage("you can't kill your buddy");
                }
            } else {
                player.sendMessage("you can't kill yourself");
            }
        }
    }

    public void protect(WolfPlayer wp){
        player.sendMessage("you protect "+wp.getPlayer().getName());
        wp.setProtected(true);
    }

    public void predict(WolfPlayer wp){
        player.sendMessage(wp.getRole().getColor());
    }
}
