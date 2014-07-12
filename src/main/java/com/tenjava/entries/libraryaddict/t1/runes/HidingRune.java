package com.tenjava.entries.libraryaddict.t1.runes;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class HidingRune implements Rune, Listener {

    private HashSet<String> in = new HashSet<String>();
    private Location location;
    private BukkitRunnable runnable;

    private double size;

    public HidingRune(Location loc, double rSize) {
        Bukkit.getPluginManager().registerEvents(this, RuneApi.getPlugin());
        this.location = loc;
        this.size = rSize;
        runnable = new BukkitRunnable() {

            private int ticksLived;

            @Override
            public void run() {
                if (ticksLived % 10 == 0) {
                    sendParticles();
                }
                if (ticksLived++ >= 30 * 20) {
                    cancel();
                    HandlerList.unregisterAll(HidingRune.this);
                    for (String name : in) {
                        Player p = Bukkit.getPlayerExact(name);
                        if (p != null) {
                            p.removePotionEffect(PotionEffectType.INVISIBILITY);
                            for (Player pa : Bukkit.getOnlinePlayers()) {
                                pa.showPlayer(p);
                            }
                        }
                    }
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 1);
    }

    @Override
    public RuneType getType() {
        return RuneType.HIDING;
    }

    private boolean isIn(Location loc) {
        return loc.distance(location) <= size && loc.getBlockY() >= location.getBlockY()
                && loc.getBlockY() <= location.getBlockY() + 3;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (isIn(event.getTo()) != in.contains(p.getName())) {
            if (in.contains(p.getName())) {
                in.remove(p.getName());
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                for (Player pa : Bukkit.getOnlinePlayers()) {
                    pa.hidePlayer(p);
                }
            } else {
                in.add(p.getName());
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0));
                for (Player pa : Bukkit.getOnlinePlayers()) {
                    pa.showPlayer(p);
                }
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!event.isCancelled() && event.getTarget() instanceof Player) {
            if (isIn(event.getTarget().getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    public void sendParticles() {
        for (Location loc : ShapesApi.getPointsCircle(location, (int) Math.ceil(Math.PI * size * 2), size)) {
            ParticleApi.sendPackets(LibsParticles.CLOUD, loc.getX(), loc.getY(), loc.getZ());
        }
    }

}
