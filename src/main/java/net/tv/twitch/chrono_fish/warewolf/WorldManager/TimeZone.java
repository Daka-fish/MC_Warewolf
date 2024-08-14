package net.tv.twitch.chrono_fish.warewolf.WorldManager;

public enum TimeZone {
    DAY("§l-昼-", 10, "YELLOW"),
    VOTE("§l-投票-", 10, "RED"),
    NIGHT("§l-夜-", 10, "PINK");

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
