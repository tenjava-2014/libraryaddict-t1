package com.tenjava.entries.libraryaddict.t1.runes;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum RuneType {
    TELEPORT(ChatColor.BLUE + "Teleporter", new ItemStack(Material.COMMAND), "Create two teleport portals!"), TRAP(ChatColor.GRAY
            + "Trap Card", new ItemStack(Material.TRIPWIRE_HOOK), "Step on the rune trap for a nasty surprise!");

    private String runeName;
    private ItemStack runeIcon;

    private RuneType(String runeName, ItemStack runeIcon, String... loreArray) {
        ItemMeta meta = runeIcon.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        for (String l : loreArray) {
            lore.add(ChatColor.GREEN + l);
        }
        meta.setLore(lore);
        meta.setDisplayName(runeName);
        runeIcon.setItemMeta(meta);
        this.runeIcon = runeIcon;
        this.runeName = runeName;
    }

    public String getName() {
        return runeName;
    }

    public ItemStack getIcon() {
        return runeIcon;
    }

    public static RuneType getRune(String name) {
        for (RuneType rune : RuneType.values()) {
            if (rune.getName().equals(name)) {
                return rune;
            }
        }
        return null;
    }
}
