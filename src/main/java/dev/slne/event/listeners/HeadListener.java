package dev.slne.event.listeners;

import java.net.URL;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.profile.PlayerTextures;

import com.destroystokyo.paper.profile.PlayerProfile;

import dev.slne.event.utils.EventSkull;

public class HeadListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getSourceBlock();

        EventSkull eventSkull = checkBlock(block);
        if (eventSkull == null) {
            return;
        }

        String skinTexture = eventSkull.getSkinTexture();

        if (skinTexture.equals("abcdefg")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPhysics(BlockFromToEvent event) {
        EventSkull eventSkull = checkBlock(event.getBlock());

        if (eventSkull == null) {
            return;
        }

        String skinTexture = eventSkull.getSkinTexture();

        if (skinTexture.equals("abcdefg")) {
            event.setCancelled(true);
        }
    }

    private EventSkull checkBlock(Block block) {
        if (block == null) {
            return null;
        }

        BlockState blockState = block.getState();
        if (blockState == null) {
            return null;
        }

        if (blockState instanceof Skull skull) {
            PlayerProfile playerProfile = skull.getPlayerProfile();
            if (playerProfile == null) {
                return null;
            }

            PlayerTextures playerTextures = playerProfile.getTextures();
            if (playerTextures == null) {
                return null;
            }

            URL skinUrl = playerTextures.getSkin();
            if (skinUrl == null) {
                return null;
            }
            return new EventSkull(block, blockState, skull, playerProfile, playerTextures);
        }

        return null;
    }

}
