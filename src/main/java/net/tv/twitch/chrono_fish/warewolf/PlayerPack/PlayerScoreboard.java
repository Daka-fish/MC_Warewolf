package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.kyori.adventure.text.Component;
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
        obj = board.registerNewObjective("dummy",Criteria.DUMMY, Component.text("Ware wolf"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("").setScore(0);
    }

    public Scoreboard getBoard() {
        return board;
    }
    public Objective getObj() {
        return obj;
    }
    public Player getPlayer() {
        return player;
    }
}
