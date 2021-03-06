package com.cactt4ck.seasonexo;

import me.clip.placeholderapi.PlaceholderAPI;
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
        this.getCommand("seasonevent").setExecutor(command1);
        this.getCommand("seasonevent").setTabCompleter(command1);
        Bukkit.getPluginManager().registerEvents(chestApparition, this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            log.info("§aPlaceholderAPI present!");
        else {
            Bukkit.getPluginManager().disablePlugin(this);
            log.info("§aPlaceholderAPI not present! Disabling SeasonExo...");
        }
    }

    @Override
    public void onDisable() {
        log.info("§6Season Exercise Disabled!");
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public void saveConfig() {
        super.saveConfig();
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
