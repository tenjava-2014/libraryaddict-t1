package com.tenjava.entries.libraryaddict.t1.apis;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import com.tenjava.entries.libraryaddict.t1.runes.*;

public class SpellApi {
    private static HashMap<String, RuneType> spells = new HashMap<String, RuneType>();
    static {
        for (RuneType type : RuneType.values()) {
            spells.put(type.getName(), type);
        }
    }

    public static String getName(Rune rune) {
        for (Entry<String, RuneType> entry : spells.entrySet()) {
            if (entry.getValue() == rune.getType()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static RuneType getRune(String name) {
        if (spells.containsKey(name)) {
            return spells.get(name);
        }
        return null;
    }
}
