package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfTask;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfInv;
import net.tv.twitch.chrono_fish.warewolf.InvPack.WolfItem;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final WareWolf wareWolf;
    private final WolfGame wolfGame;

    public Commands(WareWolf wareWolf, WolfGame wolfGame){
        this.wolfGame = wolfGame;
        this.wareWolf = wareWolf;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player snd = (Player) sender;
            if(command.getName().equalsIgnoreCase("ww")){
                switch (args[0]){
                    case "start":
                        if(!wolfGame.isRunning()){
                            wolfGame.setRunning(true);
                            wolfGame.setTimeZone(TimeZone.NIGHT);
                            wolfGame.getRoleManager().assignRole();
                            wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
                                wolfPlayer.getPlayer().sendMessage("あなたの役職は【"+wolfPlayer.getRole().getRoleName()+"】です");
                                wolfPlayer.getWolfScoreboard().resetRoleScore();
                                wolfPlayer.getWolfScoreboard().resetAlivePlayers();
                            });
                            new WolfTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                        } else {
                            snd.sendMessage("§c別のゲームが進行中です");
                        }
                        break;

                    case "action":
                        WolfInv wolfInv = new WolfInv(wolfGame);
                        //snd.openInventory(wolfInv.voteInv());
                        break;

                    case "stop":
                        wolfGame.checkWinner();
                        break;

                    case "book":
                        snd.getInventory().addItem(new WolfItem().getRoleBook());
                        break;

                    case "add":
                        if(args.length>1){
                            wolfGame.getRoleManager().addRole(Role.valueOf(args[1]));
                        }
                        wolfGame.sendMessage("役職一覧");
                        for(Role role : wolfGame.getRoleManager().getRoles()){
                            snd.sendMessage(role.getRoleName());
                        }
                        break;

                    case "leave":
                        if(args.length>1){
                            wolfGame.getRoleManager().removeRole(Role.valueOf(args[1]));
                        }
                        wolfGame.sendMessage("役職一覧");
                        for(Role role : wolfGame.getRoleManager().getRoles()){
                            snd.sendMessage(role.getRoleName());
                        }
                        break;

                    default:
                        sender.sendMessage("§c不明なコマンド");
                }
            }
        }
        return false;
    }
}
