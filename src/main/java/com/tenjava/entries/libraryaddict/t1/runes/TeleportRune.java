package com.tenjava.entries.libraryaddict.t1.runes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.RuneApi;
import com.tenjava.entries.libraryaddict.t1.ShapesApi;
import com.tenjava.entries.libraryaddict.t1.ParticleApi.LibsParticles;

public class TeleportRune implements Rune {
    private Location firstLoc, secondLoc;
    private double runeSize;
    private int ticksLived;
    private int ticksToLive;
    private BukkitRunnable runnable;

    public TeleportRune(Location fLoc, Location sLoc, double rSize) {
        this.firstLoc = fLoc;
        this.secondLoc = sLoc;
        this.runeSize = rSize;
        ticksToLive = 100;
        runnable = new BukkitRunnable() {

            @Override
            public void run() {
                ticksLived++;
                if (ticksLived >= ticksToLive) {
                    cancel();
                }
                if (ticksLived % 5 == 0) {
                    resendRunes();
                }
                if (ticksLived % 40 == 0) {
                    firstLoc.getWorld().playSound(firstLoc, Sound.PORTAL, (float) runeSize * 2, 0F);
                    firstLoc.getWorld().playSound(secondLoc, Sound.PORTAL, (float) runeSize * 2, 0F);
                }
                for (LivingEntity entity : firstLoc.getWorld().getEntitiesByClass(LivingEntity.class)) {
                    Location loc = entity.getLocation();
                    if (loc.distance(firstLoc) <= runeSize && Math.abs(loc.getBlockY() - firstLoc.getBlockY()) < 2) {
                        // TODO Magic effects to wow the peasents
                        entity.teleport(secondLoc);
                    }
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 1);
    }

    /**
     * Resending runes resends the particles.
     */
    public void resendRunes() {
        redrawRunes(firstLoc);
        redrawRunes(secondLoc);
    }

    private void makeCircle(Location loc, double distance) {
        for (Location l : ShapesApi.getPointsCircle(loc, (int) Math.ceil(Math.PI * distance * 2), distance)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ParticleApi.sendPacket(player, LibsParticles.FIREWORKS, l.getX(), l.getY(), l.getZ());
            }
        }
    }

    private void redrawRunes(Location runeLoc) {
        runeLoc = runeLoc.clone();
        for (double y = 0; y < 1; y += 0.4) {
            runeLoc.add(0, y, 0);
            makeCircle(runeLoc, runeSize);
            makeCircle(runeLoc, (runeSize / 3) * 2);
        }
    }

}
