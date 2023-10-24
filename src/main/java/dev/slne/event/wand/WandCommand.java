package dev.slne.event.wand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.slne.event.Main;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class WandCommand implements CommandExecutor, TabCompleter {

    public WandCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        String wandName = args[0].toLowerCase();
        Player player = (Player) sender;

        if (!player.hasPermission("charity.wand")) {
            return true;
        }

        Wand wand = Main.getInstance().getWandManager().getWands().stream()
                .filter(w -> w.getWandName().toLowerCase().equals(wandName))
                .findFirst().orElse(null);

        if (wand == null) {
            player.sendMessage(MessageManager.getPrefix()
                    .append(Component.text("Dieser Zauberstab existiert nicht.", MessageManager.ERROR)));
            return true;
        }

        player.getInventory().addItem(wand.getWandItemStack());
        player.sendMessage(
                MessageManager.getPrefix().append(Component.text("Du hast den Zauberstab ", MessageManager.SUCCESS))
                        .append(Component.text("\"" + wand.getWandName() + "\"", NamedTextColor.GOLD))
                        .append(Component.text(" erhalten.", MessageManager.SUCCESS)));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Wand wand : Main.getInstance().getWandManager().getWands()) {
                if (args[0].length() == 0 || wand.getWandName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(wand.getWandName());
                }
            }
        }

        return suggestions;
    }
}
