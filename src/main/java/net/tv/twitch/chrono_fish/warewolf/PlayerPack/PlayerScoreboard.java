package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScoreboard {

    private final Scoreboard board;
    private final Objective obj;
    private final Player player;
    private final WareWolfGame wareWolfGame;

    private String stateScore;
    private String timeScore;

    public PlayerScoreboard(Player player){
        this.player = player;
        wareWolfGame = WareWolf.getWareWolfgame();
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = board.registerNewObjective("sidebar",Criteria.DUMMY, Component.text("Ware wolf").decorate(TextDecoration.BOLD));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.getScore("").setScore(0);
        obj.getScore(" ").setScore(-3);
        setScores();
    }

    public Scoreboard getBoard() { return board; }
    public Objective getObj() { return obj; }
    public Player getPlayer() { return player; }

    public void setScores(){
        stateScore = "   └ "+ wareWolfGame.getWareWolfPlayers().get(player).getRole().getRoleName();
        timeScore = "   └ "+ wareWolfGame.getTimeZone().getTime()+"秒";

        obj.getScore("+ あなたの役職").setScore(-1);
        obj.getScore(stateScore).setScore(-2);
        obj.getScore(ChatColor.GRAY+"+ 墓場 - "+wareWolfGame.getDeadPlayers().size()+"人").setScore(-99);

        obj.getScore("+ 残り時間").setScore(-4);
        obj.getScore(timeScore).setScore(-5);
    }

    public void updateRole(Role newRole){
        board.resetScores(stateScore);
        stateScore = "   └ "+ newRole.getRoleName();
        obj.getScore(stateScore).setScore(-2);
    }

    public void updateTime(int newTime){
        board.resetScores("   └ "+0+"秒");
        board.resetScores(timeScore);
        timeScore = "   └ "+ newTime+"秒";
        obj.getScore(timeScore).setScore(-5);
    }
}
