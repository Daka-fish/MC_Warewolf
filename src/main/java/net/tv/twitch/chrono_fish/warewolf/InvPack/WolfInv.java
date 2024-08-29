package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WolfInv {

    private final Component acnName = Component.text("Action").decorate(TextDecoration.BOLD);
    private final Component voteName = Component.text("Who do you vote to ?").decorate(TextDecoration.ITALIC);
    private final Component killName = Component.text("Who do you kill ?").decorate(TextDecoration.ITALIC);
    private final Component protectName = Component.text("Who do you protect ?").decorate(TextDecoration.ITALIC);
    private final WolfGame wolfGame;
    private final WolfItem wolfItem;

    public WolfInv(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        wolfItem = new WolfItem();
    }

    public Component getAcnName() { return acnName; }
    public Component getVoteName() {return voteName; }
    public Component getKillName() { return killName; }
    public Component getProtectName() {return protectName; }

    public Inventory voteInv() {
        Inventory voteMenu = Bukkit.createInventory(null, 54, voteName);
        int colum = 9;
        int size =voteMenu.getSize();
        ItemStack voidItem = wolfItem.getVoidItem();

        for(int i=0; i<colum; i++) voteMenu.setItem(i,voidItem);
        for(int i=size-colum; i<size; i++) voteMenu.setItem(i,voidItem);
        for(int i = 0; i < size; i++) if (i % 9 == 0 || (i + 1) % 9 == 0) voteMenu.setItem(i, voidItem);

        int index=10;
        for(WolfPlayer wp : wolfGame.getPlayers()){
            voteMenu.setItem(index, wolfItem.getPlayerHead(wp.getPlayer()));
            index++;
        };

        return voteMenu;
    }
}
