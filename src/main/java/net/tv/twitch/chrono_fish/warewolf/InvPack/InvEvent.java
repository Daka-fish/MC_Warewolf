package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class InvEvent implements Listener {

    @EventHandler
    public void onClickInv(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getHolder() == null && e.getCurrentItem() != null){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack clickedItem = e.getCurrentItem();

                WareWolfInv wareWolfInv = new WareWolfInv();
                WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();

                switch (clickedItem.getType()){
                    case PAPER:
                        player.openInventory(wareWolfInv.voteInv());
                        break;

                    case NETHERITE_AXE:
                        player.openInventory(wareWolfInv.killInv());
                        break;

                    case GUNPOWDER:
                        player.openInventory(wareWolfInv.actionInv());
                        break;

                    case PLAYER_HEAD:
                        HashMap<Player,WareWolfPlayer> players = wareWolfGame.getWareWolfPlayers();
                        SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                        Player target = (Player) skullMeta.getOwningPlayer();
                        Component title = e.getView().title();

                        if(title.equals(wareWolfInv.getVoteName())){
                            players.get(player).vote(players.get(target));
                        } else if (title.equals(wareWolfInv.getKillName())) {
                            players.get(player).kill(players.get(target));
                        }
                        break;

                    case BLACK_STAINED_GLASS_PANE:
                        break;

                    default:
                        player.sendMessage("unknown action");
                }
            }
        }
        }
}
