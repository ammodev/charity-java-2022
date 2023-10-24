package dev.slne.event.gamemode.gamemodes.buildGamemode.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import dev.slne.event.Main;
import dev.slne.event.gamemode.GameModeListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.BuildGameMode;

public class BuildPickupListener implements GameModeListener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        BuildGameMode buildGameMode = (BuildGameMode) Main.getInstance().getGameManager()
                .getGameMode("gamemode_build");

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (buildGameMode == null) {
            return;
        }

        if (!buildGameMode.isStarted()) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (event.getItem().getItemStack().getType().equals(Material.STONE)) {
            Objective scoreObjective = buildGameMode.getScoreObjective();
            Score playerScore = scoreObjective.getScore(player);
            playerScore.setScore(playerScore.getScore() + event.getItem().getItemStack().getAmount());
        }
    }
}
