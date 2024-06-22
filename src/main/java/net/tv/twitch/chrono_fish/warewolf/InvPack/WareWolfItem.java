package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class WareWolfItem {

    public ItemStack getPlayerHead(Player player){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        headMeta.displayName(Component.text(player.getName()));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("vote to "+player.getName()).color(TextColor.color(140,140,140)));
        headMeta.lore(lore);
        head.setItemMeta(headMeta);
        return head;
    }

    public ItemStack getVotePaper(){
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("click here to vote to someone!"));
        lore.add(Component.text("(open vote menu)").color(TextColor.color(140,140,140)));
        paperMeta.lore(lore);
        paperMeta.displayName(Component.text("Vote"));
        paper.setItemMeta(paperMeta);
        return paper;
    }

    public ItemStack getBackPowder(){
        ItemStack gunpowder = new ItemStack(Material.GUNPOWDER);
        ItemMeta powderMeta = gunpowder.getItemMeta();
        powderMeta.displayName(Component.text("Back to action menu").color(TextColor.color(140,140,140)));
        gunpowder.setItemMeta(powderMeta);
        return gunpowder;
    }

    public ItemStack getVoidItem(){
        ItemStack voidItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta voidMeta = voidItem.getItemMeta();
        voidMeta.displayName(Component.text("NONE").color(TextColor.color(140,140,140)));
        voidItem.setItemMeta(voidMeta);
        return voidItem;
    }
}
