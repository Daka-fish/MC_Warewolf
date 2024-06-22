package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

import org.bukkit.ChatColor;

public enum Role {
    VILLAGER(ChatColor.GREEN+"村人","WHITE"),
    WOLF(ChatColor.RED+"人狼","BLACK");

    final String roleName;
    final String color;

    Role(String roleName, String color){
        this.roleName = roleName;
        this.color = color;
    }

    public String getRoleName() { return roleName; }
    public String getColor() { return color; }
}