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

    private final WolfScoreboard wolfScoreboard;

    public WolfPlayer(WolfGame wolfGame, Player player){
        this.wolfGame = wolfGame;
        this.player = player;
        this.role = Role.INNOCENT;
        this.votesCount = 0;
        this.isAlive = true;
        this.hasVote = false;
        this.hasActioned = false;
        this.isProtected = false;
        this.wolfScoreboard = new WolfScoreboard(this);
    }

    public WolfGame getWolfGame() {return wolfGame;}
    public Player getPlayer() {return player;}
    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}
    public int getVotesCount() {return votesCount;}
    public void setVotesCount(int votesCount){this.votesCount = votesCount;}
    public boolean isAlive() {return isAlive;}
    public void setAlive(boolean isAline){this.isAlive = isAline;}
    public boolean hasVote() {return hasVote;}
    public void setHasVote(boolean hasVote) {this.hasVote = hasVote;}
    public boolean hasActioned() { return hasActioned; }
    public void setHasActioned(boolean hasActioned) {this.hasActioned = hasActioned;}
    public boolean isProtected() {return isProtected;}
    public void setProtected(boolean isProtected) {this.isProtected = isProtected;}

    public WolfScoreboard getWolfScoreboard() {return wolfScoreboard;}

    public void vote(WolfPlayer voteTarget){
        if(!player.equals(voteTarget.getPlayer())){
            if(!hasVote()){
                if(voteTarget.isAlive()){
                    setHasVote(true);
                    voteTarget.setVotesCount(voteTarget.getVotesCount()+1);
                    player.sendMessage("§a"+voteTarget.getPlayer().getName()+"§f に投票しました");
                    player.getInventory().remove(wolfGame.getWolfItem().getVotePaper());
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
        if(getRole().equals(Role.WOLF)){
            if(wolfGame.getDay() != 0){
                if(!player.equals(wp.getPlayer())){
                    if(!wp.getRole().equals(Role.WOLF)){
                        if(!hasActioned()){
                            setHasActioned(true);
                            wolfGame.getWolfManager().addTarget(wp);
                            player.sendMessage("§a"+wp.getPlayer().getName()+"§f を選択しました");
                            player.getInventory().remove(wolfGame.getWolfItem().getKillItem());
                        }else{
                            player.sendMessage("§c既に選択しています");
                        }
                    }else{
                        player.sendMessage("§c仲間は選択できません");
                    }
                } else {
                    player.sendMessage("§c自身は選択できません");
                }
            }else{
                player.sendMessage("§c初日は殺害できません");
            }
        }
    }

    public void protect(WolfPlayer wp){
        if(getRole().equals(Role.KNIGHT)){
            if(!wp.getPlayer().equals(player)){
                if(!hasActioned()){
                    if(!wp.equals(wolfGame.getKnightManager().getYesterdayTarget())){
                        setHasActioned(true);
                        wolfGame.getKnightManager().setYesterdayTarget(wp);
                        wp.setProtected(true);
                        player.sendMessage("今夜は§a"+wp.getPlayer().getName()+"§fを守ります");
                        player.getInventory().remove(wolfGame.getWolfItem().getProtectItem());
                    }else{
                        player.sendMessage("§c昨晩と同じプレイヤーは選択できません");
                    }
                }else{
                    player.sendMessage("§c既に選択しています");
                }
            }else{
                player.sendMessage("§c自身を選択することはできません");
            }
        }
    }

    public void predict(WolfPlayer wp){
        if(getRole().equals(Role.SEER)){
            if(!wp.getPlayer().equals(player) && wp.isAlive()){
                if(!hasActioned()){
                    setHasActioned(true);
                    player.sendMessage(wp.getPlayer().getName()+"は【"+wp.getRole().getColor()+"§f】でした");
                    player.getInventory().remove(wolfGame.getWolfItem().getSeerItem());
                }else{
                    player.sendMessage("§c既に占いました");
                }
            }else{
                player.sendMessage("§c自分または死者を選択することはできません");
            }
        }
    }

    public void medium(WolfPlayer wp){
        if(getRole().equals(Role.MEDIUM)){
            if(!wp.getPlayer().equals(player) && !wp.isAlive()){
                if(!hasActioned()){
                    setHasActioned(true);
                    player.sendMessage(wp.getPlayer().getName()+"は【"+wp.getRole().getColor()+"§f】でした");
                    player.getInventory().remove(wolfGame.getWolfItem().getMediumItem());
                }else{
                    player.sendMessage("§既に死体を漁りました");
                }
            }else{
                player.sendMessage("§c自分または生存者を選択することはできません");
            }
        }
    }
}
