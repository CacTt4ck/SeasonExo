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
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ChestApparition implements Runnable, Listener {

    private boolean chestSpawned = false, chestOpened;
    private Location chestLocation;
    private TextComponent coords, message;
    JavaPlugin main;
    private int timer = 2*30;
    private World world;
    private Chest chest;

    public ChestApparition(JavaPlugin main) {
        this.main = main;
    }

    @Override
    public void run() {

        if (timer == 0) {
            this.chestLocation.getBlock().setType(Material.AIR);
            for (Player p : world.getPlayers())
                p.sendMessage(org.bukkit.ChatColor.RED + "Le coffre a disparu!");
            chestSpawned = false;
            resetTimer();

        } else if (timer <= 10 && timer > 0)
            Bukkit.broadcastMessage("Il ne reste plus que " + timer + " secondes pour trouver le coffre!");
        else if (timer%60 == 0)
            Bukkit.broadcastMessage("Il ne reste plus que " + (timer/60)%60 + " minutes");
        else if (timer%30 == 0)
            Bukkit.broadcastMessage("Il ne reste plus que " + (timer/60)%60 + " minutes et " + timer%60 + " secondes");
        timer--;

        if (chestSpawned)
            return;

        this.world = Bukkit.getWorld("world");

        int x = new Random().nextInt(-100, 100),
                z = new Random().nextInt(-100, 100),
                y = Utils.getHighestFlatSurface(world, x, z) + 1;
        this.chestLocation = new Location(world, x, y, z);
        this.chestLocation.getBlock().setType(Material.CHEST);
        chest = (Chest) chestLocation.getBlock().getState();
        chest.getBlockInventory().addItem(new ItemStack(Material.IRON_AXE));

        this.message = new TextComponent("Un coffre est apparu aux coordonnées ");
        this.message.setColor(ChatColor.RED);

        this.coords = new TextComponent(x + " " + y + " " + z);
        this.coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Se tp").create()));
        this.coords.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp @s " + x + " " + y + " " + z));
        this.coords.setColor(ChatColor.DARK_PURPLE);

        for (Player p : world.getPlayers())
            p.spigot().sendMessage(this.message, this.coords);
        chestSpawned = true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (!(event.getClickedBlock().getState() instanceof Chest))
            return;
        if (event.getClickedBlock().getState() == chest) {
            if (!chestOpened) {
                chestOpened = true;
                return;
            }
            event.setCancelled(true);
            p.sendMessage("Ce coffre est déjà ouvert!");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    public void stop() {
        chestLocation.getBlock().setType(Material.AIR);
        chestSpawned = false;
        resetTimer();
    }

    public void close() {
        chestLocation.getBlock().setType(Material.AIR);
        chestSpawned = false;
        resetTimer();
    }

    private void resetTimer() {
        timer = 2*30;
    }

}
