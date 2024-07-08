package com.terrasia.playerlevel.customquests;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import me.gypopo.economyshopgui.api.events.PostTransactionEvent;
import me.gypopo.economyshopgui.util.Transaction;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class shopGuiSell implements Listener {
    public shopGuiSell(Main main) {
    }

    @EventHandler
    public void onSell(PostTransactionEvent event) {


        Player player = event.getPlayer();
        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){
            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");
            if (type.equals("shopGuiSell")) {
                int questId = Integer.parseInt(key);
                String transTypeString = Main.getInstance().getConfig().getString("quest." + questId + ".setting.transactionType");

                if (event.getTransactionType().getName().contains(transTypeString)) {
                    String progressMethod = Main.getInstance().getConfig().getString("quest." + questId + ".setting.progressMethod");
                    if (progressMethod.equals("quantity")) {
                        String item = Main.getInstance().getConfig().getString("quest." + questId + ".setting.item");
                        if (item != null) {
                            Material configMaterial = Material.valueOf(item);
                            if (event.getItemStack().getType().equals(configMaterial)) {
                                int quantity = event.getAmount();
                                QuestsConstructor.addProgress(player, quantity, questId);
                            }
                        } else {
                            int quantity = event.getAmount();
                            Main.getInstance().getLogger().info("quantity " + quantity);
                            QuestsConstructor.addProgress(player, quantity, questId);
                        }

                    } else if (progressMethod.equals("money")) {
                        String item = Main.getInstance().getConfig().getString("quest." + questId + ".setting.item");
                        if (!item .equals( "null")) {
                            Material configMaterial = Material.valueOf(item);
                            if (event.getItemStack().getType().equals(configMaterial)) {
                                int money = (int) event.getPrice();
                                QuestsConstructor.addProgress(player, money, questId);
                            }
                        } else {
                            int money = (int) event.getPrice();
                            QuestsConstructor.addProgress(player, money, questId);
                        }

                    }
                }

            }

        }



    }
}
