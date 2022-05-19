package com.cactt4ck.seasonexo;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class ChestApparition implements Runnable {

    private boolean chestSpawned = false;
    private Location chestLocation;
    private TextComponent coords, message;

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        if (this.chestSpawned) {
            this.chestLocation.getBlock().setType(Material.AIR);
            this.chestSpawned = false;
        }

        int x = new Random().nextInt(-100, 100),
                z = new Random().nextInt(-100, 100),
                y = Utils.getHighestFlatSurface(world, x, z) + 1;
        this.chestLocation = new Location(world, x, y, z);
        this.chestLocation.getBlock().setType(Material.CHEST);

        this.message = new TextComponent("Un coffre est apparu aux coordonnées ");
        this.message.setColor(ChatColor.RED);

        this.coords = new TextComponent(x + " " + y + " " + z);
        this.coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Se tp").create()));
        this.coords.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + x + " " + y + " " + z));
        this.coords.setColor(ChatColor.DARK_PURPLE);

        for (Player p : world.getPlayers())
            p.spigot().sendMessage(this.message, this.coords);
        this.chestSpawned = true;
    }

}
