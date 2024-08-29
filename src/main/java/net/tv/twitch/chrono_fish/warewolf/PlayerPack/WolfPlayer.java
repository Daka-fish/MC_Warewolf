package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import org.bukkit.entity.Player;

public class WolfPlayer {

    private final WolfGame wolfGame;
    private final Player player;
    private Role role;
    private int votesCount;
    private boolean isAlive;
    private boolean hasVote;
    private boolean hasActioned;
    private boolean isProtected;

    public WolfPlayer(WolfGame wolfGame, Player player){
        this.wolfGame = wolfGame;
        this.player = player;
        this.role = Role.INNOCENT;
        this.votesCount = 0;
        this.isAlive = true;
        this.hasVote = false;
        this.hasActioned = false;
        this.isProtected = false;
    }

    public Player getPlayer() {return player;}
    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}
    public int getVotesCount() {return votesCount;}
    public void setVotesCount(int votesCount){this.votesCount = votesCount;}
    public boolean getIsAlive() {return isAlive;}
    public void setAlive(boolean isAline){this.isAlive = isAline;}
    public boolean hasVote() {return hasVote;}
    public void setHasVote(boolean hasVote) {this.hasVote = hasVote;}
    public boolean hasActioned() { return hasActioned; }
    public void setHasActioned(boolean hasActioned) {this.hasActioned = hasActioned;}
    public boolean isProtected() {return isProtected;}
    public void setProtected(boolean isProtected) {this.isProtected = isProtected;}

    public void vote(WolfPlayer voteTarget){
        if(!player.equals(voteTarget.getPlayer())){
            if(!hasVote){
                if(voteTarget.isAlive){
                    hasVote = true;
                    voteTarget.setVotesCount(voteTarget.getVotesCount()+1);
                    player.sendMessage("§a"+voteTarget.getPlayer().getName()+"§f に投票しました");
                }else{
                    player.sendMessage("§c死亡しているプレイヤーには投票できません");
                }
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
                    wolfGame.getWolfManager().addTarget(wp);
                } else {
                    player.sendMessage("§c仲間は選択できません");
                }
            } else {
                player.sendMessage("§c自分自身は選択できません");
            }
        }
    }

    public void protect(WolfPlayer wp){
        if(!wp.getPlayer().equals(player)){
            if(!wp.equals(wolfGame.getKnightManager().getYesterdayTarget())){
                hasActioned = true;
                player.sendMessage("今夜は§a"+wp.getPlayer().getName()+"§fを守ります");
                wolfGame.getKnightManager().setYesterdayTarget(wp);
                wp.setProtected(true);
            }else{
                player.sendMessage("§c昨晩と同じプレイヤーは選択できません");
            }
        }else{
            player.sendMessage("§c自分を選択することはできません");
        }
    }

    public void predict(WolfPlayer wp){
        if(!wp.getPlayer().equals(player) && wp.getIsAlive()){
            player.sendMessage(wp.getPlayer().getName()+"は "+wp.getRole().getColor());
        }else{
            player.sendMessage("§c自分または死者を選択することはできません");
        }

    }
}
