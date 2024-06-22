package net.tv.twitch.chrono_fish.warewolf.WorldManager;

public enum TimeZone {
    DAY("昼", 180),
    VOTE("投票", 60),
    NIGHT("夜", 120);

    private final String timeName;
    private final int time;

    TimeZone(String timeName, int time){
        this.timeName = timeName;
        this.time = time;
    }

    public String getTimeName() { return timeName; }
    public int getTime() { return time; }
}
