package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.Manager.*;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WolfGame {

    private final WareWolf wareWolf;
    private int day;
    private boolean isRunning;
    private TimeZone timeZone;
    private final ArrayList<WolfPlayer> wolfPlayers;

    private final ConfigManager configManager;
    private final TimeZoneManager timeZoneManager;
    private final RoleManager roleManager;
    private final VoteManager voteManager;
    private final WolfManager wolfManager;
    private final KnightManager knightManager;

    private final WolfItem wolfItem;

    public WolfGame(WareWolf wareWolf){
        this.wareWolf = wareWolf;
        this.day = 0;
        this.isRunning = false;
        this.timeZone = TimeZone.DAY;
        this.wolfPlayers = new ArrayList<>();
        this.configManager = new ConfigManager(wareWolf);
        this.timeZoneManager = new TimeZoneManager(wareWolf, this);
        this.roleManager = new RoleManager(this);
        this.voteManager = new VoteManager(this);
        this.wolfManager = new WolfManager(this);
        this.knightManager = new KnightManager(this);
        this.wolfItem = new WolfItem(this);
    }

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}
    public boolean isRunning() {return isRunning;}
    public void setRunning(boolean running) {this.isRunning = running;}
    public TimeZone getTimeZone() {return timeZone;}
    public void setTimeZone(TimeZone timeZone) {this.timeZone = timeZone;}
    public ArrayList<WolfPlayer> getWolfPlayers() {return wolfPlayers;}

    public ConfigManager getConfigManager() {return configManager;}
    public TimeZoneManager getTimeZoneManager() {return timeZoneManager;}
    public RoleManager getRoleManager() {return roleManager;}
    public VoteManager getVoteManager() {return voteManager;}
    public WolfManager getWolfManager() {return wolfManager;}
    public KnightManager getKnightManager() {return knightManager;}

    public WolfItem getWolfItem() {return wolfItem;}

    public void resetDailyStatus(){
        wolfManager.resetPool();
        getWolfPlayers().forEach(wolfPlayer -> {
            wolfPlayer.setHasVote(false);
            wolfPlayer.setHasActioned(false);
            wolfPlayer.setVotesCount(0);
            wolfPlayer.setProtected(false);
            wolfPlayer.getWolfScoreboard().setOwnScore();
        });
    }

    public void checkWinner(){
        ArrayList<WolfPlayer> alivePlayers = new ArrayList<>();
        getWolfPlayers().forEach(wolfPlayer -> {
            if(wolfPlayer.isAlive()) alivePlayers.add(wolfPlayer);
        });
        int whiteCount = 0;
        int blackCount = 0;
        for(WolfPlayer wolfPlayer : alivePlayers){
            if(wolfPlayer.getRole().getSide() == 0){
                whiteCount ++;
            } else if (wolfPlayer.getRole().getSide() == 1) {
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
        wolfPlayers.forEach(player->sendMessage(player.getPlayer().getName() +" : "+ player.getRole().getRoleName()));
    }

    public void sendMessage(String message){getWolfPlayers().forEach(player -> player.getPlayer().sendMessage("[人狼]"+message));}
    public void sendLogger(String message){wareWolf.putLogger(message);}

    public WolfPlayer getWolfPlayer(Player player){
        for(WolfPlayer wp : getWolfPlayers()){
            if(wp.getPlayer().equals(player)){
                return wp;
            }
        }
        return null;
    }

    public int getAlivePlayers(){
        int count = 0;
        for(WolfPlayer wp : wolfPlayers){
            if(wp.isAlive()) count++;
        }
        return count;
    }

    public void giveRoleItem(){
        wolfPlayers.forEach(wolfPlayer -> {
            switch (wolfPlayer.getRole()){
                case WOLF:
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getKillItem());
                    break;

                case KNIGHT:
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getProtectItem());
                    break;

                case SEER:
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getSeerItem());
                    break;

                case MEDIUM:
                    wolfPlayer.getPlayer().getInventory().addItem(wolfItem.getMediumItem());
                    break;

                default:
                    wolfPlayer.getPlayer().sendMessage("あなたが夜の間にできる行動はありません");
                    break;
            }
        });
    }

    public void removeRoleItem(){
        wolfPlayers.forEach(wolfPlayer -> {
            for(ItemStack roleItem : wolfItem.getRoleItems()){
                if(wolfPlayer.getPlayer().getInventory().contains(roleItem)) wolfPlayer.getPlayer().getInventory().remove(roleItem);
            }
        });
    }
}
