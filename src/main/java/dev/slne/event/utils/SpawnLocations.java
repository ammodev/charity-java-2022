package dev.slne.event.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SpawnLocations {

        private static final World WORLD = Bukkit.getWorld("world");

        // Map
        public static final Location MAP_SPAWN = new Location(WORLD, -245, 33, -25, 90, 0);

        // BuildGameMode
        public static final ArrayList<Location> PICKUP_START_LOCATIONS = new ArrayList<>(
                        Arrays.asList(new Location[] { new Location(WORLD, 0.0D, 77.0D, -54.0D, -90.0F, 0.0F),
                                        new Location(WORLD, 62.0D, 79.0D, 27.0D, 180.0F, 0.0F),
                                        new Location(WORLD, 149.0D, 81.0D, -31.0D, 90.0F, 0.0F),
                                        new Location(WORLD, 80.0D, 90.0D, -119.0D, 0.0F, 0.0F),
                                        new Location(WORLD, 72.0D, 180.0D, -42.0D, -90.0F, 90.0F) }));
}
