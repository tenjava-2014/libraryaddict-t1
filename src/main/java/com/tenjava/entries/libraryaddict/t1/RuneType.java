package com.tenjava.entries.libraryaddict.t1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum RuneType {
    DEFENSE(ChatColor.WHITE + "Defense Rune", new ItemStack(Material.IRON_CHESTPLATE),
            "Cast a spell which defends the players inside!"),

    EXPLODING(ChatColor.RED + "Exploding Rune", new ItemStack(Material.FIREBALL), "Cast a rune which explodes after 5 seconds!"),

    HEALING(ChatColor.RED + "Healing Rune", new ItemStack(Material.POTION, 1, (short) 8197), "Cast a rune on the ground to",
            "passively heal people!"),

    HIDING(ChatColor.GRAY + "Rune of Hiding", new ItemStack(Material.POTION, 1, (short) 8206), "Create a rune to hide in!"),

    SPEED(ChatColor.WHITE + "Speed Rune", new ItemStack(Material.QUARTZ), "Create a rune to boost up the",
            "speed of whatever steps inside!"),

    TELEPORT(ChatColor.BLUE + "Teleporter Rune", new ItemStack(Material.COMMAND), "Create two teleport portals!"),

    TRAP(ChatColor.GRAY + "Trap Card", new ItemStack(Material.TRIPWIRE_HOOK), "Step on the rune trap for a nasty surprise!"),

    WITHER_SUMMONING(ChatColor.DARK_RED + "Witch Summoning", new ItemStack(Material.FIRE), "Cast a rune on the ground to",
            "summon a angry wither!");

    public static RuneType getRune(String name) {
        for (RuneType rune : RuneType.values()) {
            if (rune.getName().equals(name)) {
                return rune;
            }
        }
        return null;
    }

    private int cost;
    private ItemStack runeIcon;

    private String runeName;

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

    public int getCost() {
        return cost;
    }

    public ItemStack getIcon() {
        return runeIcon;
    }

    public String getName() {
        return runeName;
    }

    public void setCost(int newCost) {
        this.cost = newCost;
        ItemMeta meta = runeIcon.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GOLD + "Rune cost: " + ChatColor.YELLOW + getCost());
        meta.setLore(lore);
        runeIcon.setItemMeta(meta);
    }
}
