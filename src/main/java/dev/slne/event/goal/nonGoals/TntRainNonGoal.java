package dev.slne.event.goal.nonGoals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.slne.event.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class TntRainNonGoal extends NonGoal {

    public TntRainNonGoal() {
        super(Component.text("Bombarda", NamedTextColor.RED, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        int range = 4;

        List<Location> locations = new ArrayList<>();
        for (Player player : getTargetedPlayers()) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    for (int y = 0; y <= range; y++) {
                        locations.add(player.getLocation().add(x, y, z));
                    }
                }
            }
        }

        new BukkitRunnable() {
            public void run() {
                for (Location location : locations) {
                    location.getWorld().createExplosion(location, 1, false, false);
                    location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location, 0);
                }
            };
        }.runTaskLater(Main.getInstance(), 2 * 20);
    }

}
