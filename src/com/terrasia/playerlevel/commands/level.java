package com.terrasia.playerlevel.commands;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.InvItemConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class level implements CommandExecutor {
    public level(Main main) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.name")));
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("playerlevel.reload")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("other.noPermission")));
                } else {
                    Main.getInstance().reloadConfig();
                    Main.getInstance().saveDefaultConfig();
                    try {
                        Main.getInstance().reloadLang();
                    } catch (UnsupportedEncodingException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError with lang file!"));
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lPlugin reloaded!"));
                }
            }
        }else if(sender instanceof Player) {
            Player player = (Player) sender;


            String itName = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.competences"));
            List itLore = Arrays.asList(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.competences_desc")));
            ItemStack clock = InvItemConstructor.getItem(Material.WATCH, itName, itLore);
            inv.setItem(10, clock);

            String yourLevel = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.yourLevel"));
            String competencePoints=  ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.competencePoints"));
            String yourProgress =  ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.yourProgress"));
            final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
            final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
            String key = "Players." + player.getName() + ".stats.";

            int level = questFile.getInt(key + "level");
            double xp = questFile.getDouble(key + "xp");
            xp = (double) Math.round(xp * 100)/100;
            int needXp = questFile.getInt(key + "need");
            int points = questFile.getInt(key + "coins");

            List lore = Arrays.asList(yourLevel + level, yourProgress + "§e" + xp +"§7/§6" + needXp, competencePoints + points);
            String skullPlayerName = player.getName();
            ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skullMeta = (SkullMeta) playerSkull.getItemMeta();
            skullMeta.setOwner(skullPlayerName);
            skullMeta.setLore(lore);
            playerSkull.setItemMeta(skullMeta);
            inv.setItem(13, playerSkull);


            itName = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.quest"));
            itLore = Arrays.asList(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.quest_desc")));
            ItemStack book = InvItemConstructor.getItem(Material.BOOK, itName,itLore);
            inv.setItem(16, book);


            String matConf = Main.getInstance().getConfig().getString("inventoryFillingItem");
            Material material = Material.valueOf(matConf.toUpperCase());


            ItemStack glass = new ItemStack(material, 1, (short) 7);
            ItemMeta glassM = glass.getItemMeta();
            glassM.setDisplayName(" ");
            glass.setItemMeta(glassM);
            int cc = 0;
            while (cc < 27) {

                if(inv.getItem(cc) == null){
                    inv.setItem(cc, glass);
                }

                cc = cc + 1;
            }

            player.openInventory(inv);

        }


        return false;
    }
}
