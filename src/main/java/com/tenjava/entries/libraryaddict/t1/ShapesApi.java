package com.tenjava.entries.libraryaddict.t1;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ShapesApi {
    public static ArrayList<Location> getPointsCircle(Location center, int pointsAmount, double distance) {
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

    public static ArrayList<Location> getBox(Location center, double boxSize) {
        ArrayList<Location> boxPoints = new ArrayList<Location>();
        boxPoints.add(center.clone().add(-boxSize, 0, -boxSize));
        boxPoints.add(center.clone().add(boxSize, 0, -boxSize));
        boxPoints.add(center.clone().add(boxSize, 0, boxSize));
        boxPoints.add(center.clone().add(-boxSize, 0, boxSize));
        return boxPoints;
    }

    public static ArrayList<Location> getLines(Location startingPoint, Location endingPoint, double distanceBetweenParticles) {
        return getLines(startingPoint, endingPoint,
                (int) Math.ceil(startingPoint.distance(endingPoint) / distanceBetweenParticles));
    }

    public static ArrayList<Location> getLines(Location startingPoint, Location endingPoint, int amountOfPoints) {
        double x = startingPoint.getX() - endingPoint.getX();
        double y = startingPoint.getY() - endingPoint.getY();
        double z = startingPoint.getZ() - endingPoint.getZ();
        // Calc (num/total)*speed
        double sumTotal = startingPoint.distance(endingPoint) / (double) amountOfPoints;
        // I want to feed in 3 numbers. Have them normalized to distanceBetweenPoints when added together.
        // Which means I want to divide them individially. By using a sum total;.
        double total = Math.abs(x) + Math.abs(y) + Math.abs(z);
        Vector vector = new Vector((x / total) * sumTotal, (y / total) * sumTotal, (z / total) * sumTotal);
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = 0; i < amountOfPoints; i++) {
            startingPoint.subtract(vector);
            locs.add(startingPoint.clone());
        }
        return locs;
    }

}
