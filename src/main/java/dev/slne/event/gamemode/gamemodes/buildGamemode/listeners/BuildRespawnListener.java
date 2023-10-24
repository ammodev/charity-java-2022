package dev.slne.event.gamemode.gamemodes.buildGamemode.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;

import dev.slne.event.Main;
import dev.slne.event.gamemode.GameModeListener;
import dev.slne.event.gamemode.gamemodes.buildGamemode.BuildGameMode;

public class BuildRespawnListener implements GameModeListener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        BuildGameMode buildGameMode = (BuildGameMode) Main.getInstance().getGameManager()
                .getGameMode("gamemode_build");
        event.setRespawnLocation(buildGameMode.getLobbyLocation());
    }

    @EventHandler
    public void onChangeSpawnpoint(PlayerSetSpawnEvent event) {
        BuildGameMode buildGameMode = (BuildGameMode) Main.getInstance().getGameManager()
                .getGameMode("gamemode_build");
        event.setLocation(buildGameMode.getLobbyLocation());
    }
}