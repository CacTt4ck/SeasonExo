package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static int getHighestFlatSurface(World world, int x, int z) {
        List<Material> incorrectSpawnMaterial = Arrays.asList(
                Material.AIR,
                Material.ACACIA_LEAVES,
                Material.AZALEA_LEAVES,
                Material.BIRCH_LEAVES,
                Material.DARK_OAK_LEAVES,
                Material.FLOWERING_AZALEA_LEAVES,
                Material.JUNGLE_LEAVES,
                Material.OAK_LEAVES,
                Material.SPRUCE_LEAVES,
                Material.GRASS,
                Material.TALL_GRASS,
                Material.VINE,
                Material.LILY_PAD,
                Material.FERN,
                Material.LARGE_FERN
        );

        for (int maxY = 318; maxY>0 ; maxY--) {
            Material material = world.getBlockAt(x, maxY, z).getType();
            if (!incorrectSpawnMaterial.contains(material))
                return maxY;
        }
        return 0;
    }

    public static Inventory generateLoot(JavaPlugin main) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "§cLoot");

        ConfigurationSection section = main.getConfig().getConfigurationSection("loots");
        Set<String> keys = section.getKeys(false);
        final int maxIndex = keys.size() + 1;
        int choice = new Random().nextInt(1, maxIndex);

        section.getStringList(String.valueOf(choice)).forEach(material -> {
            int pos;
            do {
                pos = new Random().nextInt(0, 27);

            } while (inv.getItem(pos) != null);

            String[] splitString = material.split(",");
            String item = splitString[0].toUpperCase();
            int quantity = Integer.parseInt(splitString[1].trim()),
                    chanceToSpawn = Integer.parseInt(splitString[2].trim());

            if (new Random().nextInt(1, 101) <= chanceToSpawn)
                inv.setItem(pos, new ItemStack(Material.valueOf(item), quantity));
        });
        return inv;
    }

}
