package com.tenjava.entries.libraryaddict.t1.runes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class TrapRune implements Rune, Listener {
    private BukkitRunnable runnable;
    private Location trapLocation;
    private double trapSize;

    public TrapRune(Location loc, double size) {
        Bukkit.getPluginManager().registerEvents(this, RuneApi.getPlugin());
        this.trapLocation = loc;
        this.trapSize = size;
        initialParticles();
        runnable = new BukkitRunnable() {
            private int ticksLived;

            @Override
            public void run() {
                if (ticksLived++ > 2000) {// 10min
                    cancel();
                    HandlerList.unregisterAll(TrapRune.this);
                }
                for (LivingEntity entity : trapLocation.getWorld().getEntitiesByClass(LivingEntity.class)) {
                    Location loc = entity.getLocation();
                    if (isInTrap(loc)) {
                        cancel();
                        HandlerList.unregisterAll(TrapRune.this);
                        trapCard();
                        loc.getWorld().playSound(loc, Sound.WITHER_SHOOT, 5, 1.5F);
                        loc.getWorld().createExplosion(loc.add(0, 0.5, 0), 2F);
                        break;
                    }
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 1);
    }

    public ArrayList<Location> getBox() {
        ArrayList<Location> boxLocs = ShapesApi.getBox(trapLocation, trapSize);
        ArrayList<Location> returns = new ArrayList<Location>();
        for (int i = 0; i < boxLocs.size(); i++) {
            int a = i + 1 >= boxLocs.size() ? 0 : i + 1;
            returns.addAll(ShapesApi.getLines(boxLocs.get(i), boxLocs.get(a), 0.3D));
            returns.add(boxLocs.get(i));
        }
        return returns;
    }

    @Override
    public RuneType getType() {
        return RuneType.TRAP;
    }

    public void initialParticles() {
        for (Location loc : getBox()) {
            for (double y = 0; y < 1; y += 0.2) {
                ParticleApi.sendPackets(LibsParticles.FLAME, loc.getX(), loc.getY(), loc.getZ());
            }
        }
    }

    private boolean isInTrap(Location loc) {
        if (loc.getX() >= trapLocation.getX() - trapSize && loc.getX() <= trapLocation.getX() + trapSize) {
            if (loc.getZ() >= trapLocation.getZ() - trapSize && loc.getZ() <= trapLocation.getZ() + trapSize) {
                if (loc.getY() >= trapLocation.getY() - 0.1 && loc.getY() <= trapLocation.getY() + 0.9) {
                    return true;
                }
            }
        }
        return false;
    }

    public void trapCard() {
        for (Location loc : getBox()) {
            for (double y = 0; y < 1; y += 0.2) {
                ParticleApi.sendPackets(LibsParticles.FLAME, loc.getX(), loc.getY() + y, loc.getZ());
            }
        }
    }
}
