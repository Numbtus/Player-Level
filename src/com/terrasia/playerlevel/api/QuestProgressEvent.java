package com.terrasia.playerlevel.api;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.constructor.QuestsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.File;

public class QuestProgressEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;
    int progress;
    int questid;
    int level;
    int globalevel;
    public QuestProgressEvent(Player player, int progress, int questid) {
        this.player = player;
        this.progress = progress;
        this.questid = questid;
    }


    public Player getPlayer() {
        return player;

    }

    public int getProgress() {
        return progress;
    }
    public int getQuestid() {
        return questid;
    }

    public int getLevel() {

        this.level = QuestsConstructor.getPalier(questid, player);

        return level;
    }
    public int getGlobalLevel() {
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".stats.level" ;

        this.globalevel =questFile.getInt(key);;

        return globalevel;
    }



    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
