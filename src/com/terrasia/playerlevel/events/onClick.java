package com.terrasia.playerlevel.events;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.CompetencesConstructor;
import com.terrasia.playerlevel.constructor.InvItemConstructor;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import com.terrasia.playerlevel.inventory.QuestInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class onClick implements Listener {

    public onClick(Main main) {
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {


            if (event.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("questInv.name")))) {
                event.setCancelled(true);
            }
        if (event.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("competencesInv.name")))) {
            event.setCancelled(true);
            final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
            final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
            Player player = (Player) event.getWhoClicked();
            String key = "Players." + player.getName() + ".competences.";

            ConfigurationSection sec = questFile.getConfigurationSection(key);
            for(String id : sec.getKeys(false)) {
                int comptenceId = Integer.parseInt(id);
                int postition = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".inventory.position");
                if (event.getSlot() == postition) {
                    String prefix = Main.getInstance().getConfig().getString("prefix");
                    //Level up competences system
                    int price = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".price");
                    if(Main.getInstance().getConfig().get("rewardSystem").equals("MONEY")) {
                        Main.getInstance().setupEconomy();
                        double playerMoney = Main.getInstance().economy.getBalance(player);
                        if (playerMoney >= price) {
                            //add a level
                            int currentLevel = questFile.getInt("Players." + player.getName() + ".competences." + comptenceId + ".level");
                            int maxLevel = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".maxLevel");
                            if (currentLevel == maxLevel) {
                                player.closeInventory();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + Main.getInstance().getLang().getString("competenceMessages.maxLevelReached")));
                            }else {
                                currentLevel++;
                                questFile.set("Players." + player.getName() + ".competences." + comptenceId + ".level", currentLevel);
                                try {
                                    questFile.save(qFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (currentLevel == maxLevel) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix +Main.getInstance().getLang().getString("competenceMessages.maxLevelReached")));
                                }
                                player.closeInventory();
                                Main.getInstance().economy.withdrawPlayer(player, price);
                                String msg = Main.getInstance().getLang().getString("competenceMessages.levelUp").replace("%competencename%", Main.getInstance().getConfig().getString("competences." + comptenceId + ".name"));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + msg));
                            }

                        }else {
                            String msg = Main.getInstance().getLang().getString("competenceMessages.notEnoughMoney");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+ msg));
                        }
                    }else if (Main.getInstance().getConfig().get("rewardSystem").equals("POINTS")){
                        int playerPoints = questFile.getInt("Players." + player.getName() + ".stats.coins");
                        if (playerPoints >= price) {
                            //level Up


                            int currentLevel = questFile.getInt("Players." + player.getName() + ".competences." + comptenceId + ".level");
                            int maxLevel = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".maxLevel");
                            if (currentLevel == maxLevel) {
                                player.closeInventory();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix +Main.getInstance().getLang().getString("competenceMessages.maxLevelReached")));
                            }else {
                                currentLevel++;
                                questFile.set("Players." + player.getName() + ".competences." + comptenceId + ".level", currentLevel);

                                if (currentLevel == maxLevel) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+Main.getInstance().getLang().getString("competenceMessages.maxLevelReached")));
                                }
                                player.closeInventory();
                                playerPoints = playerPoints -price;
                                questFile.set("Players." + player.getName() + ".stats.coins", playerPoints);

                                try {
                                    questFile.save(qFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String msg = Main.getInstance().getLang().getString("competenceMessages.levelUp").replace("%competencename%", Main.getInstance().getConfig().getString("competences." + comptenceId + ".name"));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+ msg));
                            }
                        }else {
                            String msg = Main.getInstance().getLang().getString("competenceMessages.notEnoughMoney");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + msg));
                        }
                    }
                }
            }
        }
            if (event.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("inventory.name")))){
                Player player = (Player) event.getWhoClicked();
                event.setCancelled(true);
                ItemStack current = event.getCurrentItem();
                if (current == null) return;
                if (event.getSlot() == 16) {
                    Inventory inv = QuestInventory.getInventory(player);
                    player.openInventory(inv);

            }
           if(event.getSlot() == 10) {
               Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("competencesInv.name")));
               final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
               final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
               String key = "Players." + player.getName() + ".competences.";

               ConfigurationSection sec = questFile.getConfigurationSection(key +".");

               for(String id : sec.getKeys(false)){
                   int comptenceId = Integer.parseInt(id);
                   int level = CompetencesConstructor.getLevel(comptenceId, player);
                   int maxLevel = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".maxLevel");
                   String competenceDesc = Main.getInstance().getConfig().getString("competences." + comptenceId + ".desc");
                   String name = Main.getInstance().getConfig().getString("competences." + comptenceId + ".name");
                   int price = Main.getInstance().getConfig().getInt("competences." + comptenceId + ".price");

                    String type = "error";
                    if (Main.getInstance().getConfig().get("rewardSystem").equals("MONEY")) {
                        type = "&a$";
                    }else if (Main.getInstance().getConfig().get("rewardSystem").equals("POINTS")){
                        type = " &6competences points";
                    }
                   String priced = ChatColor.translateAlternateColorCodes('&',"&aPrice: &e" + price + type);
                   List lore = Arrays.asList("§aLevel: §e" + level + "§7/§6" + maxLevel, ChatColor.translateAlternateColorCodes('&',competenceDesc), priced);


                   int invPostion = Main.getInstance().getConfig().getInt(  "competences." + id + ".inventory.position");
                   String materialConfig = Main.getInstance().getConfig().getString(  "competences." + id + ".inventory.material");
                   Material material = Material.valueOf(materialConfig.toUpperCase());
                   ItemStack it = InvItemConstructor.getItem(material, name, lore);
                   inv.setItem(invPostion, it);

               }













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
        }

    }

}
