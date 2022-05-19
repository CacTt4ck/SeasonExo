package com.cactt4ck.seasonexo;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.Arrays;
import java.util.List;

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

}