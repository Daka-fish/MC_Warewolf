package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WareWolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class InvEvent implements Listener {

    @EventHandler
    public void onClickInv(InventoryClickEvent e){
        if(e.getView().title().equals(new WareWolfInv().getInvName()) && e.getCurrentItem() != null){
            Player player = (Player) e.getWhoClicked();
            ItemStack clickedItem = e.getCurrentItem();
            Inventory clickedInv = e.getClickedInventory();

            WareWolfInv wareWolfInv = new WareWolfInv();
            WareWolfGame wareWolfGame = WareWolf.getWareWolfgame();

            switch (clickedItem.getType()){
                case PAPER:
                    e.setCancelled(true);
                    wareWolfInv.voteInv(clickedInv);
                    break;

                case GUNPOWDER:
                    e.setCancelled(true);
                    wareWolfInv.getActionInv(clickedInv);
                    break;

                case BLACK_STAINED_GLASS_PANE:
                    e.setCancelled(true);
                    break;

                case PLAYER_HEAD:
                    e.setCancelled(true);
                    SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();
                    HashMap<Player,WareWolfPlayer> players = wareWolfGame.getWareWolfPlayers();
                    players.get(player).vote(players.get(target));
                    break;

                default:
                    player.sendMessage("unknown action");
            }
        }
    }
}
