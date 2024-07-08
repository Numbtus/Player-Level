package com.terrasia.playerlevel;

import com.terrasia.playerlevel.customquests.cratePlusOpen;
import com.terrasia.playerlevel.customquests.crazyCratesOpen;
import com.terrasia.playerlevel.customquests.shopGuiSell;
import com.terrasia.playerlevel.customquests.terrasiaJobsXpWin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class dependenciesLoader {
    public dependenciesLoader(Main main) {

    }
    public static void load() {

        PluginManager pl = Main.getInstance().getServer().getPluginManager();
        if (isLoaded("TerrasiaJobs")) {

            Main.getInstance().getServer().getPluginManager().registerEvents(new terrasiaJobsXpWin(Main.getInstance()), Main.getInstance());
            Main.getInstance().getLogger().info(" Dependency TerrasiaJobs load");

        }
        if (isLoaded("EconomyShopGUI")) {

            Main.getInstance().getServer().getPluginManager().registerEvents(new shopGuiSell(Main.getInstance()), Main.getInstance());
            Main.getInstance().getLogger().info(" Dependency EconomyShopGUI load");

        }
        if (isLoaded("CrazyCrates")) {

            Main.getInstance().getServer().getPluginManager().registerEvents(new crazyCratesOpen(Main.getInstance()), Main.getInstance());
            Main.getInstance().getLogger().info(" Dependency CrazyCrates load");

        }
        if (isLoaded("CratesPlus")) {

            Main.getInstance().getServer().getPluginManager().registerEvents(new cratePlusOpen(Main.getInstance()), Main.getInstance());
            Main.getInstance().getLogger().info(" Dependency CratesPlus load");

        }

        if (isLoaded("Vault") ) {

            Main.getInstance().getLogger().info(" Dependency Vault load");

        }else {
            Main.getInstance().getLogger().info(" Vault dependecy is needed !");
            Main.getInstance().getPluginLoader().disablePlugin(Main.getInstance());
        }
    }
    public static boolean isLoaded(String pluginName) {
        PluginManager pl = Main.getInstance().getServer().getPluginManager();
        return pl.getPlugin(pluginName) != null;
    }

}
