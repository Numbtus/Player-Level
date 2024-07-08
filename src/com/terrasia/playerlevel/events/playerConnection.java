package com.terrasia.playerlevel.events;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.CompetencesConstructor;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.io.IOException;

public class playerConnection implements Listener {

    public playerConnection(Main main) {
    }

    @EventHandler
    public void PlayerJoin(PlayerLoginEvent event) {


        Player player = event.getPlayer();





            final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
            final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
            String key = "Players." + player.getName() + ".stats.";
            int xp = questFile.getInt(key + "xp");
            int level = questFile.getInt(key + "level");
            int need = xp+level;
        // IF PLAYER NEVER PLAY
            if (need == 0) {
                questFile.set(key + "level", 1);
                questFile.set(key + "xp", 0);
                questFile.set(key + "need", 100);
                questFile.set(key + "coins", 0);
                ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
                try {
                    questFile.save(qFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(String id : sec.getKeys(false)){

                    int questId = Integer.parseInt(id);
                    int questLevel = Main.getInstance().getConfig().getInt("quest." + id + ".level");
                    int difficulty = Main.getInstance().getConfig().getInt("quest." + id + ".difficulty");
                    new QuestsConstructor(player, questLevel, difficulty, questId);


                }
                sec =  Main.getInstance().getConfig().getConfigurationSection("competences");
                for (String id : sec.getKeys(false)) {

                    int competenceId = Integer.parseInt(id);
                    new CompetencesConstructor(player, competenceId);

                }





// METTRE CES QUEST DANS LA CONFIG :
                //Quest #1, harvester selling
          //      new QuestsConstructor(player,20, 1, 1);
                //Quest #2, tools upgrade
       //         new QuestsConstructor(player,5, 1, 2);
                //Quest #3, win jobs xp
        //        new QuestsConstructor(player,20, 1, 3);
                //Quest #4, hoe replant
        //        new QuestsConstructor(player,20, 1, 4);

                //Competence #1, harvester selling boost





// IF PLAYER ALREADY PLAY? CHECK IF THERE IS NEW QUEST
            }else {
                ConfigurationSection sec = Main.getInstance().getConfig().getConfigurationSection("quest");
                int size = sec.getKeys(false).size();
                int currentSize = questFile.getConfigurationSection("Players." + player.getName() + ".Quests").getKeys(false).size();

                if(currentSize < size) {


                    for(String id : sec.getKeys(false)){

                        int questId = Integer.parseInt(id);
                        if (questId > currentSize) {
                            int questLevel = Main.getInstance().getConfig().getInt("quest." + id + ".level");
                            int difficulty = Main.getInstance().getConfig().getInt("quest." + id + ".difficulty");
                            new QuestsConstructor(player, questLevel, difficulty, questId);
                            currentSize ++;
                        }
                    }


                }



                sec =  Main.getInstance().getConfig().getConfigurationSection("competences");
                size = sec.getKeys(false).size();

                currentSize = questFile.getConfigurationSection("Players." + player.getName() + ".competences").getKeys(false).size();


                if(currentSize < size) {
                    for (String id : sec.getKeys(false)) {

                        int competenceId = Integer.parseInt(id);
                        if (competenceId > currentSize) {
                            new CompetencesConstructor(player, competenceId);
                            currentSize ++;
                        }

                    }
                }
            }

        // Set quest in storage









    }

}
