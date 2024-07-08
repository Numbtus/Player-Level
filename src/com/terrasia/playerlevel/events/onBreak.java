package com.terrasia.playerlevel.events;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class onBreak implements Listener {

    public onBreak(Main main) {
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){

            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");


           //break type
            if (type.equals("BREAK")) {
              String materialConf =  Main.getInstance().getConfig().getString("quest." + key + ".setting.material");
              Material material = Material.valueOf(materialConf.toUpperCase());
              if (event.getBlock().getType() == material){
                  int questId = Integer.parseInt(key);
                  Player player = event.getPlayer();
                  QuestsConstructor.addProgress(player, 1, questId);
              }
            } else if (type.equals("FARM")) {
                String materialConf =  Main.getInstance().getConfig().getString("quest." + key + ".setting.crops");
                Material material = Material.valueOf(materialConf.toUpperCase());
                if (event.getBlock().getType() == material){
                    if (materialConf.equals("CARROT") || materialConf.equals("WHEAT") || materialConf.equals("POTATO")) {
                        if(event.getBlock().getState().getRawData() == 7) {
                            int questId = Integer.parseInt(key);
                            Player player = event.getPlayer();
                            QuestsConstructor.addProgress(player, 1, questId);
                        }
                    }else if (materialConf.equals("NETHER_WARTS")) {
                        if(event.getBlock().getState().getRawData() == 3) {
                            int questId = Integer.parseInt(key);
                            Player player = event.getPlayer();
                            QuestsConstructor.addProgress(player, 1, questId);
                        }
                    }else {
                        System.out.println("ERROR | You need to use a BREAK quest, not a FARM quest !");
                    }

                }
            }


        }
    }
}
