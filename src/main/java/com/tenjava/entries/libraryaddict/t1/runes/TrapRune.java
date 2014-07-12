package com.tenjava.entries.libraryaddict.t1.runes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.tenjava.entries.libraryaddict.t1.ParticleApi;
import com.tenjava.entries.libraryaddict.t1.ParticleApi.LibsParticles;
import com.tenjava.entries.libraryaddict.t1.RuneApi;
import com.tenjava.entries.libraryaddict.t1.ShapesApi;

public class TrapRune implements Rune, Listener {
    private Location trapLocation;
    private double trapSize;

    public TrapRune(Location loc, double size) {
        Bukkit.getPluginManager().registerEvents(this, RuneApi.getPlugin());
        this.trapLocation = loc;
        this.trapSize = size;
    }

    public ArrayList<Location> getBox() {
        ArrayList<Location> boxLocs = ShapesApi.getBox(trapLocation, trapSize);
        ArrayList<Location> returns = new ArrayList<Location>();
        for (int i = 0; i < boxLocs.size(); i++) {
            returns.add(boxLocs.get(i));
            returns.addAll(ShapesApi.getLines(boxLocs.get(i), boxLocs.get(i + 1 >= boxLocs.size() ? 0 : i + 1), 0.3D));
        }
        return returns;
    }

    public void initialParticles() {
        for (Location loc : getBox()) {
            ParticleApi.sendPackets(LibsParticles.FLAME, loc.getX(), loc.getY(), loc.getZ());
        }
    }
}
