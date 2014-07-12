package com.tenjava.entries.libraryaddict.t1.apis;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ShapesApi {
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
        startingPoint = startingPoint.clone();
        Vector vector = endingPoint.toVector().subtract(startingPoint.toVector());
        vector.normalize();
        vector.multiply(startingPoint.distance(endingPoint) / (double) amountOfPoints);
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = 0; i < amountOfPoints; i++) {
            locs.add(startingPoint.add(vector).clone());
        }
        return locs;
    }

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

}
