package net.tv.twitch.chrono_fish.warewolf.Manager;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;

public class KnightManager {

    private final WolfGame wolfGame;
    private WolfPlayer yesterdayTarget;

    public KnightManager(WolfGame wolfGame){
        this.wolfGame = wolfGame;
    }

    public void setYesterdayTarget(WolfPlayer target) {this.yesterdayTarget = target;}
    public WolfPlayer getYesterdayTarget() {return yesterdayTarget;}
}
