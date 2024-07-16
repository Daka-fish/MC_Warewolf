package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.PlayerPack.PlayerScoreboard;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WolfPack.KillManager;
import net.tv.twitch.chrono_fish.warewolf.WolfPack.WolfChat;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.BossBarManager;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class WareWolfGame {

    private WareWolf wareWolf;
    private GameState gameState;
    private TimeZone timeZone;
    private final HashMap<Player, PlayerScoreboard> scoreboardHashMap;
    private final HashMap<Player, WareWolfPlayer> wareWolfPlayers;
    private final ArrayList<WareWolfPlayer> alivePlayers;
    private final ArrayList<WareWolfPlayer> deadPlayers;
    private final ArrayList<Role> roles;
    private final GameManager gameManager;
    private final KillManager killManager;
    private final BossBarManager bossBarManager;

    public WareWolfGame(WareWolf wareWolf){
        this.wareWolf = wareWolf;
        gameState = GameState.FINISHED;
        timeZone = TimeZone.DAY;
        scoreboardHashMap = new HashMap<>();
        wareWolfPlayers = new HashMap<>();
        alivePlayers = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        roles = new ArrayList<>();
        gameManager = new GameManager(wareWolf,this);
        killManager = new KillManager(this);
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

    public ArrayList<Role> getRoles() { return roles; }
    public GameManager getGameManager() { return gameManager;}
    public BossBarManager getBossBarManager() { return bossBarManager; }
    public KillManager getKillManager() { return killManager; }
}
