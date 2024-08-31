package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfTask;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class TimeZoneManager {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;

    private BossBar bossBar;

    public TimeZoneManager(WareWolf wareWolf, WolfGame wolfGame){
        this.wareWolf = wareWolf;
        this.wolfGame = wolfGame;
        this.bossBar = BossBar.bossBar(Component.text("§l-"+wolfGame.getTimeZone().getName()+"-"), 1.0F, BossBar.Color.valueOf(wolfGame.getTimeZone().getColor()), BossBar.Overlay.NOTCHED_20);
    }

    public BossBar getBossBar() {return bossBar;}
    public void resetBossBar() {this.bossBar = BossBar.bossBar(Component.text("§l-"+wolfGame.getTimeZone().getName()+"-"), 1.0F, BossBar.Color.valueOf(wolfGame.getTimeZone().getColor()), BossBar.Overlay.NOTCHED_20);}

    public void timeZoneEnd(){
        switch(wolfGame.getTimeZone()){
            case DAY:
                if(wolfGame.isRunning()){
                    wolfGame.setTimeZone(TimeZone.VOTE);
                    wolfGame.getPlayers().forEach(wolfPlayer -> {
                        if(wolfPlayer.isAlive()) wolfPlayer.getPlayer().getInventory().addItem(new WolfItem().getVotePaper());
                    });
                    wolfGame.sendMessage("投票の時間になりました、紙を右クリックして投票してください");
                    new WolfTask(wareWolf,wolfGame).runTaskTimer(wareWolf,0,20);
                }
                break;

            case VOTE:
                wolfGame.sendMessage("投票の結果. . .");
                wolfGame.getPlayers().forEach(wolfPlayer -> wolfPlayer.getPlayer().getInventory().remove(Material.PAPER));
                Bukkit.getScheduler().runTaskLater(wareWolf, ()->{
                    wolfGame.getVoteManager().kickMostVotedPlayer();
                    //wolfGame.checkWinner();
                    if(wolfGame.isRunning()){
                        wolfGame.setTimeZone(TimeZone.NIGHT);
                        wolfGame.sendMessage("夜になりました");
                        wolfGame.giveRoleItem();
                        new WolfTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                    }
                    },60L);
                break;

            case NIGHT:
                //wolfGame.checkWinner();
                wolfGame.resetDailyStatus();
                wolfGame.setDay(wolfGame.getDay()+1);
                wolfGame.sendMessage(wolfGame.getDay()+"日目の朝になりました");
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
