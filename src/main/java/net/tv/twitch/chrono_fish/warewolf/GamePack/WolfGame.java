package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.Manager.KnightManager;
import net.tv.twitch.chrono_fish.warewolf.Manager.RoleManager;
import net.tv.twitch.chrono_fish.warewolf.Manager.VoteManager;
import net.tv.twitch.chrono_fish.warewolf.Manager.WolfManager;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WolfGame {

    private int day;
    private boolean isRunning;
    private TimeZone timeZone;
    private final ArrayList<WolfPlayer> players;

    private final RoleManager roleManager;
    private final VoteManager voteManager;
    private final WolfManager wolfManager;
    private final KnightManager knightManager;

    public WolfGame(){
        this.day = 0;
        this.isRunning = false;
        this.timeZone = TimeZone.DAY;
        this.players = new ArrayList<>();
        this.roleManager = new RoleManager(this);
        this.voteManager = new VoteManager(this);
        this.wolfManager = new WolfManager(this);
        this.knightManager = new KnightManager(this);
    }

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public boolean getRunning() {return isRunning;}
    public void setRunning(boolean running) {this.isRunning = running;}
    public TimeZone getTimeZone() {return timeZone;}
    public void setTimeZone(TimeZone timeZone) {this.timeZone = timeZone;}
    public ArrayList<WolfPlayer> getPlayers() {return players;}
    public RoleManager getRoleManager() {return roleManager;}
    public VoteManager getVoteManager() {return voteManager;}
    public WolfManager getWolfManager() {return wolfManager;}
    public KnightManager getKnightManager() {return knightManager;}

    public void resetDayInformation(){
        wolfManager.resetPool();
    }

    public void checkWinner(){
        ArrayList<WolfPlayer> alivePlayers = new ArrayList<>();
        players.forEach(wolfPlayer -> {
            if(wolfPlayer.getIsAlive()) alivePlayers.add(wolfPlayer);
        });
        int whiteCount = 0;
        int blackCount = 0;
        for(WolfPlayer wolfPlayer : alivePlayers){
            if(wolfPlayer.getRole().getColor().equalsIgnoreCase("白")){
                whiteCount ++;
            } else if (wolfPlayer.getRole().getColor().equalsIgnoreCase("黒")) {
                blackCount ++;
            }
        }
        if(whiteCount <= blackCount){
            isRunning = false;
            sendMessage("ゲーム終了、人狼の勝ち");

        }else if(0 == blackCount){
            isRunning = false;
            sendMessage("ゲーム終了、市民の勝ち");
        }
    }

    public void sendMessage(String message){players.forEach(player -> player.getPlayer().sendMessage("[人狼]"+message));}

    public WolfPlayer getWolfPlayer(Player player){
        for(WolfPlayer wp : players){
            if(wp.getPlayer().equals(player)){
                return wp;
            }
        }
        return new WolfPlayer(this, player);
    }

    public void changeTurn(){
        switch (timeZone){
            case DAY:
                timeZone = TimeZone.VOTE;
                sendMessage("投票の時間になりました");
                break;

            case VOTE:
                timeZone = TimeZone.NIGHT;
                voteManager.kickMostVotePlayer();
                sendMessage("夜になりました");
                break;

            case NIGHT:
                timeZone = TimeZone.DAY;
                day += 1;
                wolfManager.killTarget();
                sendMessage(day+"日目の朝になりました");
                break;
        }
    }
}
