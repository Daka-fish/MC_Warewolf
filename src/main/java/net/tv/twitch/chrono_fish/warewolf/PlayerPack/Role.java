package net.tv.twitch.chrono_fish.warewolf.PlayerPack;

public enum Role {
    INNOCENT("村人","白",0),
    WOLF("人狼","§c黒",1),
    KNIGHT("騎士","白",0),
    SEER("占い師","白",0),
    MEDIUM("霊媒師","白",0),
    MADMAN("狂人", "白",1),
    FOX("羊","白",3);

    final String roleName;
    final String color;
    final int side;

    Role(String roleName, String color, int side){
        this.roleName = roleName;
        this.color = color;
        this.side = side;
    }

    public String getRoleId(){return roleName;}
    public String getRoleName() {
        if(side == 0){
            return "§a"+roleName;
        }
        return "§c"+roleName;
    }
    public String getColor() {return color;}
    public int getSide() {return side;}
}