package com.terrasia.playerlevel.inventory;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.InvItemConstructor;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class QuestInventory {
    public QuestInventory(Main main) {

    }

    public static Inventory getInventory(Player player) {



        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getLang().getString("questInv.name")));
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".Quests";





        ConfigurationSection sec = questFile.getConfigurationSection(key +".");

        for(String id : sec.getKeys(false)){

            int questId = Integer.parseInt(id);
            //palier
            int questLevel = QuestsConstructor.getPalier(questId, player);
            int needPalier = questFile.getInt(key + "." + id  + ".palier");
            int progress = questFile.getInt(key + "." + id + ".progress");
            int needProgress = questFile.getInt(key + "." + id + ".needProgress");
            int difficulty = Main.getInstance().getConfig().getInt(key + "." + id + ".difficulty");

            String name = Main.getInstance().getConfig().getString("quest." + id + ".name");
            List lore = Arrays.asList("§aPalier: §e" + questLevel + "§7/§6" + needPalier, "§aDifficulté: §e" + difficulty, "§aProgression: §e" + progress + "§7/§6" + needProgress);
            int invPostion = Main.getInstance().getConfig().getInt(  "quest." + id + ".inventory.position");
            String materialConfig = Main.getInstance().getConfig().getString(  "quest." + id + ".inventory.material");
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




        return inv;
    }
}
