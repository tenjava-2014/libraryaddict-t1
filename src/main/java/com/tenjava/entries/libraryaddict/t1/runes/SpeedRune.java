package com.tenjava.entries.libraryaddict.t1.runes;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class SpeedRune implements Rune {

    private Location location;
    private BukkitRunnable runnable;
    private double size;

    public SpeedRune(Location loc, double rSize) {
        this.location = loc;
        this.size = rSize;
        runnable = new BukkitRunnable() {

            private int ticksLived;

            @Override
            public void run() {
                if (ticksLived % 10 == 0) {
                    sendParticles();
                }
                if (ticksLived++ >= 7 * 20) {
                    cancel();
                }
                if (ticksLived % 20 == 0) {
                    for (LivingEntity entity : location.getWorld().getEntitiesByClass(LivingEntity.class)) {
                        if (!(entity instanceof Monster)) {
                            Location loc = entity.getLocation();
                            if (loc.distance(location) <= size && loc.getBlockY() >= location.getBlockY()
                                    && loc.getBlockY() <= location.getBlockY() + 2) {
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20 * 60, 1), true);
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
        return RuneType.SPEED;
    }

    public void sendParticles() {
        for (int y = 0; y < 3; y++) {
            for (Location loc : ShapesApi.getPointsCircle(location.clone().add(0, y, 0), (int) Math.ceil(Math.PI * size * 2),
                    size)) {
                ParticleApi.sendPackets(LibsParticles.CLOUD, loc.getX(), loc.getY(), loc.getZ());
            }
        }
    }

}
