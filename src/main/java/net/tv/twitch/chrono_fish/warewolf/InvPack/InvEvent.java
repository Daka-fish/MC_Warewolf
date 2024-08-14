package net.tv.twitch.chrono_fish.warewolf.InvPack;

import net.kyori.adventure.text.Component;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.WareWolfPlayer;
import net.tv.twitch.chrono_fish.warewolf.WareWolf;
import net.tv.twitch.chrono_fish.warewolf.WorldManager.TimeZone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class InvEvent implements Listener {

    private WolfGame wolfGame;

    public InvEvent(WolfGame wolfGame){
        this.wolfGame = wolfGame;
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent e){
        if(e.getClickedInventory() != null){
            if(e.getClickedInventory().getHolder() == null && e.getCurrentItem() != null){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                ItemStack clickedItem = e.getCurrentItem();

                WareWolfInv wareWolfInv = new WareWolfInv(wolfGame);

                WareWolfPlayer wp = wolfGame.getWareWolfPlayers().get(player);

                switch (clickedItem.getType()){
                    case PAPER:
                        player.openInventory(wareWolfInv.voteInv());
                        break;

                    case NETHERITE_AXE:
                        if(wp.getRole().equals(Role.WOLF)){
                            player.openInventory(wareWolfInv.killInv());
                        } else {
                            player.sendMessage("you are not WOLF");
                        }
                        break;

                    case DIAMOND_SWORD:
                        if(wp.getRole().equals(Role.NIGHT)){
                            player.openInventory(wareWolfInv.protectInv());
                        } else {
                            player.sendMessage("you are not NIGHT");
                        }
                        break;

                    case GUNPOWDER:
                        player.openInventory(wareWolfInv.actionInv());
                        break;

                    case PLAYER_HEAD:
                        HashMap<Player,WareWolfPlayer> players = wolfGame.getWareWolfPlayers();
                        SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                        Player target = (Player) skullMeta.getOwningPlayer();
                        Component title = e.getView().title();

                        if(title.equals(wareWolfInv.getVoteName())){
                            if(wolfGame.getTimeZone().equals(TimeZone.VOTE)){
                                if(!players.get(player).isHasVote()){
                                    players.get(player).vote(players.get(target));
                                } else {
                                    player.sendMessage("you have already voted");
                                }
                            } else {
                                player.sendMessage("It's not time yet");
                            }

                        } else if (title.equals(wareWolfInv.getKillName())) {
                            if(wolfGame.getTimeZone().equals(TimeZone.NIGHT)){
                                if(!players.get(player).isHasActioned()){
                                    players.get(player).kill(players.get(target));
                                } else {
                                    player.sendMessage("you have already killed");
                                }
                            } else {
                                player.sendMessage("It's not time yet");
                            }

                        } else if (title.equals(wareWolfInv.getProtectName())) {
                            if(!players.get(player).isHasActioned()){
                                players.get(player).protect(players.get(target));
                            } else {
                                player.sendMessage("you have already protected");
                            }
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
