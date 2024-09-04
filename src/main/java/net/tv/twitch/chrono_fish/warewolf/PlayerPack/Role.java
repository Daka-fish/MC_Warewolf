package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

public enum Role {
    INNOCENT("村人","白"),
    WOLF("人狼","§c黒"),
    KNIGHT("騎士","白"),
    SEER("占い師","白"),
    MEDIUM("霊媒師","白"),
    MADMAN("狂人", "白");

    final String roleName;
    final String color;

    Role(String roleName, String color){
        this.roleName = roleName;
        this.color = color;
    }

    public String getRoleName() { return roleName; }
    public String getColor() { return color; }
}