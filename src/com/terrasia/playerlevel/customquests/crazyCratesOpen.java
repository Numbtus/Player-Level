package com.terrasia.playerlevel.customquests;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import me.badbones69.crazycrates.api.events.PlayerPrizeEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class crazyCratesOpen implements Listener {
    public crazyCratesOpen(Main main) {

    }

    @EventHandler
    public void onCrateOpen(PlayerPrizeEvent event) {
        Player player = event.getPlayer();
        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){
            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");
            if (type.equals("crazyCratesOpen")) {
                String crateName = Main.getInstance().getConfig().getString("quest." + key + ".setting.crate");
                if (crateName.equals(event.getCrateName())) {
                    int questId = Integer.parseInt(key);
                    QuestsConstructor.addProgress(player, 1, questId);
                }

            }
        }
    }
}
