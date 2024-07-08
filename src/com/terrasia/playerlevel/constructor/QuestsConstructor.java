package com.terrasia.playerlevel.constructor;

import com.terrasia.playerlevel.Main;
import com.terrasia.playerlevel.api.QuestProgressEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.io.File;
import java.io.IOException;

public class QuestsConstructor {

    Player player;
    String name;
    int progress;
    int needprogress;
    int questLevel;
    int questPalier;
    int questDificulty;
    int questId;

    private static final HandlerList handlers = new HandlerList();

    public QuestsConstructor(Player player, int questPalier, int questDificulty, int questId) {
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".Quests." + questId;

      if (questPalier == 5) {
          //Easy
          if(questDificulty == 0) {
            questFile.set(key + ".palier", questPalier);
            questFile.set(key + ".difficulty", questDificulty);
            questFile.set(key + ".progress", 0);
            questFile.set(key + ".needProgress", 1);
          }
          //normal
          if(questDificulty == 1) {
              questFile.set(key + ".palier", questPalier);
              questFile.set(key + ".difficulty", questDificulty);
              questFile.set(key + ".progress", 0);
              questFile.set(key + ".needProgress", 5);
          }
          //Difficult
          if(questDificulty == 2) {
              questFile.set(key + ".palier", questPalier);
              questFile.set(key + ".difficulty", questDificulty);
              questFile.set(key + ".progress", 0);
              questFile.set(key + ".needProgress", 10);
          }
          try {
              questFile.save(qFile);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }



        if (questPalier == 10) {
            //Easy
            if(questDificulty == 0) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 5);
            }
            //normal
            if(questDificulty == 1) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 20);
            }
            //Difficult
            if(questDificulty == 2) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 100);
            }
            try {
                questFile.save(qFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        if (questPalier == 20) {
            //Easy
            if(questDificulty == 0) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 1);
            }
            //normal
            if(questDificulty == 1) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 5);
            }
            //Difficult
            if(questDificulty == 2) {
                questFile.set(key + ".palier", questPalier);
                questFile.set(key + ".difficulty", questDificulty);
                questFile.set(key + ".progress", 0);
                questFile.set(key + ".needProgress", 100);
            }
            try {
                questFile.save(qFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }



    public static void addProgress(Player player, int progress, int questId) {
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String prefix = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix"));
        String key = "Players." + player.getName() + ".Quests." + questId;
        int needProgress = questFile.getInt(key + ".needProgress");
        int oldProgress = questFile.getInt(key + ".progress");


        // COMAND EXECUTOR
        int comptenceId = CompetencesConstructor.isTypeExist("COMMAND");
        if(comptenceId != -1) {
            int level = CompetencesConstructor.getLevel(comptenceId, player);
            double percentagePerLevel = Main.getInstance().getConfig().getDouble("competences." + comptenceId + ".settings.percentage") * level;

            double rdm = (Math.random() * (100 - 0 + 1) + 0);
            if(percentagePerLevel > rdm) {
                String cmd = Main.getInstance().getConfig().getString("competences." + comptenceId + ".settings.command").replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }

        }
        Bukkit.getServer().getPluginManager().callEvent(new QuestProgressEvent(player, progress, questId));

        comptenceId = CompetencesConstructor.isTypeExist("XPBOOSTER");
        // XP BOOSTER

        int newPrgogress = oldProgress + progress;
        if (needProgress < 1) {
            questFile.set(key + ".progress", newPrgogress);
            try {
                questFile.save(qFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (newPrgogress >= needProgress) {
            // passage de palier
            int questPalier = questFile.getInt(key + ".palier");
            int questDiffuculty = questFile.getInt(key + ".difficulty");
            int actuallyPalier = getPalier(questId, player);
            int newXpNeed = 0;
            double xpWin = 0;
            double rest = newPrgogress - needProgress;
            if (rest < 1) {
                rest = 0;
            }
            if (questPalier == 5) {
                if (questDiffuculty == 0) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 5;
                        xpWin = 10;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 10;
                        xpWin = 25;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 15;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 20;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = -1;
                        xpWin = 65;
                    }

                }

                if (questDiffuculty == 1) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 10;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 50;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 100;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 250;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = -1;
                        xpWin = 80;
                    }

                }

                if (questDiffuculty == 2) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 50;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 100;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 1000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 10000;
                        xpWin = 90;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = -1;
                        xpWin = 100;
                    }

                }
            }

            if (questPalier == 10) {
                if (questDiffuculty == 0) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 10;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 20;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 50;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 100;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 150;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 200;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 400;
                        xpWin = 65;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 500;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 800;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = -1;
                        xpWin = 80;
                    }

                }

                if (questDiffuculty == 1) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 50;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 100;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 300;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 500;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 1000;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 5000;
                        xpWin = 65;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 10000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 25000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 50000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = -1;
                        xpWin = 85;
                    }

                }

                if (questDiffuculty == 2) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 500;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 1000;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 2500;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 7500;
                        xpWin = 65;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 15000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 50000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 100000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 200000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 500000;
                        xpWin = 90;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = -1;
                        xpWin = 100;
                    }

                }

            }
            if (questPalier == 20) {
                if (questDiffuculty == 0) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 5;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 10;
                        xpWin = 35;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 25;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 50;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 100;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 250;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 500;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 1000;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 1250;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = 1750;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 11) {
                        newXpNeed = 2500;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 12) {
                        newXpNeed = 4000;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 13) {
                        newXpNeed = 5000;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 14) {
                        newXpNeed = 7500;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 15) {
                        newXpNeed = 10000;
                        xpWin = 65;
                    }
                    if (actuallyPalier == 16) {
                        newXpNeed = 12500;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 17) {
                        newXpNeed = 15000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 18) {
                        newXpNeed = 17500;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 19) {
                        newXpNeed = 20000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 20) {
                        newXpNeed = -1;
                        xpWin = 80;
                    }

                }


                if (questDiffuculty == 1) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 10;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 50;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 100;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 250;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 500;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 1000;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 2500;
                        xpWin = 65;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 5000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 7500;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = 15000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 11) {
                        newXpNeed = 30000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 12) {
                        newXpNeed = 75000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 13) {
                        newXpNeed = 100000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 14) {
                        newXpNeed = 150000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 15) {
                        newXpNeed = 175000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 16) {
                        newXpNeed = 200000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 17) {
                        newXpNeed = 300000;
                        xpWin = 85;
                    }
                    if (actuallyPalier == 18) {
                        newXpNeed = 400000;
                        xpWin = 85;
                    }
                    if (actuallyPalier == 19) {
                        newXpNeed = 500000;
                        xpWin = 85;
                    }
                    if (actuallyPalier == 20) {
                        newXpNeed = -1;
                        xpWin = 90;
                    }

                }

                if (questDiffuculty == 2) {
                    if (actuallyPalier == 1) {
                        newXpNeed = 500;
                        xpWin = 10;
                    }
                    if (actuallyPalier == 2) {
                        newXpNeed = 1000;
                        xpWin = 15;
                    }
                    if (actuallyPalier == 3) {
                        newXpNeed = 2500;
                        xpWin = 15;
                    }
                    if (actuallyPalier == 4) {
                        newXpNeed = 5000;
                        xpWin = 20;
                    }
                    if (actuallyPalier == 5) {
                        newXpNeed = 10000;
                        xpWin = 20;
                    }
                    if (actuallyPalier == 6) {
                        newXpNeed = 25000;
                        xpWin = 25;
                    }
                    if (actuallyPalier == 7) {
                        newXpNeed = 50000;
                        xpWin = 30;
                    }
                    if (actuallyPalier == 8) {
                        newXpNeed = 100000;
                        xpWin = 35;
                    }
                    if (actuallyPalier == 9) {
                        newXpNeed = 250000;
                        xpWin = 40;
                    }
                    if (actuallyPalier == 10) {
                        newXpNeed = 500000;
                        xpWin = 45;
                    }
                    if (actuallyPalier == 11) {
                        newXpNeed = 750000;
                        xpWin = 50;
                    }
                    if (actuallyPalier == 12) {
                        newXpNeed = 1000000;
                        xpWin = 55;
                    }
                    if (actuallyPalier == 13) {
                        newXpNeed = 1250000;
                        xpWin = 60;
                    }
                    if (actuallyPalier == 14) {
                        newXpNeed = 1500000;
                        xpWin = 70;
                    }
                    if (actuallyPalier == 15) {
                        newXpNeed = 1750000;
                        xpWin = 75;
                    }
                    if (actuallyPalier == 16) {
                        newXpNeed = 2000000;
                        xpWin = 80;
                    }
                    if (actuallyPalier == 17) {
                        newXpNeed = 3000000;
                        xpWin = 85;
                    }
                    if (actuallyPalier == 18) {
                        newXpNeed = 4000000;
                        xpWin = 90;
                    }
                    if (actuallyPalier == 19) {
                        newXpNeed = 5000000;
                        xpWin = 95;
                    }
                    if (actuallyPalier == 20) {
                        newXpNeed = -1;
                        xpWin = 100;
                    }

                }
            }

                if(comptenceId != -1) {
                    int level = CompetencesConstructor.getLevel(comptenceId, player);
                    double percentagePerLevel = Main.getInstance().getConfig().getDouble("competences." + comptenceId + ".settings.boostpercentage") * level;
                    xpWin = xpWin + xpWin*(percentagePerLevel/100) ;
                }
            questFile.set(key + ".needProgress", newXpNeed);
            questFile.set(key + ".progress", rest);
            try {
                questFile.save(qFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int xpNeed = questFile.getInt("Players." + player.getName() + ".stats.need" );
            double xp = questFile.getDouble("Players." + player.getName() + ".stats.xp" );
            int level = questFile.getInt("Players." + player.getName() + ".stats.level" );
            double newXp = xp + xpWin;
            int coins = questFile.getInt("Players." + player.getName() + ".stats.coins" );

            if(newXp >= xpNeed) {
                double restXp = newXp - xpNeed;
                questFile.set("Players." + player.getName() + ".stats.xp", restXp);
                int newLevel = level +1;
                questFile.set("Players." + player.getName() + ".stats.level", newLevel);
                int newCoins = coins + 1;
                questFile.set("Players." + player.getName() + ".stats.coins",newCoins);
                String message = Main.getInstance().getLang().getString("other.newLevel");
                message = message.replace("%level%", "" + newLevel);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
                if(Main.getInstance().getConfig().get("rewardSystem").equals("MONEY")) {
                    //Add money
                    int money = Main.getInstance().getConfig().getInt("moneyPerLevel");
                    boolean multiplyByLevel = Main.getInstance().getConfig().getBoolean("multiplyByLevel");
                    if (multiplyByLevel) {
                        money = money * newLevel;
                    }
                    Main.getInstance().economy.depositPlayer(player, money);
                    
                }


                try {
                    questFile.save(qFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                questFile.set("Players." + player.getName() + ".stats.xp", newXp);
                try {
                    questFile.save(qFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (newXpNeed == -1) {
                String message = Main.getInstance().getLang().getString("quest.maxPalier");
                player.sendMessage(prefix + message);
            }
            int newPalier = actuallyPalier + 1;
            String message = Main.getInstance().getLang().getString("quest.newPalier");
            message = message.replace("%newPalier%", "" + newPalier);
            message = message.replace("%questId%", "" + questId);
            message = message.replace("%questName%", getName(questId));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));


        } else {

            questFile.set(key + ".progress", newPrgogress);
            try {
                questFile.save(qFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    }
    public static String getName(int questId) {
        String name = "null";

        name = Main.getInstance().getConfig().getString("quest." + questId + ".name");

        return name;
    }

    public int getProgress(Player player, int questId) {

        int progress = 0 ;
        return progress;
    }
    public static int getPalier(int questId, Player player) {
        int palier = -1;
        final File qFile = new File(Main.getInstance().getDataFolder(), "storage.yml");
        final YamlConfiguration questFile = YamlConfiguration.loadConfiguration(qFile);
        String key = "Players." + player.getName() + ".Quests." + questId;
        int questPalier = questFile.getInt(key + ".palier");
        int questDiffuculty = questFile.getInt(key + ".difficulty");
        int needProgress = questFile.getInt(key + ".needProgress");



        if (questPalier == 5) {
            if (questDiffuculty == 0){
                if (needProgress == 1) {
                    palier = 1;
                }
                if (needProgress == 5) {
                    palier = 2;
                }
                if (needProgress == 10) {
                    palier = 3;
                }
                if (needProgress == 15) {
                    palier = 4;
                }
                if (needProgress == 20) {
                    palier = 5;
                }
            }



            if (questDiffuculty == 1){
                if (needProgress == 5) {
                    palier = 1;
                }
                if (needProgress == 10) {
                    palier = 2;
                }
                if (needProgress == 50) {
                    palier = 3;
                }
                if (needProgress == 100) {
                    palier = 4;
                }
                if (needProgress == 1000) {
                    palier = 5;
                }
            }



            if (questDiffuculty == 2){
                if (needProgress == 10) {
                    palier = 1;
                }
                if (needProgress == 50) {
                    palier = 2;
                }
                if (needProgress == 100) {
                    palier = 3;
                }
                if (needProgress == 1000) {
                    palier = 4;
                }
                if (needProgress == 10000) {
                    palier = 5;
                }
            }

        }


        if (questPalier == 10) {
            if (questDiffuculty == 0){
                if (needProgress == 5) {
                    palier = 1;
                }
                if (needProgress == 10) {
                    palier = 2;
                }
                if (needProgress == 20) {
                    palier = 3;
                }
                if (needProgress == 50) {
                    palier = 4;
                }
                if (needProgress == 100) {
                    palier = 5;
                }
                if (needProgress == 150) {
                    palier = 6;
                }
                if (needProgress == 200) {
                    palier = 7;
                }
                if (needProgress == 400) {
                    palier = 8;
                }
                if (needProgress == 500) {
                    palier = 9;
                }
                if (needProgress == 1000) {
                    palier = 10;
                }
            }



            if (questDiffuculty == 1){
                if (needProgress == 20) {
                    palier = 1;
                }
                if (needProgress == 50) {
                    palier = 2;
                }
                if (needProgress == 100) {
                    palier = 3;
                }
                if (needProgress == 300) {
                    palier = 4;
                }
                if (needProgress == 500) {
                    palier = 5;
                }
                if (needProgress == 1000) {
                    palier = 6;
                }
                if (needProgress == 5000) {
                    palier = 7;
                }
                if (needProgress == 10000) {
                    palier = 8;
                }
                if (needProgress == 25000) {
                    palier = 9;
                }
                if (needProgress == 50000) {
                    palier = 10;
                }
            }



            if (questDiffuculty == 2){
                if (needProgress == 100) {
                    palier = 1;
                }
                if (needProgress == 500) {
                    palier = 2;
                }
                if (needProgress == 1000) {
                    palier = 3;
                }
                if (needProgress == 2500) {
                    palier = 4;
                }
                if (needProgress == 7500) {
                    palier = 5;
                }
                if (needProgress == 15000) {
                    palier = 6;
                }
                if (needProgress == 50000) {
                    palier = 7;
                }
                if (needProgress == 100000) {
                    palier = 8;
                }
                if (needProgress == 200000) {
                    palier = 9;
                }
                if (needProgress == 500000) {
                    palier = 10;
                }
            }
        }




        if (questPalier == 20) {
            if (questDiffuculty == 0){
                if (needProgress == 1) {
                    palier = 1;
                }
                if (needProgress == 5) {
                    palier = 2;
                }
                if (needProgress == 10) {
                    palier = 3;
                }
                if (needProgress == 25) {
                    palier = 4;
                }
                if (needProgress == 50) {
                    palier = 5;
                }
                if (needProgress == 100) {
                    palier = 6;
                }
                if (needProgress == 250) {
                    palier = 7;
                }
                if (needProgress == 500) {
                    palier = 8;
                }
                if (needProgress == 1000) {
                    palier = 9;
                }
                if (needProgress == 1250) {
                    palier = 10;
                }
                if (needProgress == 1750) {
                    palier = 11;
                }
                if (needProgress == 2500) {
                    palier = 12;
                }
                if (needProgress == 4000) {
                    palier = 13;
                }
                if (needProgress == 5000) {
                    palier = 14;
                }
                if (needProgress == 7500) {
                    palier = 15;
                }
                if (needProgress == 10000) {
                    palier = 16;
                }
                if (needProgress == 12500) {
                    palier = 17;
                }
                if (needProgress == 15000) {
                    palier = 18;
                }
                if (needProgress == 17500) {
                    palier = 19;
                }
                if (needProgress == 20000) {
                    palier = 20;
                }
            }



            if (questDiffuculty == 1){
                if (needProgress == 5) {
                    palier = 1;
                }
                if (needProgress == 10) {
                    palier = 2;
                }
                if (needProgress == 50) {
                    palier = 3;
                }
                if (needProgress == 100) {
                    palier = 4;
                }
                if (needProgress == 250) {
                    palier = 5;
                }
                if (needProgress == 500) {
                    palier = 6;
                }
                if (needProgress == 1000) {
                    palier = 7;
                }
                if (needProgress == 2500) {
                    palier = 8;
                }
                if (needProgress == 5000) {
                    palier = 9;
                }
                if (needProgress == 7500) {
                    palier = 10;
                }
                if (needProgress == 15000) {
                    palier = 11;
                }
                if (needProgress == 30000) {
                    palier = 12;
                }
                if (needProgress == 75000) {
                    palier = 13;
                }
                if (needProgress == 100000) {
                    palier = 14;
                }
                if (needProgress == 150000) {
                    palier = 15;
                }
                if (needProgress == 175000) {
                    palier = 16;
                }
                if (needProgress == 200000) {
                    palier = 17;
                }
                if (needProgress == 300000) {
                    palier = 18;
                }
                if (needProgress == 400000) {
                    palier = 19;
                }
                if (needProgress == 500000) {
                    palier = 20;
                }
            }



            if (questDiffuculty == 2){
                if (needProgress == 100) {
                    palier = 1;
                }
                if (needProgress == 500) {
                    palier = 2;
                }
                if (needProgress == 1000) {
                    palier = 3;
                }
                if (needProgress == 2500) {
                    palier = 4;
                }
                if (needProgress == 5000) {
                    palier = 5;
                }
                if (needProgress == 10000) {
                    palier = 6;
                }
                if (needProgress == 25000) {
                    palier = 7;
                }
                if (needProgress == 50000) {
                    palier = 8;
                }
                if (needProgress == 100000) {
                    palier = 9;
                }
                if (needProgress == 250000) {
                    palier = 10;
                }
                if (needProgress == 500000) {
                    palier = 11;
                }
                if (needProgress == 750000) {
                    palier = 12;
                }
                if (needProgress == 1000000) {
                    palier = 13;
                }
                if (needProgress == 1250000) {
                    palier = 14;
                }
                if (needProgress == 1500000) {
                    palier = 15;
                }
                if (needProgress == 1750000) {
                    palier = 16;
                }
                if (needProgress == 2000000) {
                    palier = 17;
                }
                if (needProgress == 3000000) {
                    palier = 18;
                }
                if (needProgress == 4000000) {
                    palier = 19;
                }
                if (needProgress == 5000000) {
                    palier = 20;

                }
            }
        }

        return palier;
    }



}
