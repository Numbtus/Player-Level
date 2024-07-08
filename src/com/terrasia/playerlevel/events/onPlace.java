package com.terrasia.playerlevel.events;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class onPlace implements Listener {
    public onPlace(Main main){
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){

            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");

            if (type.equals("PLACE")) {

                String materialConf =  Main.getInstance().getConfig().getString("quest." + key + ".setting.material");
                Material material = Material.valueOf(materialConf.toUpperCase());
                if (event.getBlockPlaced().getType() == material){
                    int questId = Integer.parseInt(key);
                    QuestsConstructor.addProgress(player, 1, questId);

                }
            }
        }


    }

}
