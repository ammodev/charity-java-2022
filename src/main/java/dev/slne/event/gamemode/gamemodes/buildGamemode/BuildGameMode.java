package dev.slne.event.gamemode.gamemodes.buildGamemode;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import dev.slne.event.gamemode.EventGameMode;
import dev.slne.event.gamemode.gamemodes.buildGamemode.book.BuildBookImpl;
import dev.slne.event.gamemode.gamemodes.buildGamemode.listeners.BuildDeathListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.listeners.BuildPickupListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.listeners.BuildPlayerAttributeListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.listeners.BuildRespawnListener;
import dev.slne.event.utils.SpawnLocations;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BuildGameMode extends EventGameMode {

    private Scoreboard scoreboard;
    private Objective scoreObjective;

    public BuildGameMode() {
        super("gamemode_build", Component.text("Block Sammler", NamedTextColor.AQUA, TextDecoration.BOLD), 2);
    }

    @Override
    public void onLoad() {
        addListener(new BuildDeathListener());
        addListener(new BuildPickupListener());
        addListener(new BuildPlayerAttributeListener());
        addListener(new BuildRespawnListener());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnable() {
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if (this.scoreboard.getObjective("coins_score") != null) {
            this.scoreboard.getObjective("coins_score").unregister();
        }

        this.scoreObjective = this.scoreboard.registerNewObjective("coins_score", Criteria.DUMMY,
                (Component) Component.text("coins_score"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            // player.teleportAsync(getLobbyLocation());
        }
    }

    @Override
    public void onDisable() {
        if (this.scoreboard.getObjective("coins_score") != null) {
            this.scoreboard.getObjective("coins_score").unregister();
        }

        this.scoreObjective = null;
    }

    @Override
    public Location getLobbyLocation() {
        ArrayList<Location> spawns = SpawnLocations.PICKUP_START_LOCATIONS;

        return spawns.get((new Random()).nextInt(spawns.size()));
    }

    @Override
    public Book getDescriptionBook() {
        return new BuildBookImpl().buildBook();
    }

    public Objective getScoreObjective() {
        return scoreObjective;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
