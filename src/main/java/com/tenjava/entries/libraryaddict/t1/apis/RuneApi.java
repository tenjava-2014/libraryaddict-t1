package com.tenjava.entries.libraryaddict.t1.apis;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.libraryaddict.t1.runes.*;

public class RuneApi {
    private static JavaPlugin plugin;

    public static void castDefense(Location location) {
        new DefenseRune(location, 5);
    }

    public static void castExploding(Location loc, double size) {
        new ExplodingRune(loc, size);
    }

    public static void castHealing(Location secondTeleport, double i) {
        new HealingRune(secondTeleport, i);
    }

    public static void castHiding(Location secondTeleport, double i) {
        new HidingRune(secondTeleport, i);
    }

    public static void castSpeed(Location secondTeleport, double i) {
        new SpeedRune(secondTeleport, i);
    }

    public static void castSummoning(Location secondTeleport, double i) {
        new WitherSummoning(secondTeleport, i);
    }

    public static void castTeleport(Location firstTeleport, Location secondTeleport, double runeSize) {
        new TeleportRune(firstTeleport, secondTeleport, runeSize);
    }

    public static void castTrap(Location trapLoc, double runeSize) {
        new TrapRune(trapLoc, runeSize);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void init(JavaPlugin p) {
        plugin = p;
    }

}
