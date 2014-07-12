package com.tenjava.entries.libraryaddict.t1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RuneMaster extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        RuneApi.init(this);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Player p = event.getPlayer();
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
