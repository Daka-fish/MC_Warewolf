package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.Manager.*;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WolfGame {

    private final WareWolf wareWolf;
    private int day;
    private boolean isRunning;
    private TimeZone timeZone;
    private final ArrayList<WolfPlayer> players;

    private final TimeZoneManager timeZoneManager;
    private final RoleManager roleManager;
    private final VoteManager voteManager;
    private final WolfManager wolfManager;
    private final KnightManager knightManager;

    public WolfGame(WareWolf wareWolf){
        this.wareWolf = wareWolf;
        this.day = 0;
        this.isRunning = false;
        this.timeZone = TimeZone.DAY;
        this.players = new ArrayList<>();
        this.timeZoneManager = new TimeZoneManager(wareWolf, this);
        this.roleManager = new RoleManager(this);
        this.voteManager = new VoteManager(this);
        this.wolfManager = new WolfManager(this);
        this.knightManager = new KnightManager(this);
    }

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public boolean isRunning() {return isRunning;}
    public void setRunning(boolean running) {this.isRunning = running;}
    public TimeZone getTimeZone() {return timeZone;}
    public void setTimeZone(TimeZone timeZone) {this.timeZone = timeZone;}
    public ArrayList<WolfPlayer> getPlayers() {return players;}

    public TimeZoneManager getTimeZoneManager() {return timeZoneManager;}
    public RoleManager getRoleManager() {return roleManager;}
    public VoteManager getVoteManager() {return voteManager;}
    public WolfManager getWolfManager() {return wolfManager;}
    public KnightManager getKnightManager() {return knightManager;}

    public void resetDailyStatus(){
        wolfManager.resetPool();
        getPlayers().forEach(wolfPlayer -> {
            wolfPlayer.setHasVote(false);
            wolfPlayer.setHasActioned(false);
            wolfPlayer.setVotesCount(0);
            wolfPlayer.setProtected(false);
        });
    }

    public void checkWinner(){
        ArrayList<WolfPlayer> alivePlayers = new ArrayList<>();
        getPlayers().forEach(wolfPlayer -> {
            if(wolfPlayer.isAlive()) alivePlayers.add(wolfPlayer);
        });
        int whiteCount = 0;
        int blackCount = 0;
        for(WolfPlayer wolfPlayer : alivePlayers){
            if(wolfPlayer.getRole().getColor().equalsIgnoreCase(Role.INNOCENT.getColor())){
                whiteCount ++;
            } else if (wolfPlayer.getRole().getColor().equalsIgnoreCase(Role.WOLF.getColor())) {
                blackCount ++;
            }
        }
        if(whiteCount <= blackCount){
            setRunning(false);
            sendMessage("ゲーム終了、人狼の勝ち");

        }else if(0 == blackCount){
            setRunning(false);
            sendMessage("ゲーム終了、市民の勝ち");
        }
        players.forEach(player->sendMessage(player.getPlayer().getName() +" : "+ player.getRole().getRoleName()));
    }

    public void sendMessage(String message){getPlayers().forEach(player -> player.getPlayer().sendMessage("[人狼]"+message));}
    public void sendLogger(String message){wareWolf.putLogger(message);}

    public WolfPlayer getWolfPlayer(Player player){
        for(WolfPlayer wp : getPlayers()){
            if(wp.getPlayer().equals(player)){
                return wp;
            }
        }
        return null;
    }

    public void giveRoleItem(){
        WolfItem wolfItem = new WolfItem();
        players.forEach(wolfPlayer -> {
            switch (wolfPlayer.getRole()){
                case WOLF:
                    wolfPlayer.getPlayer().sendMessage("追加されたアイテムを右クリックで行動してください");
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getKillItem());
                    break;

                case KNIGHT:
                    wolfPlayer.getPlayer().sendMessage("追加されたアイテムを右クリックで行動してください");
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getProtectItem());
                    break;

                case INNOCENT:
                    wolfPlayer.getPlayer().sendMessage("市民が夜の間にできる行動はありません");
                    break;

                default:
                    break;
            }
        });
    }
}
