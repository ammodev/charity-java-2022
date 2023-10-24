package dev.slne.event.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.slne.event.Main;
import dev.slne.event.user.User;
import dev.slne.event.utils.PlayerListUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class OnlineListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = Main.getInstance().getUser(player);

        if (player.isOp()) {
            TextComponent.Builder builder = Component.text();
            builder.append(Component.text("Leitung", NamedTextColor.GOLD));
            builder.append(Component.text(" | ", NamedTextColor.DARK_GRAY));
            builder.append(Component.text(user.getPlayer().getName(), NamedTextColor.GOLD));

            Main.getInstance().getTeamUtils().getAdminTeam().addPlayer(player);

            Component component = builder.build();
            player.displayName(component);
            player.playerListName(component);
        } else {
            Main.getInstance().getTeamUtils().getPlayerTeam().addPlayer(player);
        }

        player.sendPlayerListHeaderAndFooter(PlayerListUtils.getTabListHeader(player),
                PlayerListUtils.getTabListFooter(player));

        TextComponent.Builder builder = Component.text();
        builder.append(Component.text("[", NamedTextColor.DARK_GRAY));
        builder.append(Component.text("+", NamedTextColor.GREEN));
        builder.append(Component.text("] ", NamedTextColor.DARK_GRAY));
        builder.append(user.getPlayer().displayName());

        event.joinMessage(builder.build());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = Main.getInstance().getUser(player);

        TextComponent.Builder builder = Component.text();
        builder.append(Component.text("[", NamedTextColor.DARK_GRAY));
        builder.append(Component.text("-", NamedTextColor.RED));
        builder.append(Component.text("] ", NamedTextColor.DARK_GRAY));
        builder.append(user.getPlayer().displayName());

        event.quitMessage(builder.build());

        // Finally remove user from the list of users
        Main.getInstance().getUserManager().removeUser(player);
    }

}
