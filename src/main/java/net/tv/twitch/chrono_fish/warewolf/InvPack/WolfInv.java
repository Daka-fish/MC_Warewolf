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
    private final WolfGame wolfGame;
    private final WolfItem wolfItem;

    public WolfInv(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        wolfItem = new WolfItem();
    }

    public Component getVoteName() {return voteName; }
    public Component getKillName() { return killName; }
    public Component getProtectName() {return protectName; }

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

    public void setHeads(Inventory inventory){
        int colum = 9;
        int size =inventory.getSize();
        ItemStack voidItem = wolfItem.getVoidItem();

        for(int i=0; i<colum; i++) inventory.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) inventory.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) inventory.setItem(i, voidItem);

        int index=10;
        for(WolfPlayer wp : wolfGame.getPlayers()){
            inventory.setItem(index, wolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        }
    }
}
