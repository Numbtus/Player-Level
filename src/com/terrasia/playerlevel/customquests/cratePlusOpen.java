package com.terrasia.playerlevel.customquests;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plus.crates.Events.CrateOpenEvent;

public class cratePlusOpen implements Listener {
    public cratePlusOpen(Main main) {

    }

    @EventHandler
    public void onCratesOpen(CrateOpenEvent event) {

        Main.getInstance().getLogger().info("Events opener call");
        Player player = event.getPlayer();
        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){
            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");
            if (type.equals("cratePlusOpen")) {

                Main.getInstance().getLogger().info("Type exist");
                String crateName = Main.getInstance().getConfig().getString("quest." + key + ".setting.crate");

                Main.getInstance().getLogger().info("Crate detected name: " + event.getCrate().getName(false));
                if (crateName.equals(event.getCrate().getName(false))) {

                    Main.getInstance().getLogger().info("Detected");
                    int questId = Integer.parseInt(key);
                    QuestsConstructor.addProgress(player, 1, questId);
                }

            }
        }
    }
}
