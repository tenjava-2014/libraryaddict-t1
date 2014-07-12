package com.tenjava.entries.libraryaddict.t1;

import java.util.ArrayList;

import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleApi {

    private static void sendPacket(Player player, double x, double y, double z) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles("flame", (float) x, (float) y, (float) z, 0, 0, 0,
                0, 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static ArrayList<Location> getPoints(Location center, int pointsAmount, double distance) {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = 0; i < pointsAmount; i++) {
            double angle = ((2 * Math.PI) / (double) pointsAmount) * (double) i;
            double x = distance * Math.cos(angle);
            double z = distance * Math.sin(angle);
            Location loc = center.clone().add(x, 0, z);
            locs.add(loc);
        }
        return locs;
    }

    public static void makeCircle(World world, Location loc, double distance) {
        for (Location l : getPoints(loc, (int) Math.ceil(Math.PI * distance * 2), distance)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, l.getX(), l.getY(), l.getZ());
            }
        }
    }

}
