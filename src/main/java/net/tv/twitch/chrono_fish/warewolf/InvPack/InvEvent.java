package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WolfPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InvEvent implements Listener {

    private WolfGame wolfGame;
    private WolfInv wolfInv;

    public InvEvent(WolfGame wolfGame, WolfInv wolfInv) {
        this.wolfGame = wolfGame;
        this.wolfInv = wolfInv;
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getHolder() == null && e.getCurrentItem() != null) {
                e.setCancelled(true);
                if(e.getView().title().equals(wolfInv.getVoteName())){
                    WolfPlayer clicker = wolfGame.findPlayer((Player) e.getWhoClicked());
                    ItemStack skull = e.getCurrentItem();
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    Player target = (Player) skullMeta.getOwningPlayer();
                    WolfPlayer wolfPlayer = wolfGame.findPlayer(target);
                    clicker.vote(wolfPlayer);
                    clicker.getPlayer().closeInventory();
                }
            }
        }
    }
}
