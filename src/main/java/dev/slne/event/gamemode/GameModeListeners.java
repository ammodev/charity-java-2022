package dev.slne.event.gamemode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.slne.event.Main;
import dev.slne.event.utils.SpawnLocations;

public class GameModeListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        EventGameMode activeGameMode = Main.getInstance().getGameManager().getCurrentGameMode();
        Player player = event.getPlayer();

        if (activeGameMode != null) {
            player.teleportAsync(activeGameMode.getLobbyLocation());
        } else {
            player.teleportAsync(SpawnLocations.MAP_SPAWN);
        }
    }

}
