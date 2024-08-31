package net.tv.twitch.chrono_fish.warewolf.GamePack;

import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class WolfEvent implements Listener {

    private final WolfGame wolfGame;
    private final WolfInv wolfInv;
    private final WolfItem wolfItem;

    public WolfEvent(WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.wolfInv = new WolfInv(wolfGame);
        this.wolfItem = new WolfItem();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        wolfGame.getPlayers().add(new WolfPlayer(wolfGame, joinedPlayer));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player quitter = e.getPlayer();
        wolfGame.getPlayers().remove(wolfGame.getWolfPlayer(quitter));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(wolfGame.isRunning() && e.getItem() != null){
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Player player = e.getPlayer();
                switch(e.getItem().getType()){
                    case PAPER:
                        player.openInventory(wolfInv.getVoteInventory(player));
                        break;

                    case NETHERITE_AXE:
                        player.openInventory(wolfInv.getKillInventory(player));
                        break;

                    case IRON_SWORD:
                        player.openInventory(wolfInv.getProtectInventory(player));
                        break;

                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(wolfGame.isRunning()){
            if(e.getClickedInventory() != null && e.getCurrentItem() != null){
                WolfPlayer wolfPlayer = wolfGame.getWolfPlayer((Player) e.getWhoClicked());
                InventoryView clickedView = e.getView();
                ItemStack currentItem = e.getCurrentItem();
                if(currentItem.equals(wolfItem.getVoidItem())){
                    e.setCancelled(true);
                    return;
                }
                if(clickedView.title().equals(wolfInv.getVoteName())){
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                    WolfPlayer target = wolfGame.getWolfPlayer((Player) skullMeta.getOwningPlayer());
                    wolfPlayer.vote(target);
                    wolfPlayer.getPlayer().closeInventory();
                    return;
                }
                if(clickedView.title().equals(wolfInv.getKillName())){
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                    WolfPlayer target = wolfGame.getWolfPlayer((Player) skullMeta.getOwningPlayer());
                    wolfPlayer.kill(target);
                    wolfPlayer.getPlayer().closeInventory();
                    return;
                }
                if(clickedView.title().equals(wolfInv.getProtectName())){
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                    WolfPlayer target = wolfGame.getWolfPlayer((Player) skullMeta.getOwningPlayer());
                    wolfPlayer.protect(target);
                    wolfPlayer.getPlayer().closeInventory();
                }
            }
        }
    }
}
