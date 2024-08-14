package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WareWolfInv {

    private final Component acnName = Component.text("Action").decorate(TextDecoration.BOLD);
    private final Component voteName = Component.text("Who do you vote to ?").decorate(TextDecoration.ITALIC);
    private final Component killName = Component.text("Who do you kill ?").decorate(TextDecoration.ITALIC);
    private final Component protectName = Component.text("Who do you protect ?").decorate(TextDecoration.ITALIC);
    private final WolfGame wolfGame;
    private final WareWolfItem wareWolfItem;

    public WareWolfInv(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        wareWolfItem = new WareWolfItem();
    }

    public Component getAcnName() { return acnName; }
    public Component getVoteName() {return voteName; }
    public Component getKillName() { return killName; }
    public Component getProtectName() {return protectName; }

    public Inventory voteInv() {
        Inventory voteMenu = Bukkit.createInventory(null, 54, voteName);
        int colum = 9;
        int size =voteMenu.getSize();
        ItemStack voidItem = wareWolfItem.getVoidItem();
        ItemStack backItem = wareWolfItem.getBackPowder();

        for(int i=0; i<colum; i++) voteMenu.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) voteMenu.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) voteMenu.setItem(i, voidItem);

        int index=10;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            voteMenu.setItem(index,wareWolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        };

        voteMenu.setItem(43,backItem);
        return voteMenu;
    }

    public Inventory actionInv() {
        Inventory acnInv = Bukkit.createInventory(null, 54, acnName);
        acnInv.setItem(11, wareWolfItem.getVotePaper());
        acnInv.setItem(13, wareWolfItem.getKillItem());
        acnInv.setItem(15, wareWolfItem.getProtectItem());
        acnInv.setItem(38, wareWolfItem.getFortuneTellerItem());
        acnInv.setItem(40, wareWolfItem.getMediumItem());
        return acnInv;
    }

    public Inventory killInv() {
        Inventory killMenu = Bukkit.createInventory(null, 54, killName);
        int colum = 9;
        int size =killMenu.getSize();
        ItemStack voidItem = wareWolfItem.getVoidItem();
        ItemStack backItem = wareWolfItem.getBackPowder();

        for(int i=0; i<colum; i++) killMenu.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) killMenu.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) killMenu.setItem(i, voidItem);

        int index=10;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            killMenu.setItem(index,wareWolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        };

        killMenu.setItem(43,backItem);
        return killMenu;
    }

    public Inventory protectInv() {
        Inventory protectMenu = Bukkit.createInventory(null, 54, protectName);
        int colum = 9;
        int size =protectMenu.getSize();
        ItemStack voidItem = wareWolfItem.getVoidItem();
        ItemStack backItem = wareWolfItem.getBackPowder();

        for(int i=0; i<colum; i++) protectMenu.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) protectMenu.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) protectMenu.setItem(i, voidItem);

        int index=10;
        for(WareWolfPlayer wp : wolfGame.getAlivePlayers()){
            protectMenu.setItem(index,wareWolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        };

        protectMenu.setItem(43,backItem);
        return protectMenu;
    }
}
