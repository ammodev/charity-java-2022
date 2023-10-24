package dev.slne.event.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import dev.slne.event.Main;
import eu.decentsoftware.holograms.api.nms.PacketListener;

public class PingUtils extends PacketListener implements Listener {

    private static Map<UUID, List<Long>> keepAliveTime;

    public void ignite() {
        keepAliveTime = Collections.synchronizedMap(new HashMap<UUID, List<Long>>());
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(),
                PacketType.Play.Server.KEEP_ALIVE, PacketType.Play.Client.KEEP_ALIVE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                packetReceiving(event);
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                packetSending(event);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent event) {
        if (keepAliveTime.containsKey(event.getPlayer().getUniqueId())) {
            keepAliveTime.remove(event.getPlayer().getUniqueId());
        }
    }

    public static long getPing(Player player) {
        return getPing(player.getUniqueId());
    }

    public static long getPing(UUID uuid) {
        if (keepAliveTime.containsKey(uuid)) {
            List<Long> keepAliveTime = PingUtils.keepAliveTime.get(uuid);

            if (keepAliveTime.size() > 0) {
                return keepAliveTime.get(keepAliveTime.size() - 1);
            }
        }

        return -1;
    }

    public static Map<UUID, List<Long>> getPingTimes() {
        return keepAliveTime;
    }

    private void packetSending(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Server.KEEP_ALIVE)) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();

            long currentTime = System.currentTimeMillis();
            List<Long> timeData = keepAliveTime.get(uuid);

            if (timeData == null) {
                timeData = new ArrayList<Long>(2);
                timeData.add(0L);
                timeData.add(0L);
            }

            timeData.set(0, currentTime);
            keepAliveTime.put(uuid, timeData);
        }
    }

    private void packetReceiving(PacketEvent event) {
        if (event.getPacketType().equals(PacketType.Play.Client.KEEP_ALIVE)) {
            long currentTime = System.currentTimeMillis();
            final Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();

            long pingTime = 0L;
            List<Long> timeData = keepAliveTime.get(uuid);

            if (timeData == null) {
                timeData = new ArrayList<Long>(2);
                timeData.add(0L);
                timeData.add(0L);
            } else {
                pingTime = currentTime - timeData.get(0);
                timeData.set(1, pingTime);
            }

            keepAliveTime.put(uuid, timeData);
        }
    }
}
