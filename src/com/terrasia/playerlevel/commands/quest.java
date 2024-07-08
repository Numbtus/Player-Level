package com.terrasia.playerlevel.commands;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import com.terrasia.playerlevel.inventory.QuestInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class quest implements CommandExecutor {
    public quest(Main main) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {


        if (args.length >= 1) {

            if(args[0].equalsIgnoreCase("progress")) {
                if(sender.hasPermission("playerlevel.admin")) {
                    String pr = Main.getInstance().getConfig().getString("prefix");
                    String message = pr + "&c&lSyntaxe error: &c/quest progres add <player> <questId> [progress]";
                    String addSyntaxMsg = ChatColor.translateAlternateColorCodes('&', message);

                    if (args.length >= 2) {
                        if (args[1] == null) {
                            sender.sendMessage("Error 1: Syntaxe");
                        } else if (args[1].equalsIgnoreCase("add")) {
                            // add progress
                            if (args.length >= 3) {
                                Player targetPlayer = Bukkit.getPlayerExact(args[2]);
                                if (targetPlayer == null) {
                                    sender.sendMessage(addSyntaxMsg);
                                } else {
                                    if (args.length >= 5) {
                                        int progress = 0;
                                        int questId = -1;
                                        try {
                                            questId = Integer.parseInt(args[3]);
                                            progress = Integer.parseInt(args[4]);


                                            final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
                                            final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
                                            String key = "Players." + targetPlayer.getName() + ".Quests";
                                            ConfigurationSection sec = questFile.getConfigurationSection(key +".");
                                            int idMax = -1;
                                            for(String id : sec.getKeys(false)){
                                                int currentId = Integer.parseInt(id);
                                                if (currentId > idMax) {
                                                    idMax++;
                                                }
                                            }

                                            idMax++;
                                            if (questId <= idMax) {
                                                QuestsConstructor.addProgress(targetPlayer, progress, questId);

                                                String lang = Main.getInstance().getLang().getString("other.addProgressSender");
                                                lang = lang.replace("%player%", targetPlayer.getName());
                                                lang = lang.replace("%questId%", "" + questId);
                                                lang = lang.replace("%progress%", "" + progress);
                                                String returnMessage = Main.getInstance().getConfig().getString("prefix") + lang;
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', returnMessage));
                                                lang = Main.getInstance().getLang().getString("other.addProgressReceive");
                                                lang = lang.replace("%questId%", "" + questId);
                                                lang = lang.replace("%progress%", "" + progress);
                                                returnMessage = Main.getInstance().getConfig().getString("prefix") + lang;
                                                targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', returnMessage));

                                            }else {
                                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pr + "Error, quest id isn't exist"));
                                            }
                                        } catch (NumberFormatException e) {
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', pr + "Error, progress isn't a correct int"));
                                        }
                                    } else {
                                        sender.sendMessage(addSyntaxMsg);
                                    }
                                }


                            } else {
                                sender.sendMessage(addSyntaxMsg);
                            }
                        } else if (args[1].equalsIgnoreCase("reset")) {
                            sender.sendMessage("RESET DETECTED");
                        } else {
                            sender.sendMessage("Error: Syntaxe");
                        }
                }else {

                        sender.sendMessage(addSyntaxMsg);
                    }
                }else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("other.noPermission")));
                }
            }else {

                    sender.sendMessage("Error of syntaxe");

            }
        }else if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory invQuest = QuestInventory.getInventory(player);
            player.openInventory(invQuest);
        }



        return false;
    }
}
