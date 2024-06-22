package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WareWolfInv {

    private final Component invName = Component.text("Action menu").decorate(TextDecoration.BOLD);
    Inventory inv = Bukkit.createInventory(null, 54, invName);
    private final WareWolfGame wareWolfGame;
    private final WareWolfItem wareWolfItem;

    public WareWolfInv(){
        wareWolfGame = WareWolf.getWareWolfgame();
        wareWolfItem = new WareWolfItem();
    }

    public Inventory getInv() { return inv; }
    public Component getInvName() { return invName; }

    public void voteInv(Inventory inv) {
        inv.clear();
        int colum = 9;
        int size =inv.getSize();
        ItemStack voidItem = wareWolfItem.getVoidItem();
        ItemStack backItem = wareWolfItem.getBackPowder();

        for(int i=0; i<colum; i++) inv.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) inv.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) inv.setItem(i, voidItem);

        int index=10;
        for(WareWolfPlayer wp : wareWolfGame.getAlivePlayers()){
            inv.setItem(index,wareWolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        };

        inv.setItem(43,backItem);
    }

    public Inventory getActionInv(Inventory inv) {
        inv.clear();
        inv.setItem(11, wareWolfItem.getVotePaper());
        return inv;
    }
}
