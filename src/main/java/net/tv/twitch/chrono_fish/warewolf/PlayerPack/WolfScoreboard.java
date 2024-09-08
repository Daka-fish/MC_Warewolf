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
    String knightScore;

    public WolfScoreboard(WolfPlayer wolfPlayer){
        this.wolfPlayer = wolfPlayer;
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.obj = board.registerNewObjective("sidebar", Criteria.DUMMY, Component.text("-info-").decorate(TextDecoration.BOLD));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScores();
        setOwnScore();
        wolfPlayer.getPlayer().setScoreboard(board);
    }

    public void setScores(){
        roleScore = "  └ " + wolfPlayer.getRole().getRoleName();
        obj.getScore("").setScore(0);
        obj.getScore("+あなたの役職:").setScore(-1);
        obj.getScore(roleScore).setScore(-2);
        obj.getScore(" ").setScore(-3);
        obj.getScore("§7------").setScore(-4);
        obj.getScore("   ").setScore(-9);
    }

    public void setOwnScore(){
        switch(wolfPlayer.getRole()){
            case WOLF:
                obj.getScore("+仲間").setScore(-5);
                int i = -6;
                for(WolfPlayer wolf : wolfPlayer.getWolfGame().getWolfManager().getWolfs()){
                    if(!wolf.equals(wolfPlayer)){
                        obj.getScore("  └ §c" + wolf.getPlayer().getName()).setScore(i);
                        i--;
                    }
                }
                break;

            case KNIGHT:
                knightScore = "  └ §7UNKNOWN";
                obj.getScore("+昨夜の選択").setScore(-5);
                obj.getScore(knightScore).setScore(-6);
                break;

            default:
                alivePlayers = "  └ §e" + wolfPlayer.getWolfGame().getAlivePlayers();
                obj.getScore("+生存人数").setScore(-5);
                obj.getScore(alivePlayers).setScore(-6);
                break;
        }
    }

    public void removeOwnScore(){
        for(String entry : board.getEntries()){
            if(obj.getScore(entry).getScore() != -9 && obj.getScore(entry).getScore() < -4) board.resetScores(entry);
        }
    }

    public void resetRoleScore(){
        board.resetScores(roleScore);
        roleScore = "  └ " + wolfPlayer.getRole().getRoleName();
        obj.getScore(roleScore).setScore(-2);
    }

    public void resetOwnScore(){
        switch(wolfPlayer.getRole()){
            case KNIGHT:
                WolfPlayer exTarget = wolfPlayer.getWolfGame().getKnightManager().getYesterdayTarget();
                board.resetScores(knightScore);
                knightScore = "  └ " + (exTarget!=null ? wolfPlayer.getWolfGame().getKnightManager().getYesterdayTarget().getPlayer().getName() : "§7UNKNOWN");
                obj.getScore("+昨夜の選択").setScore(-5);
                obj.getScore(knightScore).setScore(-6);
                break;

            case WOLF:
                break;

            default:
                board.resetScores(alivePlayers);
                alivePlayers = "  └ §e" + wolfPlayer.getWolfGame().getAlivePlayers();
                obj.getScore(alivePlayers).setScore(-6);
                break;
        }
    }
}
