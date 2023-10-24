package dev.slne.event.credits;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

public class Credits {
    private static Credits instance;

    private final ICredits nms;

    private Credits() {
        this.nms = (ICredits) new ModernCredits();
    }

    public static void init() {
        getInstance();
    }

    private static Credits getInstance() {
        if (instance == null)
            instance = new Credits();
        return instance;
    }

    public static void showCredits(Player player) {
        (getInstance()).nms.showCredits(player);
    }

    public static String getMcVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }

    public static boolean sendPacket(Player player, PacketContainer packet) {

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        return true;
    }
}
