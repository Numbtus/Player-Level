package com.terrasia.playerlevel.constructor;

import com.terrasia.playerlevel.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InvItemConstructor {



    public static ItemStack getItem(Material material, String name, List lore) {



        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }



}
