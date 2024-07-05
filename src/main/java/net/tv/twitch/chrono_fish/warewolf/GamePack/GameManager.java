package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WolfPack.KillManager;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class GameManager {

    private final WareWolfGame wareWolfGame;

    public GameManager(WareWolfGame wareWolfGame){
        this.wareWolfGame = wareWolfGame;
    }

    public void assignRole(){
        int index = 0;
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            Player player = wp.getPlayer();
            Role role = wareWolfGame.getRoles().get(index);
            wareWolfGame.getScoreboardHashMap().get(player).updateRole(role);
            wp.setRole(role);
            WareWolf.putLog(player.getName()+" : "+ wp.getRole());
            index++;
        }
    }

    public void setAlivePlayers(){
        for(Map.Entry<Player, WareWolfPlayer> entry: wareWolfGame.getWareWolfPlayers().entrySet()){
            WareWolfPlayer wareWolfPlayer = entry.getValue();
            if(wareWolfPlayer.isAline()){
                wareWolfGame.getAlivePlayers().add(wareWolfPlayer);
            }
        }
    }

    public void setRoles(){
        ArrayList<Role> roles = wareWolfGame.getRoles();
        roles.add(Role.INNOCENT);
        roles.add(Role.WOLF);
        Collections.shuffle(roles);
    }

    public WareWolfPlayer getMostVoted(){
        int maxVoted = 0;
        WareWolfPlayer target = null;
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            if(wp.getVotesCount() >= maxVoted){
                target = wp;
            }
        }
        return target;
    }

    public void changeTurn(){
        if(wareWolfGame.getGameState().equals(GameState.RUNNING)){
            switch (wareWolfGame.getTimeZone()){
                case DAY:
                    wareWolfGame.setTimeZone(TimeZone.VOTE);
                    break;

                case VOTE:
                    wareWolfGame.setTimeZone(TimeZone.NIGHT);
                    break;

                case NIGHT:
                    wareWolfGame.setTimeZone(TimeZone.DAY);
                    break;
            }
        }
    }

    public void checkWinner(){
        int whitePlayer = 0;
        int blackPlayer = 0;
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            if(wp.getRole().getColor().equals("WHITE")){
                whitePlayer ++;
            } else if(wp.getRole().getColor().equals("BLACK")){
                blackPlayer ++;
            }
        }
        if(whitePlayer <= blackPlayer){
            wareWolfGame.setGameState(GameState.FINISHED);
        }
    }

    public void timeZoneEndTask(){
        TimeZone timeZone = wareWolfGame.getTimeZone();
        if(timeZone.equals(TimeZone.VOTE)){
            getMostVoted().getPlayer().setHealth(0);
            wareWolfGame.getKillManager().setCurrentWolfs();
        }
        else if(timeZone.equals(TimeZone.NIGHT)){
            wareWolfGame.getKillManager().killPlayer();
            setAlivePlayers();
            wareWolfGame.getAlivePlayers().forEach(WareWolfPlayer::reset);
            wareWolfGame.getKillManager().reset();
        }
        checkWinner();
        changeTurn();
    }

    public int countHasVote(){
        int hasVote = 0;
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            if(wp.isHasVote()) hasVote ++;
        }
        return hasVote;
    }

    public void broadcast(String message){
        Component tag = Component.text("[ww]").color(TextColor.color(100,200,100));
        Bukkit.broadcast(tag.append(Component.text(message)));
    }
}
