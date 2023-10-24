package dev.slne.event.gamemode.gamemodes.buildGamemode.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import dev.slne.event.gamemode.GameModeListener;

public class BuildPlayerAttributeListener implements GameModeListener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}