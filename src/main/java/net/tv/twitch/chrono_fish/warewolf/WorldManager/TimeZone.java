package net.tv.twitch.chrono_fish.warewolf.WorldManager;

import org.bukkit.ChatColor;

public enum TimeZone {
    DAY(ChatColor.BOLD+"昼", 10, "YELLOW"),
    VOTE(ChatColor.BOLD+"投票", 10, "RED"),
    NIGHT(ChatColor.BOLD+"夜", 10, "PINK");

    private final String timeName;
    private final int time;
    private final String color;

    TimeZone(String timeName, int time, String color){
        this.timeName = timeName;
        this.time = time;
        this.color = color;
    }

    public String getTimeName() { return timeName; }
    public int getTime() { return time; }
    public String getColor() { return color; }
}
