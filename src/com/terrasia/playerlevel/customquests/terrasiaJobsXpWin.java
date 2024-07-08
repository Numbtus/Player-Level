package com.terrasia.playerlevel.customquests;

import com.terrasia.jobs.customevents.xpWinEvent;
import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class terrasiaJobsXpWin implements Listener {
    public terrasiaJobsXpWin(Main main) {
    }

    @EventHandler
    public void onXpWin(xpWinEvent event) {


        Player player = event.getPlayer();
        String job = event.getJobs();
        ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
        for(String key : sec.getKeys(false)){

            String type = Main.getInstance().getConfig().getString("quest." + key + ".setting.type");

            if (type.equals("terrasiaJobsXpWin")) {

                String questJob =  Main.getInstance().getConfig().getString("quest." + key + ".setting.job");

                 if (questJob.equals("all")) {
                    int questId = Integer.parseInt(key);
                    int progress = event.getXp();
                    QuestsConstructor.addProgress(player, progress, questId);
                }else if (questJob.equals(job)){
                     int questId = Integer.parseInt(key);
                     int progress = event.getXp();
                     QuestsConstructor.addProgress(player, progress, questId);
                 }
            }
        }


    }
}
