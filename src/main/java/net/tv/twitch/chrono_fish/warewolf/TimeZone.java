package net.tv.twitch.chrono_fish.warewolf;

public enum TimeZone {
    DAY("議論", 10, "YELLOW"),
    VOTE("投票", 10, "RED"),
    NIGHT("夜", 10, "PINK");

    private final String name;
    private int time;
    private final String color;

    TimeZone(String name, int time, String color){
        this.name = name;
        this.time = time;
        this.color = color;
    }

    public String getName() { return name; }
    public void setTime(int time) {this.time = time;}
    public int getTime() { return time; }
    public String getColor() { return color; }
}
