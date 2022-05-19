package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class SeasonExo extends JavaPlugin {

    Logger log = this.getLogger();
    public static FileConfiguration config;
    public static File configFile;

    @Override
    public void onEnable() {
        log.info("§6Season Exercise Enabled!");

        config = getConfig();
        config.options().copyDefaults(true);
        configFile = new File(this.getDataFolder(), "config.yml");
        this.saveDefaultConfig();

        config = YamlConfiguration.loadConfiguration(configFile);

        Bukkit.getScheduler().runTaskTimer(this, new ChestApparition(), 20*5, 20*30);

    }

    @Override
    public void onDisable() {
        log.info("§6Season Exercise Disabled!");
        Bukkit.getScheduler().cancelTasks(this);

    }

    /*public static void saveJobs() {
        try{
            jobs.save(jobsFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/

}
