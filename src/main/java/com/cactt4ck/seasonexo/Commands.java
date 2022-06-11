package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Commands implements TabExecutor {

    JavaPlugin main;
    boolean eventRunning;
    private ChestApparition chestApparition;

    public Commands(JavaPlugin main, ChestApparition chestApparition) {
        this.main = main;
        this.chestApparition = chestApparition;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("seasonevent")) {
            if (args.length < 1 || args.length > 4)
                return false;

            if (args[0].equalsIgnoreCase("start")) {
                if (eventRunning) {
                    p.sendMessage(ChatColor.RED + "Event is still running!");
                    return true;
                }
                Bukkit.getScheduler().runTaskTimer(this.main, this.chestApparition, 20*5, 20);
                p.sendMessage(ChatColor.GREEN + "Event started!");
                eventRunning = true;
                return true;

            } else if (args[0].equalsIgnoreCase("stop")) {
                if (eventRunning) {
                    this.chestApparition.stop();
                    Bukkit.getScheduler().cancelTasks(main);
                    p.sendMessage(ChatColor.RED + "Event stopped!");
                    eventRunning = false;
                    return true;
                }
                p.sendMessage(ChatColor.RED + "No event running!");
                return true;

            } else if (args[0].equalsIgnoreCase("closeinv")) {
                if (!eventRunning) {
                    p.sendMessage(ChatColor.RED + "No event running!");
                    return true;
                }
                this.chestApparition.close();
                return true;

            } else if (args[0].equalsIgnoreCase("delete")) {
                if (!eventRunning) {
                    p.sendMessage(ChatColor.RED + "No event running!");
                    return true;
                }
                this.chestApparition.delete();
                return true;

            } else if (args[0].equalsIgnoreCase("reload")) {
                main.reloadConfig();
                p.sendMessage(ChatColor.GREEN + "Season Event reload complete!");
                return true;

            } else if (args[0].equalsIgnoreCase("addpos")) {
                final Location playerLoc = p.getLocation();
                ConfigurationSection section = main.getConfig().getConfigurationSection("locations");
                Set<String> keys = section.getKeys(false);
                final int maxIndex = keys.size() + 1;


                if (args.length == 3) { // /se addpos x z
                    section.set(maxIndex + ".world", playerLoc.getWorld().getName());
                    section.set(maxIndex + ".x", Integer.parseInt(args[1]));
                    section.set(maxIndex + ".z", Integer.parseInt(args[2]));

                    main.saveConfig();
                    main.reloadConfig();
                    p.sendMessage("§aPosition added!");

                    return true;

                } else if (args.length == 1) { // /se addpos
                    section.set(maxIndex + ".world", playerLoc.getWorld().getName());
                    section.set(maxIndex + ".x", playerLoc.getBlockX());
                    section.set(maxIndex + ".z", playerLoc.getBlockZ());

                    main.saveConfig();
                    main.reloadConfig();
                    p.sendMessage("§aPosition added!");

                    return true;
                }
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("seasonevent") || cmd.getName().equalsIgnoreCase("se") || cmd.getName().equalsIgnoreCase("sevt"))
            return Arrays.asList("start", "stop", "closeinv", "delete", "reload", "addpos");

        return null;
    }
}
