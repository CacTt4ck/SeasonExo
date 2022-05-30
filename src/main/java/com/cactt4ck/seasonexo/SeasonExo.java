package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

        ChestApparition chestApparition = new ChestApparition(this);
        TabExecutor command1 = new Commands(this, chestApparition);
        this.getCommand("event").setExecutor(command1);
        this.getCommand("event").setTabCompleter(command1);
        Bukkit.getPluginManager().registerEvents(chestApparition, this);
    }

    @Override
    public void onDisable() {
        log.info("§6Season Exercise Disabled!");
        Bukkit.getScheduler().cancelTasks(this);

    }

    @NotNull
    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }

    /*public static void saveJobs() {
        try{
            jobs.save(jobsFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/

}
