package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.BossBarManager;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class WareWolfGame {

    private GameState gameState;
    private TimeZone timeZone;
    private final HashMap<Player, PlayerScoreboard> scoreboardHashMap;
    private final HashMap<Player, WareWolfPlayer> wareWolfPlayers;
    private final ArrayList<WareWolfPlayer> alivePlayers;
    private final ArrayList<WareWolfPlayer> deadPlayers;
    private final GameManager gameManager;
    private final BossBarManager bossBarManager;

    public WareWolfGame(){
        gameState = GameState.FINISHED;
        timeZone = TimeZone.DAY;
        scoreboardHashMap = new HashMap<>();
        wareWolfPlayers = new HashMap<>();
        alivePlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        gameManager = new GameManager(this);
        bossBarManager = new BossBarManager(this);
    }

    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public TimeZone getTimeZone() { return timeZone; }
    public void setTimeZone(TimeZone timeZone) { this.timeZone = timeZone; }

    public HashMap<Player, PlayerScoreboard> getScoreboardHashMap() { return scoreboardHashMap; }
    public HashMap<Player, WareWolfPlayer> getWareWolfPlayers() { return wareWolfPlayers; }
    public ArrayList<WareWolfPlayer> getAlivePlayers() { return alivePlayers; }
    public ArrayList<WareWolfPlayer> getDeadPlayers() { return deadPlayers; }
    public GameManager getGameManager() { return gameManager;}
    public BossBarManager getBossBarManager() { return bossBarManager; }

    public void broadcast(String message){
        Component tag = Component.text("[ww]").color(TextColor.color(0,200,0));
        Bukkit.broadcast(tag.append(Component.text(message)));
    }
    public void changeTurn(){
        switch (timeZone){
            case DAY:
                timeZone = TimeZone.VOTE;
                break;

            case VOTE:
                timeZone = TimeZone.NIGHT;
                break;

            case NIGHT:
                timeZone = TimeZone.DAY;
                break;
        }
    }
}
