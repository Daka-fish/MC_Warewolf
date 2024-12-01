package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WolfInv {

    private final Component voteName = Component.text("Who do you vote to?").decorate(TextDecoration.ITALIC);
    private final Component killName = Component.text("Who do you kill?").decorate(TextDecoration.ITALIC);
    private final Component protectName = Component.text("Who do you protect?").decorate(TextDecoration.ITALIC);
    private final Component seerName = Component.text("Who do you predict?").decorate(TextDecoration.ITALIC);
    private final Component mediumName = Component.text("Whose body do you see?").decorate(TextDecoration.ITALIC);
    private final WolfGame wolfGame;
    private final WolfItem wolfItem;

    public WolfInv(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        wolfItem = new WolfItem(wolfGame);
    }

    public Component getVoteName() {return voteName;}
    public Component getKillName() {return killName;}
    public Component getProtectName() {return protectName;}
    public Component getSeerName() {return seerName;}
    public Component getMediumName() {return mediumName;}

    public Inventory getVoteInventory(Player player) {
        Inventory voteMenu = Bukkit.createInventory(player, 54, voteName);
        setHeads(voteMenu);
        return voteMenu;
    }

    public Inventory getKillInventory(Player player){
        Inventory killMenu = Bukkit.createInventory(player, 54, killName);
        setHeads(killMenu);
        return killMenu;
    }

    public Inventory getProtectInventory(Player player){
        Inventory protectMenu = Bukkit.createInventory(player, 54,protectName);
        setHeads(protectMenu);
        return protectMenu;
    }

    public Inventory getSeerInventory(Player player){
        Inventory seerMenu = Bukkit.createInventory(player,54, seerName);
        setHeads(seerMenu);
        return seerMenu;
    }

    public Inventory getMediumInventory(Player player){
        Inventory mediumMenu = Bukkit.createInventory(player,54, mediumName);
        setHeads(mediumMenu);
        return mediumMenu;
    }

    public void setHeads(Inventory inventory){
        int colum = 9;
        int size =inventory.getSize();
        ItemStack voidItem = wolfItem.getVoidItem();

        for(int i=0; i<colum; i++) inventory.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) inventory.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) inventory.setItem(i, voidItem);

        int index=10;
        for(WolfPlayer wp : wolfGame.getParticipants()){
            inventory.setItem(index, wolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        }
    }
}
