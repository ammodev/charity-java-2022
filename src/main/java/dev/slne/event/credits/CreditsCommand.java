package dev.slne.event.credits;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CreditsCommand implements CommandExecutor {

    public CreditsCommand(PluginCommand command) {
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!sender.hasPermission("charity.credits")) {
            return true;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Credits.showCredits(player);
        }

        sender.sendMessage(
                MessageManager.getPrefix()
                        .append(Component.text("Die Credits werden an alle Spieler gesendet.", NamedTextColor.GRAY)));
        return true;
    }

}
