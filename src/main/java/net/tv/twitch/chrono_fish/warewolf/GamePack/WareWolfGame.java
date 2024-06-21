package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class WareWolfGame {

    private GameState gameState;
    private TimeZone timeZone;
    private final HashMap<Player, PlayerScoreboard> scoreboardHashMap;
    private final HashMap<String, WareWolfPlayer> wareWolfPlayers;
    private final ArrayList<WareWolfPlayer> alivePlayers;
    private final ArrayList<WareWolfPlayer> deadPlayers;

    public WareWolfGame(){
        gameState = GameState.FINISHED;
        timeZone = TimeZone.DAY;
        scoreboardHashMap = new HashMap<>();
        wareWolfPlayers = new HashMap<>();
        alivePlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
    }

    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public HashMap<Player, PlayerScoreboard> getScoreboardHashMap() {
        return scoreboardHashMap;
    }
    public HashMap<String, WareWolfPlayer> getWareWolfPlayers() {
        return wareWolfPlayers;
    }
    public ArrayList<WareWolfPlayer> getAlivePlayers() {
        return alivePlayers;
    }
    public ArrayList<WareWolfPlayer> getDeadPlayers() {
        return deadPlayers;
    }
}
