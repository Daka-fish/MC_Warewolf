package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class WolfItem {

    public ItemStack getPlayerHead(Player player){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        headMeta.displayName(Component.text(player.getName()));
        head.setItemMeta(headMeta);
        return head;
    }

    public ItemStack getRoleBook(){
        Component text = Component.text("役職の数を変更できます\n");
        for(Role role : Role.values()){
            text=text.append(Component.text("\n\n ・"+role.getRoleName()+"  "));
            text=text.append(Component.text("+1").decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/ww add "+role.name()))
                    .hoverEvent(HoverEvent.showText(Component.text(role.getRoleName()+"を1人増やします"))));
            text=text.append(Component.text(" / "));
            text=text.append(Component.text("-1").decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.runCommand("/ww leave "+role.name()))
                    .hoverEvent(HoverEvent.showText(Component.text(role.getRoleName()+"を1人減らします"))));
        }
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor("You are the game master");
        bookMeta.setTitle("Role Manager");
        bookMeta.addPages(text);
        book.setItemMeta(bookMeta);
        return book;
    }

    public ItemStack getVotePaper(){
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("open vote menu"));
        paperMeta.lore(lore);
        paperMeta.displayName(Component.text("投票"));
        paper.setItemMeta(paperMeta);
        return paper;
    }

    public ItemStack getVoidItem(){
        ItemStack voidItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta voidMeta = voidItem.getItemMeta();
        voidMeta.displayName(Component.text("NONE"));
        voidItem.setItemMeta(voidMeta);
        return voidItem;
    }

    public ItemStack getKillItem(){
        ItemStack killItem = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta killMeta = killItem.getItemMeta();
        killMeta.displayName(Component.text("殺害"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("open kill menu"));
        killMeta.lore(lore);
        killItem.setItemMeta(killMeta);
        return killItem;
    }

    public ItemStack getProtectItem(){
        ItemStack protectItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta protectMeta = protectItem.getItemMeta();
        protectMeta.displayName(Component.text("護衛"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("open protect menu"));
        protectMeta.lore(lore);
        protectItem.setItemMeta(protectMeta);
        return protectItem;
    }

    public ItemStack getFortuneTellerItem(){
        return new ItemStack(Material.SPYGLASS);
    }

    public ItemStack getMediumItem(){
        return new ItemStack(Material.WOODEN_SHOVEL);
    }
}
