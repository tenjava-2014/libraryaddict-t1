package com.tenjava.entries.libraryaddict.t1.apis;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.libraryaddict.t1.runes.*;

public class RuneApi {
    private static JavaPlugin plugin;

    public static void init(JavaPlugin p) {
        plugin = p;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void castTeleport(Location firstTeleport, Location secondTeleport, double runeSize) {
        TeleportRune rune = new TeleportRune(firstTeleport, secondTeleport, runeSize);
    }

    public static void castTrap(Location trapLoc, double runeSize) {
        TrapRune rune = new TrapRune(trapLoc, runeSize);
    }

    public static void castDefense(Location location) {
        DefenseRune rune = new DefenseRune(location, 5);
    }

}
