package com.tenjava.entries.libraryaddict.t1.apis;

import java.util.HashMap;

import org.bukkit.ChatColor;

import com.tenjava.entries.libraryaddict.t1.runes.Rune;
import com.tenjava.entries.libraryaddict.t1.runes.TrapRune;

public class SpellApi {
    private static HashMap<String, Class<? extends Rune>> spells = new HashMap<String, Class<? extends Rune>>();
    static {
        spells.put(ChatColor.BLUE + "Trapcard", TrapRune.class);
        spells.put(ChatColor.BLUE + "Teleporter", TrapRune.class);
    }

    public static Class<? extends Rune> getRune(String name) {
        if (spells.containsKey(name)) {
            return spells.get(name);
        }
        return null;
    }
}
