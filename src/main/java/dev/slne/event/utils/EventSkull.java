package dev.slne.event.utils;

import java.net.URL;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.profile.PlayerTextures;

import com.destroystokyo.paper.profile.PlayerProfile;

public class EventSkull {
    private Block block;
    private BlockState blockState;
    private Skull skull;

    private PlayerProfile playerProfile;
    private PlayerTextures playerTextures;

    public EventSkull(Block block, BlockState blockState, Skull skull, PlayerProfile playerProfile,
            PlayerTextures playerTextures) {
        this.block = block;
        this.blockState = blockState;
        this.skull = skull;
        this.playerProfile = playerProfile;
        this.playerTextures = playerTextures;
    }

    public URL getSkinUrl() {
        return this.playerTextures.getSkin();
    }

    public String getSkinTexture() {
        String skinUrlString = this.playerTextures.getSkin().toString();
        String[] skinUrlStringSplit = skinUrlString.split("/");
        return skinUrlStringSplit[skinUrlStringSplit.length - 1];
    }

    public Block getBlock() {
        return block;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public Skull getSkull() {
        return skull;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public PlayerTextures getPlayerTextures() {
        return playerTextures;
    }
}