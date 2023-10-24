package dev.slne.event.goal.goals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.slne.event.Main;
import dev.slne.event.goal.Goal;
import dev.slne.event.goal.nonGoals.NonGoal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class RewindGoal extends Goal implements Listener {

    private final int REWIND_SECONDS = 15;
    private final int REWIND_LOCATION_EVERY_TICKS = 1;

    private List<PlayerLocations> locations;
    private BukkitTask task;

    public RewindGoal() {
        super("rewind", Component.text("Zeit Zurückspulen", NamedTextColor.AQUA, TextDecoration.BOLD),
                null, "Spult die Zeit um 15 Sekunden zurück", 500);

        this.locations = new ArrayList<>();
        this.startTask();

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        PlayerLocations playerLocation = this.locations.stream().filter(playerLocationObject -> {
            return playerLocationObject.getPlayer().equals(event.getPlayer());
        }).findFirst().orElse(null);

        if (event.getCause().equals(TeleportCause.COMMAND) || event.getCause().equals(TeleportCause.PLUGIN)
                || event.getCause().equals(TeleportCause.SPECTATE) || event.getCause().equals(TeleportCause.UNKNOWN)) {
            if (playerLocation != null) {
                playerLocation.playerLocations.clear();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerLocations playerLocation = this.locations.stream().filter(playerLocationObject -> {
            return playerLocationObject.getPlayer().equals(event.getPlayer());
        }).findFirst().orElse(null);

        if (playerLocation != null) {
            this.locations.remove(playerLocation);
        }
    }

    @Override
    public void executeGoal() {
        if (this.task != null && !this.task.isCancelled()) {
            this.task.cancel();
        }

        new BukkitRunnable() {
            public void run() {
                int totalSize = 0;

                for (PlayerLocations playerLocation : locations) {
                    playerLocation.rewind();
                    totalSize += playerLocation.getPlayerLocations().size();
                }

                if (totalSize == 0) {
                    locations.clear();
                    startTask();

                    this.cancel();
                    return;
                }
            };
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public PlayerLocations getPlayerLocations(Player player) {
        return this.locations.stream().filter(playerLocations -> playerLocations.getPlayer().equals(player)).findFirst()
                .orElseGet(() -> {
                    PlayerLocations playerLocations = new PlayerLocations(player);
                    this.locations.add(playerLocations);
                    return playerLocations;
                });
    }

    public void startTask() {
        if (this.task != null && !this.task.isCancelled()) {
            this.task.cancel();
        }

        this.task = new BukkitRunnable() {
            public void run() {
                for (Player player : NonGoal.getTargetedPlayers()) {
                    PlayerLocations playerLocations = getPlayerLocations(player);
                    playerLocations.addLocation(player.getLocation());

                    int currentSize = playerLocations.getPlayerLocations().size();
                    int maxSize = REWIND_SECONDS * 20;

                    if (currentSize >= maxSize) {
                        playerLocations.removeFirstLocation();
                    }
                }
            };
        }.runTaskTimer(Main.getInstance(), 0, REWIND_LOCATION_EVERY_TICKS);
    }

    public List<PlayerLocations> getLocations() {
        return locations;
    }

    private static class PlayerLocations {
        private Player player;
        private List<Location> playerLocations;

        public PlayerLocations(Player player) {
            this.player = player;
            this.playerLocations = new ArrayList<>();
        }

        public Player getPlayer() {
            return player;
        }

        public List<Location> getPlayerLocations() {
            return playerLocations;
        }

        public void rewind() {
            if (this.playerLocations.size() == 0) {
                return;
            }

            List<Location> rewindLocations = new ArrayList<>(this.playerLocations);
            Location location = rewindLocations.get(rewindLocations.size() - 1);

            if (location == null) {
                return;
            }

            this.player.teleport(location, TeleportCause.ENDER_PEARL);

            int size = this.playerLocations.size() - 1;
            this.playerLocations.remove(size);
        }

        public void addLocation(Location location) {
            this.playerLocations.add(location);
        }

        public Location removeFirstLocation() {
            if (this.playerLocations.size() == 0) {
                return null;
            }

            return this.playerLocations.remove(0);
        }
    }

}
