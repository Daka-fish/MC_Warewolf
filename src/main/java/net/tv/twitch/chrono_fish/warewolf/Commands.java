package net.tv.twitch.chrono_fish.warewolf;

import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfTask;
import net.tv.twitch.chrono_fish.warewolf.GamePack.WolfGame;
import net.tv.twitch.chrono_fish.warewolf.PlayerPack.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
                            wolfGame.setDay(0);
                            wolfGame.getRoleManager().assignRole();
                            wolfGame.getWolfPlayers().forEach(wolfPlayer -> {
                                wolfPlayer.getPlayer().sendMessage("あなたの役職は【"+wolfPlayer.getRole().getRoleName()+"§f】です");
                                wolfPlayer.getWolfScoreboard().resetRoleScore();
                                wolfPlayer.getWolfScoreboard().removeOwnScore();
                                wolfPlayer.getWolfScoreboard().setOwnScore();
                            });
                            new WolfTask(wareWolf, wolfGame).runTaskTimer(wareWolf,0,20);
                        } else {
                            snd.sendMessage("§c別のゲームが進行中です");
                        }
                        break;

                    case "stop":
                        wolfGame.checkWinner();
                        break;

                    case "book":
                        snd.getInventory().addItem(wolfGame.getWolfItem().getRoleBook());
                        break;

                    case "add":
                        if(args.length>1){
                            Role role = Role.valueOf(args[1]);
                            wolfGame.getRoleManager().addRole(role);
                            snd.sendMessage(role.getRoleName()+"§fを1人増やしました");
                        }
                        break;

                    case "leave":
                        if(args.length>1){
                            Role role = Role.valueOf(args[1]);
                            wolfGame.getRoleManager().removeRole(role);
                            snd.sendMessage(role.getRoleName()+"§fを1人減らしました");
                        }
                        break;

                    case "roleList":
                        StringBuilder roles = new StringBuilder("[役職一覧]\n");
                        ArrayList<Role> roleList = wolfGame.getRoleManager().getRoles();
                        for(Role role : roleList){
                            roles.append(role.getRoleName()).append("§f/");
                        }
                        snd.sendMessage(roles.toString());
                        break;

                    case "time":
                        if(args.length == 1){
                            snd.sendMessage("[時間設定]");
                            for(TimeZone timeZone : TimeZone.values()){
                                snd.sendMessage(timeZone.getName() +": §a"+timeZone.getTime()+"§f秒");
                            }
                        }else if(args.length>2){
                            try{
                                TimeZone timeZone = TimeZone.valueOf(args[1].toUpperCase());
                                timeZone.setTime(Integer.parseInt(args[2]));
                                wolfGame.getConfigManager().setConfigTime(timeZone, timeZone.getTime());
                                snd.sendMessage(timeZone.getName()+"の時間を§e"+timeZone.getTime()+"§f秒に変更しました");
                            } catch (IllegalArgumentException e) {
                                snd.sendMessage("§c/ww time {timezone} {time}");
                            }
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
