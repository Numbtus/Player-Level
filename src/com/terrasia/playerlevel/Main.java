package com.terrasia.playerlevel;

import com.terrasia.playerlevel.commands.level;
import com.terrasia.playerlevel.commands.quest;
import com.terrasia.playerlevel.customquests.terrasiaJobsXpWin;
import com.terrasia.playerlevel.events.*;
import com.terrasia.playerlevel.Metrics;
//import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;


public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    FileConfiguration config;
    File cfile;
    private File langFile = null;
    private FileConfiguration lang = null;


  //  public Economy economy = null;



    @Override
    public void onEnable() {

        config = getConfig();
        saveDefaultConfig();
        cfile = new File(getDataFolder(), "config.yml");
        createLang();
      //  setupEconomy();




        // CONFIG AUTO UPDATE
        int configVersion = getConfig().getInt("version");
        int currentVersion = 4;
        if (configVersion != currentVersion){
            System.out.println("Config file isn't up to date");
            if(configVersion == 1){
                getConfig().set("version", currentVersion);
                getConfig().set("inventoryFillingItem", "STAINED_GLASS_PANE");
                getConfig().set("metrics", true);
                getLang().set("other.addProgressSender", "test");
                getLang().set("other.addProgressReceive", "test");


                try {
                    getLang().save(this.langFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveConfig();
                System.out.println("Config file updated");
            } else if(configVersion == 2){
                getConfig().set("version", currentVersion);
                // AJOUTER LES NOUVEAUX PARAMETRES ICI
                getConfig().set("inventoryFillingItem", "STAINED_GLASS_PANE");
                getConfig().set("metrics", true);
                getLang().set("other.addProgressSender", "test");
                getLang().set("other.addProgressReceive", "test");


                try {
                    getLang().save(this.langFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveConfig();
                System.out.println("Config file updated");
            } else if (configVersion == 3) {
                getConfig().set("version", currentVersion);
                getConfig().set("metrics", true);
                getLang().set("other.addProgressSender", "test");
                getLang().set("other.addProgressReceive", "test");


                try {
                    getLang().save(this.langFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveConfig();
                System.out.println("Config file updated");
            }
        }
        boolean metricsParams = getConfig().getBoolean("metrics");
        if(metricsParams) {
            int pluginId = 18303; // <-- Replace with the id of your plugin!
            Metrics metrics = new Metrics(this, pluginId);
        }

        getServer().getPluginManager().registerEvents(new playerConnection(this), this);
        getServer().getPluginManager().registerEvents(new onClick(this), this);
        getServer().getPluginManager().registerEvents(new onBreak(this), this);
        getServer().getPluginManager().registerEvents(new onPlace(this), this);
        getServer().getPluginManager().registerEvents(new onKill(this), this);


        getCommand("level").setExecutor(new level(this));
        getCommand("quest").setExecutor(new quest(this));
        instance = this;

        //Load dependencies
        dependenciesLoader.load();




        System.out.println("[PlayerLevel] Plugin is enable!");
        // UPDATE CHECKER
        getLogger().info("Plugin version: " + getDescription().getVersion());
        new UpdateChecker(this, 109348).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("Plugin is up to date");

            } else {
                getLogger().info("There is a new update available on spigot!");
            }
        });
    }




  //  public boolean setupEconomy() {
       // RegisteredServiceProvider<Economy> eco = getServer().getServicesManager().getRegistration(Economy.class);
     //   if (eco != null) {
        //    economy = eco.getProvider();
       // }

      //  return economy != null;
    //}

    public FileConfiguration getLang() {
        return this.lang;
    }


    private void createLang() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
        }

        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }



    public void reloadLang() throws UnsupportedEncodingException {
        if (langFile == null) {
            langFile = new File(getDataFolder(), "lang.yml");
        }
        lang = YamlConfiguration.loadConfiguration(langFile);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(this.getResource("lang.yml"), "UTF8");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            lang.setDefaults(defConfig);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[PlayerLevel] Plugin is disable!");
    }

    public static Main getInstance() {
        return instance;
    }
}
