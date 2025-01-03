package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.Manager.*;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class WolfGame {

    private int dayCount;
    private boolean isRunning;
    private TimeZone timeZone;
    private final ArrayList<WolfPlayer> participants;

    private final ConfigManager configManager;
    private final TimeZoneManager timeZoneManager;
    private final RoleManager roleManager;

    private final WolfItem wolfItem;
    private final WolfInv wolfInv;

    private final ArrayList<WolfPlayer> killPool;

    public WolfGame(WareWolf wareWolf){
        this.dayCount = 0;
        this.isRunning = false;
        this.timeZone = TimeZone.DAY;
        this.participants = new ArrayList<>();
        this.configManager = new ConfigManager(wareWolf);
        this.timeZoneManager = new TimeZoneManager(wareWolf, this);
        this.roleManager = new RoleManager(this);
        this.wolfItem = new WolfItem(this);
        this.wolfInv = new WolfInv(this);
        this.killPool = new ArrayList<>();
        for(TimeZone tz : TimeZone.values()){
            tz.setTime(configManager.getConfigTime(tz));
        }
    }

    public int getDayCount(){return dayCount;}
    public void setDayCount(int dayCount){this.dayCount = dayCount;}
    public boolean isRunning(){return isRunning;}
    public void setRunning(boolean running){this.isRunning = running;}
    public TimeZone getTimeZone(){return timeZone;}
    public void setTimeZone(TimeZone timeZone){this.timeZone = timeZone;}
    public ArrayList<WolfPlayer> getParticipants(){return participants;}

    public ConfigManager getConfigManager() {return configManager;}
    public TimeZoneManager getTimeZoneManager() {return timeZoneManager;}
    public RoleManager getRoleManager() {return roleManager;}

    public WolfItem getWolfItem() {return wolfItem;} //不必要
    public WolfInv getWolfInv(){return wolfInv;}

    public ArrayList<WolfPlayer> getKillPool() {return killPool;}

    public void checkWinner(){
        ArrayList<WolfPlayer> alivePlayers = new ArrayList<>();
        getParticipants().forEach(wolfPlayer -> {
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
        participants.forEach(player->sendMessage(player.getPlayer().getName() +" : "+ player.getRole().getRoleName()));
    }

    public void sendMessage(String message){getParticipants().forEach(player -> player.getPlayer().sendMessage("[人狼]"+message));}

    public WolfPlayer getWolfPlayer(Player player){
        for(WolfPlayer wp : getParticipants()){
            if(wp.getPlayer().equals(player)){
                return wp;
            }
        }
        return null;
    }

    public ArrayList<WolfPlayer> getAlivePlayers(){
        ArrayList<WolfPlayer> alivePlayers = new ArrayList<>();
        for(WolfPlayer wp : participants){
            if(wp.isAlive()) alivePlayers.add(wp);
        }
        return alivePlayers;
    }

    public ArrayList<WolfPlayer> getWolfs(){
        ArrayList<WolfPlayer> wolfs = new ArrayList<>();
        participants.forEach(participant ->{
            if(participant.getRole().equals(Role.WOLF)) wolfs.add(participant);
        });
        return wolfs;
    }

    public void giveRoleItem(){
        participants.forEach(wolfPlayer -> {
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
        participants.forEach(wolfPlayer -> {
            for(ItemStack roleItem : wolfItem.getRoleItems()){
                if(wolfPlayer.getPlayer().getInventory().contains(roleItem)) wolfPlayer.getPlayer().getInventory().remove(roleItem);
            }
        });
    }

    public void kickMostVotedPlayer(){
        int vote = 0;
        WolfPlayer mostVoted = null;
        ArrayList<WolfPlayer> players = getParticipants();
        Collections.shuffle(players); //同数の場合のランダム処理
        for(WolfPlayer wp : players){
            if(wp.getVotesCount()>vote){
                mostVoted = wp;
                vote = wp.getVotesCount();
            }
        }
        if(mostVoted != null){
            mostVoted.setAlive(false);
            sendMessage("投票によって§c"+mostVoted.getPlayer().getName()+"§fを追放しました");
            return;
        }
        sendMessage("追放者はいませんでした");
    }

    public void killPlayer(){
        if(killPool.size() != 0){
            Collections.shuffle(killPool);
            WolfPlayer target = killPool.get(0);
            if(target != null && !target.isProtected()){
                target.setAlive(false);
                sendMessage(target.getPlayer().getName()+"が何者かに殺された");
                return;
            }
        }
        sendMessage("昨晩の犠牲者はいなかった");
    }
}
