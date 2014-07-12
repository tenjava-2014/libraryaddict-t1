package com.tenjava.entries.libraryaddict.t1.runes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class DefenseRune implements Rune, Listener {

    private Location location;
    private BukkitRunnable runnable;
    private double size;

    public DefenseRune(Location loc, double rSize) {
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
                if (ticksLived++ >= 20 * 20) {
                    cancel();
                    HandlerList.unregisterAll(DefenseRune.this);
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 1);
    }

    @Override
    public RuneType getType() {
        return RuneType.DEFENSE;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.isCancelled() && event.getEntity() instanceof Player) {
            Location loc = event.getEntity().getLocation();
            if (loc.distance(location) <= size && loc.getBlockY() >= location.getBlockY()
                    && loc.getBlockY() <= location.getBlockY() + 3) {
                event.setDamage(event.getDamage() / 4);
                ParticleApi.sendPackets(LibsParticles.RED_DUST, location.getX(), location.getY() + 1.5, location.getZ(), 3, 1.5,
                        3, 100);
            }
        }
    }

    public void sendParticles() {
        for (int y = 1; y <= 3; y++) {
            for (Location loc : ShapesApi.getPointsCircle(location.clone().add(0, y, 0), (int) Math.ceil(Math.PI * size * 2),
                    size)) {
                ParticleApi.sendPackets(LibsParticles.WATER_DRIP, loc.getX(), loc.getY(), loc.getZ());
            }
        }
    }

}
