package com.terrasia.playerlevel.constructor;

import com.terrasia.playerlevel.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;

public class CompetencesConstructor {

    private static final HandlerList handlers = new HandlerList();

    public CompetencesConstructor(Player player, int comptencesId) {
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".competences." + comptencesId;
        questFile.set(key + ".level", 0);

        try {
            questFile.save(qFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getLevel(int competenceId, Player player){
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".competences." + competenceId;
        int level = questFile.getInt(key + ".level");

        return level;
    }

    public static int isTypeExist(String competenceType) {

        ConfigurationSection sec =  Main.getInstance().getConfig().getConfigurationSection("competences");
        for(String id : sec.getKeys(false)){

            int competenceId = Integer.parseInt(id);
           if (Main.getInstance().getConfig().getString("competences." + competenceId + ".type").equals(competenceType)) {
               return competenceId;
           }


        }

        return -1;
    }

    public static void addProgress(Player player, int competenceId, int progress) {
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".competences." + competenceId;
        int level = questFile.getInt(key + ".level");
        level = level + progress;
        questFile.set(key + ".level", level);

        try {
            questFile.save(qFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
