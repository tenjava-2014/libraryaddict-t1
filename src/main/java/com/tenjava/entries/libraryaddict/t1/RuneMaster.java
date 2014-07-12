package com.tenjava.entries.libraryaddict.t1;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;

public class RuneMaster extends JavaPlugin implements Listener {

    private ItemStack wand;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        RuneApi.init(this);
        wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "The wand of ages");
        meta.setLore(Arrays.asList(ChatColor.BLUE + "No spell selected"));
        wand.setItemMeta(meta);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getItemInHand();
        if (item != null && item.getType() == Material.BLAZE_ROD) {
            String displayName = item.getItemMeta().getDisplayName();
            if (displayName != null && displayName.startsWith(ChatColor.GOLD + "The wand of ages")) {
                List<String> lore = item.getItemMeta().getLore();
                if (lore != null && !lore.isEmpty()) {
                    String selectedSpell = lore.get(0);
                    Block b = p.getTargetBlock(null, 150);
                    while (b.getType() != Material.AIR) {
                        b = b.getRelative(BlockFace.UP);
                    }
                    Location firstTeleport = p.getLocation();
                    Location secondTeleport = b.getLocation().add(0.5, 0, 0.5);
                    double runeSize = Math.min(5, Math.max(2, firstTeleport.distance(secondTeleport) / 10));
                    RuneApi.castTrap(secondTeleport, 2);
                    // RuneApi.castTeleport(firstTeleport, secondTeleport, runeSize);
                }
            }
        }
    }
}
