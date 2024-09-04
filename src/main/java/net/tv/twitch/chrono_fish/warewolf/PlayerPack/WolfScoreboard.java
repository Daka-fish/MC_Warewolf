package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class WolfScoreboard {

    private final WolfPlayer wolfPlayer;
    private final Scoreboard board;
    private final Objective obj;

    public WolfScoreboard(WolfPlayer wolfPlayer){
        this.wolfPlayer = wolfPlayer;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-WW-").decorate(TextDecoration.BOLD));
        setScores();
    }

    public void setScores(){wolfPlayer.getPlayer().setScoreboard(board);}

    public Scoreboard getBoard() {return board;}
}
