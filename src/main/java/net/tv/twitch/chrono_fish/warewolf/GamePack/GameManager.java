package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZoneTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class GameManager {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;

    public GameManager(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
    }

    public void assignRole(){
        int index = 0;
        if(wolfGame.getAlivePlayers().size() == wolfGame.getRoles().size()){
            for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
                Player player = wp.getPlayer();
                Role role = wolfGame.getRoles().get(index);
                wolfGame.getScoreboardHashMap().get(player).updateRole(role);
                wp.setRole(role);
                WareWolf.putLog(player.getName()+" : "+ wp.getRole());
                index++;
            }
        } else {
            broadcast("役職の合計人数と参加者の人数を確認してください");
        }
    }

    public void setAlivePlayers(){
        for(Map.Entry<Player, WareWolfPlayer> entry: wolfGame.getWareWolfPlayers().entrySet()){
            WareWolfPlayer wareWolfPlayer = entry.getValue();
            if(wareWolfPlayer.isAline()){
                wolfGame.getAlivePlayers().add(wareWolfPlayer);
            }
        }
    }

    public void setRoles(){
        ArrayList<Role> roles = wolfGame.getRoles();
        roles.add(Role.INNOCENT);
        roles.add(Role.WOLF);
        Collections.shuffle(roles);
    }

    public WareWolfPlayer getMostVoted(){
        int maxVoted = 0;
        WareWolfPlayer target = null;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            if(wp.getVotesCount() >= maxVoted){
                target = wp;
            }
        }
        return target;
    }

    public void changeTurn(){
        if(wolfGame.getGameState().equals(GameState.RUNNING)){
            switch (wolfGame.getTimeZone()){
                case DAY:
                    wolfGame.setTimeZone(TimeZone.VOTE);
                    break;

                case VOTE:
                    wolfGame.setTimeZone(TimeZone.NIGHT);
                    break;

                case NIGHT:
                    wolfGame.setTimeZone(TimeZone.DAY);
                    break;
            }
            wolfGame.getBossBarManager().reloadBar();
        }
    }

    public void checkWinner(){
        int whitePlayer = 0;
        int blackPlayer = 0;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            if(wp.getRole().getColor().equals("WHITE")){
                whitePlayer ++;
            } else if(wp.getRole().getColor().equals("BLACK")){
                blackPlayer ++;
            }
        }
        if(whitePlayer <= blackPlayer){
            wolfGame.setGameState(GameState.FINISHED);
        }
    }

    public void timeZoneEndTask(){
        TimeZone timeZone = wolfGame.getTimeZone();
        if(timeZone.equals(TimeZone.VOTE)){
            getMostVoted().getPlayer().setHealth(0);
            wolfGame.getKillManager().setCurrentWolfs();
        }
        else if(timeZone.equals(TimeZone.NIGHT)){
            wolfGame.getKillManager().killPlayer();
            setAlivePlayers();
            wolfGame.getAlivePlayers().forEach(WareWolfPlayer::reset);
            wolfGame.getKillManager().reset();
        }
        checkWinner();
        if(wolfGame.getGameState().equals(GameState.RUNNING)){
            changeTurn();
            new TimeZoneTask(wareWolf, wolfGame).runTaskTimer(wareWolf,10,20);
        }
    }

    public int countHasVote(){
        int hasVote = 0;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            if(wp.isHasVote()) hasVote ++;
        }
        return hasVote;
    }

    public void broadcast(String message){
        Component tag = Component.text("[ww]").color(TextColor.color(100,200,100));
        Bukkit.broadcast(tag.append(Component.text(message)));
    }
}
