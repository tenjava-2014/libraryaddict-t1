package com.tenjava.entries.libraryaddict.t1;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddWandCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (sender.hasPermission("runemaster.grabwand")) {
            Player p = (Player) sender;
            if (args.length > 0) {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.RED + "Cannot find the player '" + args[0] + "'");
                    return false;
                }
            }
            ItemStack wand = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "The wand of ages");
            meta.setLore(Arrays.asList(ChatColor.BLUE + "No spell selected"));
            wand.setItemMeta(meta);
            p.getInventory().addItem(wand);
        }
        return true;
    }
}
