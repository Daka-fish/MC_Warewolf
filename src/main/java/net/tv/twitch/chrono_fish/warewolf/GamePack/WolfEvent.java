package net.tv.twitch.chrono_fish.warewolf.GamePack;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.TimeZone;
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
        this.wolfItem = wolfGame.getWolfItem();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player joinedPlayer = e.getPlayer();
        wolfGame.getParticipants().add(new WolfPlayer(wolfGame, joinedPlayer));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player quitter = e.getPlayer();
        wolfGame.getParticipants().remove(wolfGame.getWolfPlayer(quitter));
    }

    @EventHandler
    public void onChat(AsyncChatEvent e){
        if(wolfGame.isRunning()){
            if(wolfGame.getTimeZone().equals(TimeZone.NIGHT)){
                e.setCancelled(true);
                WolfPlayer wolfPlayer = wolfGame.getWolfPlayer(e.getPlayer());
                if(wolfPlayer.getRole().equals(Role.WOLF)){
                    //人狼チャット
                }else{
                    e.getPlayer().sendMessage("§c夜の間は会話できません");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(wolfGame.isRunning() && e.getItem() != null){
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Player player = e.getPlayer();
                switch(e.getItem().getType()){
                    case PAPER:
                        e.setCancelled(true);
                        player.openInventory(wolfInv.getVoteInventory(player));
                        break;

                    case NETHERITE_AXE:
                        e.setCancelled(true);
                        player.openInventory(wolfInv.getKillInventory(player));
                        break;

                    case IRON_SWORD:
                        e.setCancelled(true);
                        player.openInventory(wolfInv.getProtectInventory(player));
                        break;

                    case ENDER_EYE:
                        e.setCancelled(true);
                        player.openInventory(wolfInv.getSeerInventory(player));
                        break;

                    case WOODEN_SHOVEL:
                        e.setCancelled(true);
                        player.openInventory(wolfInv.getMediumInventory(player));
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
                    return;
                }
                if(clickedView.title().equals(wolfInv.getSeerName())){
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                    WolfPlayer target = wolfGame.getWolfPlayer((Player) skullMeta.getOwningPlayer());
                    wolfPlayer.predict(target);
                    wolfPlayer.getPlayer().closeInventory();
                    return;
                }
                if(clickedView.title().equals(wolfInv.getMediumName())){
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) currentItem.getItemMeta();
                    WolfPlayer target = wolfGame.getWolfPlayer((Player) skullMeta.getOwningPlayer());
                    wolfPlayer.medium(target);
                    wolfPlayer.getPlayer().closeInventory();
                    return;
                }
            }
        }
    }
}
