package dev.slne.event.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.setCancelled(true);

        Player source = event.getPlayer();

        LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().character('&').extractUrls()
                .hexColors().build();
        Component message = serializer.deserialize(serializer.serialize(event.message()));

        TextComponent.Builder builder = Component.text();
        builder.append(source.displayName());
        builder.append(Component.space());
        builder.append(Component.text(">>", NamedTextColor.DARK_GRAY));
        builder.append(Component.space());
        builder.append(message.colorIfAbsent(NamedTextColor.WHITE));

        Bukkit.broadcast(builder.build());
    }

}
