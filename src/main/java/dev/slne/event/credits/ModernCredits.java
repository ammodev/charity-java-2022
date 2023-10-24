package dev.slne.event.credits;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import dev.slne.event.Main;

public class ModernCredits implements ICredits {
    static Field field_spectatorMode = null;

    static {
        try {
            field_spectatorMode = PacketType.Play.Server.GAME_STATE_CHANGE.getPacketClass().getDeclaredField("e");
        } catch (NoSuchFieldException e) {
            Main.getInstance().getLogger()
                    .severe("Failed to initialize the credits. This server version is not compatible");
            e.printStackTrace();
        }
    }

    public void showCredits(Player player) {
        if (field_spectatorMode == null) {
            Main.getInstance().getLogger()
                    .severe("Failed to show credits screen to player. This server version is not compatible.");
            return;
        }
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
        try {
            Object spectatorMode = field_spectatorMode.get(null);
            packet.getModifier().write(0, spectatorMode);
            packet.getFloat().write(0, Float.valueOf(1.0F));
            Credits.sendPacket(player, packet);
        } catch (IllegalAccessException e) {
            Main.getInstance().getLogger()
                    .severe("Failed to show credits screen to player:");
            e.printStackTrace();
        }
    }
}