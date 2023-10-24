package dev.slne.event.gamemode.gamemodes.buildGamemode.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Objective;

import dev.slne.event.Main;
import dev.slne.event.gamemode.GameModeListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.BuildGameMode;

public class BuildDeathListener implements GameModeListener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        BuildGameMode buildGameMode = (BuildGameMode) Main.getInstance().getGameManager()
                .getGameMode("gamemode_build");

        if (buildGameMode == null) {
            return;
        }

        if (!buildGameMode.isStarted()) {
            return;
        }

        Objective scoreObjective = buildGameMode.getScoreObjective();
        Player player = event.getPlayer();
        int percentage = 25;
        int currentScore = scoreObjective.getScore(player.getName()).getScore();
        int newScore = currentScore - currentScore * percentage / 100;
        scoreObjective.getScore(player).setScore(newScore);
    }
}
