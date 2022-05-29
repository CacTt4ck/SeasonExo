package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Commands implements TabExecutor {

    JavaPlugin main;
    private int task;
    boolean eventRunning;
    private ChestApparition chestApparition;

    public Commands(JavaPlugin main) {
        this.main = main;
        chestApparition = new ChestApparition(this.main);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("event")) {
            if (args.length != 1)
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
                this.chestApparition.close();
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("event"))
            return Arrays.asList("start", "stop", "closeinv");
        return null;
    }
}