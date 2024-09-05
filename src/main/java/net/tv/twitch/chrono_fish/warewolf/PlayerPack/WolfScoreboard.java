package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class WolfScoreboard {

    private final WolfPlayer wolfPlayer;
    private final Scoreboard board;
    private final Objective obj;

    String roleScore;
    String alivePlayers;

    public WolfScoreboard(WolfPlayer wolfPlayer){
        this.wolfPlayer = wolfPlayer;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-info-").decorate(TextDecoration.BOLD));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScores();
        wolfPlayer.getPlayer().setScoreboard(board);
    }

    public void setScores(){
        roleScore = "  └ " + wolfPlayer.getRole().getRoleName();
        alivePlayers = "  └ §e" + wolfPlayer.getWolfGame().getAlivePlayers();
        obj.getScore(" ").setScore(0);
        obj.getScore("+あなたの役職:").setScore(-1);
        obj.getScore(roleScore).setScore(-2);
        obj.getScore("  ").setScore(-3);
        obj.getScore("+生存人数:").setScore(-4);
        obj.getScore(alivePlayers).setScore(-5);
        obj.getScore("   ").setScore(-6);
    }

    public Scoreboard getBoard() {return board;}

    public void resetRoleScore(){
        board.resetScores(roleScore);
        roleScore = "  └ " + wolfPlayer.getRole().getRoleName();
        obj.getScore(roleScore).setScore(-2);
    }

    public void resetAlivePlayers(){
        board.resetScores(alivePlayers);
        alivePlayers = "  └ §e" + wolfPlayer.getWolfGame().getAlivePlayers();
        obj.getScore(alivePlayers).setScore(-5);
    }
}
