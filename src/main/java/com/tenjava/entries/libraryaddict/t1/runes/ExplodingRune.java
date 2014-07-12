package com.tenjava.entries.libraryaddict.t1.runes;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class ExplodingRune implements Rune {

    private Location location;
    private BukkitRunnable runnable;
    private double size;

    public ExplodingRune(Location loc, double s) {
        this.size = s;
        this.location = loc;
        runnable = new BukkitRunnable() {
            private int ticksLived = 0;

            public void run() {
                sendParticles();
                if (ticksLived++ / 5 >= 5) {
                    cancel();
                    location.getWorld().createExplosion(location.clone().add(0, 1, 0), (float) size + 1);
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 4);
    }

    @Override
    public RuneType getType() {
        return RuneType.EXPLODING;
    }

    private void sendParticles() {
        for (Location loc : ShapesApi.getPointsCircle(location, (int) Math.ceil(Math.PI * size * 2), size)) {
            ParticleApi.sendPackets(LibsParticles.LAVA, loc.getX(), loc.getY(), loc.getZ());
        }
    }
}
