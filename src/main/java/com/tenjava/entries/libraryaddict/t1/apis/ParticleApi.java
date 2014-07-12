package com.tenjava.entries.libraryaddict.t1.apis;

import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleApi {
    public enum LibsParticles {
        BUBBLE("bubble"), FIREWORKS("fireworksSpark"), FLAME("flame"), HEART("heart"), HUGE_EXPLOSION("hugeexplosion"), LAVA(
                "lava"), PORTAL("portal"), RED_DUST("reddust"), WATER_DRIP("dripWater"), LAVA_DRIP("dripLava");
        private String name;

        private LibsParticles(String name) {
            this.name = name;
        }
    }

    public static void sendPacket(Player player, LibsParticles particle, double x, double y, double z) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle.name, (float) x, (float) y, (float) z, 0,
                0, 0, 0, 1);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPackets(LibsParticles particle, double x, double y, double z) {
        sendPackets(particle, x, y, z, 0, 0, 0, 1);
    }

    public static void sendPackets(LibsParticles particle, double x, double y, double z, double xOffset, double zOffset,
            double yOffset, int particleNumber) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle.name, (float) x, (float) y, (float) z,
                (float) xOffset, (float) yOffset, (float) zOffset, 0F, particleNumber);
        Location loc = new Location(null, x, y, z);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (loc.getWorld() == null) {
                loc.setWorld(player.getWorld());
            }
            if (player.getLocation().distance(loc) < 50) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

}
