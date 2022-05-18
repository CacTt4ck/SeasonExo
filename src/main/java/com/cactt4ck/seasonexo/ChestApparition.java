package com.cactt4ck.seasonexo;

import org.bukkit.Bukkit;

public class ChestApparition implements Runnable {

    int counter = 0;

    @Override
    public void run() {
        Bukkit.getServer().broadcastMessage("Test " + this.counter);
        this.counter++;
    }

}
