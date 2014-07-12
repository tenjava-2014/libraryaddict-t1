package com.tenjava.entries.libraryaddict.t1;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;

public class RuneMaster extends JavaPlugin implements Listener {

    private Inventory spellInv = Bukkit.createInventory(null, (int) (Math.ceil((double) RuneType.values().length / 9)) * 9,
            ChatColor.GOLD + "Rune selector");

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        RuneApi.init(this);
        int i = 0;
        for (RuneType type : RuneType.values()) {
            ItemStack item = type.getIcon();
            spellInv.setItem(i++, item);
        }
        getCommand("addwand").setExecutor(new AddWandCommand());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().equals(spellInv)) {
            if (event.getRawSlot() < spellInv.getSize() || event.isShiftClick()) {
                event.setCancelled(true);
                if (event.getRawSlot() < spellInv.getSize()) {
                    ItemStack item = event.getCurrentItem();
                    if (item != null && item.getType() != Material.AIR) {
                        Player p = (Player) event.getWhoClicked();
                        if (isWand(p.getItemInHand())) {
                            String displayName = item.getItemMeta().getDisplayName();
                            RuneType type = RuneType.getRune(displayName);
                            if (type != null) {
                                item = p.getItemInHand();
                                ItemMeta meta = item.getItemMeta();
                                ArrayList<String> lore = new ArrayList<String>();
                                lore.add(type.getName());
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                p.updateInventory();
                                p.sendMessage(ChatColor.RED + "You have selected the rune " + type.getName());
                            } else {
                                p.sendMessage(ChatColor.RED + "Error! Can't find the rune " + displayName);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You must be holding a wand!");
                        }
                    }
                }
            }
        }
    }

    private boolean isWand(ItemStack item) {
        if (item != null && item.getType() == Material.BLAZE_ROD) {
            String displayName = item.getItemMeta().getDisplayName();
            if (displayName != null && displayName.startsWith(ChatColor.GOLD + "The wand of ages")) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getItemInHand();
        if (isWand(item)) {
            if (event.getAction() != Action.PHYSICAL) {
                event.setCancelled(true);
                if (!p.isSneaking()) {
                    List<String> lore = item.getItemMeta().getLore();
                    if (lore != null && !lore.isEmpty()) {
                        RuneType type = RuneType.getRune(lore.get(0));
                        if (type != null) {
                            boolean cast = true;
                            switch (type) {
                            case TRAP:
                            case TELEPORT:
                            case EXPLODING:
                                Block b = p.getTargetBlock(null, type == RuneType.TRAP ? 10 : 150);
                                while (b.getType() != Material.AIR) {
                                    b = b.getRelative(BlockFace.UP);
                                }
                                if (b.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                                    p.sendMessage(ChatColor.RED + "Unable to place a rune!");
                                    return;
                                }
                                Location firstTeleport = p.getLocation();
                                Location secondTeleport = b.getLocation().add(0.5, 0, 0.5);
                                double runeSize = Math.min(5, Math.max(2, firstTeleport.distance(secondTeleport) / 10));
                                switch (type) {
                                case TRAP:
                                    RuneApi.castTrap(secondTeleport, 2);
                                    break;
                                case TELEPORT:
                                    RuneApi.castTeleport(firstTeleport, secondTeleport, runeSize);
                                    break;
                                case EXPLODING:
                                    RuneApi.castExploding(secondTeleport, 3);
                                    break;
                                default:
                                    break;
                                }
                                break;
                            case DEFENSE:
                                RuneApi.castDefense(p.getLocation());
                                break;
                            default:
                                cast = false;
                                break;
                            }
                            if (cast) {
                                p.getWorld().playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 3, 0.8F);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You don't have a spell selected!");
                        }
                    }
                } else {
                    event.getPlayer().openInventory(spellInv);
                }
            }
        }
    }
}
