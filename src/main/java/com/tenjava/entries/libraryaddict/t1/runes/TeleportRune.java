package com.tenjava.entries.libraryaddict.t1.runes;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class TeleportRune implements Rune {
    private Location firstLoc, secondLoc;
    private double runeSize;
    private BukkitRunnable runnable;
    private int ticksLived = -60;
    private int ticksToLive;

    public TeleportRune(Location fLoc, Location sLoc, double rSize) {
        this.firstLoc = fLoc;
        this.secondLoc = sLoc;
        this.runeSize = rSize;
        ticksToLive = 150;
        runnable = new BukkitRunnable() {

            @Override
            public void run() {
                if (ticksLived >= ticksToLive) {
                    cancel();
                }
                if (ticksLived % 5 == 0) {
                    resendRunes();
                }
                if (ticksLived % 40 == 0 && ticksLived < 150) {
                    firstLoc.getWorld().playSound(firstLoc, Sound.PORTAL, (float) Math.min(3, runeSize), 0F);
                    firstLoc.getWorld().playSound(secondLoc, Sound.PORTAL, (float) Math.min(3, runeSize), 0F);
                }
                if (ticksLived > 0) {
                    for (LivingEntity entity : firstLoc.getWorld().getEntitiesByClass(LivingEntity.class)) {
                        Location loc = entity.getLocation();
                        if (loc.distance(firstLoc) <= runeSize && loc.getBlockY() >= firstLoc.getBlockY()
                                && loc.getBlockY() <= firstLoc.getBlockY() + 2) {
                            for (double y = 0; y < 2; y += 0.5) {
                                ParticleApi.sendPackets(LibsParticles.PORTAL, secondLoc.getX(), secondLoc.getY() + y,
                                        secondLoc.getZ(), 1.5, 1.5, 1.5, 40);
                            }
                            ParticleApi.sendPackets(LibsParticles.HUGE_EXPLOSION, secondLoc.getX(), secondLoc.getY() + 1,
                                    secondLoc.getZ(), 0, 0, 0, 5);
                            secondLoc.getWorld().playSound(secondLoc, Sound.ENDERMAN_TELEPORT, 3, 0);
                            Location newLoc = secondLoc.clone();
                            newLoc.setDirection(entity.getEyeLocation().getDirection());
                            entity.setFallDistance(0F);
                            entity.teleport(newLoc);
                        }
                    }
                }
                ticksLived++;
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 1);
    }

    @Override
    public RuneType getType() {
        return RuneType.TELEPORT;
    }

    private void makeCircle(Location loc, double distance) {
        for (Location l : ShapesApi.getPointsCircle(loc, (int) Math.ceil(Math.PI * distance * 2), distance)) {
            ParticleApi.sendPackets(ticksLived >= 0 ? LibsParticles.FIREWORKS : LibsParticles.RED_DUST, l.getX(), l.getY(),
                    l.getZ());
        }
    }

    private void redrawRunes(Location runeLoc) {
        runeLoc = runeLoc.clone();
        if (ticksLived >= 0) {
            for (double y = 0; y < 1; y += 0.4) {
                runeLoc.add(0, y, 0);
                makeCircle(runeLoc, runeSize);
                makeCircle(runeLoc, (runeSize / 3) * 2);
            }
        } else {
            makeCircle(runeLoc, runeSize);
        }
    }

    /**
     * Resending runes resends the particles.
     */
    public void resendRunes() {
        redrawRunes(firstLoc);
        redrawRunes(secondLoc);
    }

}
