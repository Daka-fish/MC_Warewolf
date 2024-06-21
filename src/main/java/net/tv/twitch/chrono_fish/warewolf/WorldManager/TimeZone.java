package net.tv.twitch.chrono_fish.warewolf.WorldManager;

public enum TimeZone {
    DAY("昼"),
    VOTE("投票"),
    NIGHT("夜");

    private final String timeName;

    TimeZone(String timeName){
        this.timeName = timeName;
    }

    public String getTimeName() {
        return timeName;
    }
}
