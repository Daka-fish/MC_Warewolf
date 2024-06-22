package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScoreboard {

    private final Scoreboard board;
    private final Objective obj;
    private final Player player;

    public PlayerScoreboard(Player player){
        this.player = player;
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar",Criteria.DUMMY, Component.text("Ware wolf").decorate(TextDecoration.BOLD));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("").setScore(0);
        setScores();
    }

    public Scoreboard getBoard() { return board; }
    public Objective getObj() { return obj; }
    public Player getPlayer() { return player; }

    public void setScores(){
        obj.getScore("+ あなたの役職").setScore(-1);
        obj.getScore("   └ "+ WareWolf.getWareWolfgame().getWareWolfPlayers().get(player).getRole().getRoleName().toString()).setScore(-2);
    }
}
