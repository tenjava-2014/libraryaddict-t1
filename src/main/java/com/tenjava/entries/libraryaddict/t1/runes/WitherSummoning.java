package com.tenjava.entries.libraryaddict.t1.runes;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import com.tenjava.entries.libraryaddict.t1.Rune;
import com.tenjava.entries.libraryaddict.t1.RuneType;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.apis.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.apis.RuneApi;
import com.tenjava.entries.libraryaddict.t1.apis.ShapesApi;

public class WitherSummoning implements Rune {

    private Location location;
    private BukkitRunnable runnable;
    private double size;

    /**
     * Slowly draw the circle Then draw the star Then when its all drawn. Turn it RED!!!!
     */
    public WitherSummoning(Location loc, double s) {
        this.location = loc;
        this.size = s;
        final ArrayList<Location> points = ShapesApi.getPointsCircle(location, (int) Math.ceil(Math.PI * size * 2), size);
        ArrayList<Location> starPoints = ShapesApi.getPointsCircle(location, 5, size);
        int a = new Random().nextInt(5);
        for (int i = 0; i < starPoints.size(); i++) {
            int a2 = a;
            a += 2;
            if (a >= starPoints.size()) {
                a -= starPoints.size();
            }
            points.addAll(ShapesApi.getLines(starPoints.get(a2), starPoints.get(a), 0.7));
        }
        runnable = new BukkitRunnable() {
            int pos;

            public void run() {
                pos++;
                if (pos % 5 == 0 && pos > points.size()) {
                    location.getWorld().playSound(location, Sound.LAVA_POP, 4, 0);
                } else if (pos % 10 == 0 && pos <= points.size()) {
                    location.getWorld().playSound(location, Sound.GHAST_MOAN, 4, 0);
                }
                for (int a = 0; a < Math.min(pos, points.size()); a++) {
                    Location loc = points.get(a);
                    ParticleApi.sendPackets(pos >= points.size() ? LibsParticles.RED_DUST : LibsParticles.FIREWORKS, loc.getX(),
                            loc.getY() + 0.1, loc.getZ());
                }
                if (pos == points.size() + 30) {
                    location.getWorld().spawnEntity(location.add(0, 1, 0), EntityType.WITHER);
                    location.getWorld().playSound(location, Sound.WITHER_SPAWN, 4, 0);
                    location.getWorld().playSound(location, Sound.WITHER_SPAWN, 4, 0.5F);
                    ParticleApi.sendPackets(LibsParticles.HUGE_EXPLOSION, location.getX(), location.getY() + 3, location.getZ(),
                            5, 5, 5, 25);
                    cancel();
                }
            }
        };
        runnable.runTaskTimer(RuneApi.getPlugin(), 0, 4);
    }

    @Override
    public RuneType getType() {
        return RuneType.WITHER_SUMMONING;
    }

}
