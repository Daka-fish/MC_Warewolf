package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfTask;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class TimeZoneManager {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;

    public TimeZoneManager(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
    }

    public void timeZoneEnd(){
        wolfGame.getWolfPlayers().forEach(wolfPlayer -> wolfPlayer.getWolfScoreboard().resetOwnScore());
        switch(wolfGame.getTimeZone()){
            case DAY:
                if(wolfGame.isRunning()){
                    wolfGame.setTimeZone(TimeZone.VOTE);
                    wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
                        if(wolfPlayer.isAlive()) wolfPlayer.getPlayer().getInventory().addItem(wolfGame.getWolfItem().getVotePaper());
                    });
                    wolfGame.sendMessage("投票の時間になりました、紙を右クリックして投票してください");
                    new WolfTask(wareWolf,wolfGame).runTaskTimer(wareWolf,0,20);
                }
                break;

            case VOTE:
                wolfGame.sendMessage("投票の結果. . .");
                wolfGame.getWolfPlayers().forEach(wolfPlayer -> wolfPlayer.getPlayer().getInventory().remove(Material.PAPER));
                Bukkit.getScheduler().runTaskLater(wareWolf, ()->{
                    wolfGame.kickMostVotedPlayer();
                    //wolfGame.checkWinner();
                    if(wolfGame.isRunning()){
                        wolfGame.setTimeZone(TimeZone.NIGHT);
                        wolfGame.sendMessage("夜になりました、追加されたアイテムを右クリックで行動してください");
                        wolfGame.giveRoleItem();
                        new WolfTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                    }
                    },60L);
                break;

            case NIGHT:
                wolfGame.removeRoleItem();
                //wolfGame.checkWinner();
                wolfGame.resetDailyStatus();
                wolfGame.setDayCount(wolfGame.getDayCount()+1);
                wolfGame.sendMessage(wolfGame.getDayCount()+"日目の朝になりました");
                Bukkit.getScheduler().runTaskLater(wareWolf, ()->{
                    wolfGame.getWolfManager().killTarget();
                    if(wolfGame.isRunning()){
                        wolfGame.setTimeZone(TimeZone.DAY);
                        new WolfTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                    }
                },40L);
                break;
        }
    }
}
