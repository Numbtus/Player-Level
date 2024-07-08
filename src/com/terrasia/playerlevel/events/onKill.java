package com.terrasia.playerlevel.events;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class onKill implements Listener {
    public onKill(Main main) {

    }

    @EventHandler
    public void onKillEvent(EntityDeathEvent event) {
        EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
        if (event.getEntity().getLastDamageCause().getCause() == damageCause) {

            Player player = event.getEntity().getKiller();
            if (player != null) {
                ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
                for (String key : sec.getKeys(false)) {
                    String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");

                    if (type.equals("KILL")) {
                        String entityConf = Main.getInstance().getConfig().getString("quest." + key + ".setting.entity");
                        EntityType entity = EntityType.valueOf(entityConf.toUpperCase());
                        if (event.getEntity().getType() == entity) {
                            int questId = Integer.parseInt(key);
                            QuestsConstructor.addProgress(player, 1, questId);
                        }
                    }
                }
            }
        }
    }
}
