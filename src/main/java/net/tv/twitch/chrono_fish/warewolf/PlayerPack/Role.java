package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

public enum Role {
    INNOCENT("§a村人","白"),
    WOLF("§c人狼","§c黒"),
    NIGHT("§9騎士","白");

    final String roleName;
    final String color;

    Role(String roleName, String color){
        this.roleName = roleName;
        this.color = color;
    }

    public String getRoleName() { return roleName; }
    public String getColor() { return color; }
}