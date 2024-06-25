package net.tv.twitch.chrono_fish.warewolf.WolfPack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;

import java.util.ArrayList;

public class WolfChat {

    private final ArrayList<WareWolfPlayer> wolfs;

    public WolfChat(ArrayList<WareWolfPlayer> wolfs){
        this.wolfs = wolfs;
    }

    public void sendMessageWolf(Component message){ wolfs.forEach(wolf -> wolf.getPlayer().sendMessage(message)); }
}
