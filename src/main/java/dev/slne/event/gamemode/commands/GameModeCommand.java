package dev.slne.event.gamemode.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.slne.event.Main;
import dev.slne.event.gamemode.EventGameMode;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;

public class GameModeCommand implements CommandExecutor, TabCompleter {

    public GameModeCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);

        command.setPermission("gamemode.command");
        command.setAliases(Arrays.asList("gm"));
        command.setDescription("The gamemode command");
        command.setUsage("/game <gamemode>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!(args.length >= 1)) {
            return true;
        }

        if (!sender.hasPermission("charity.gamemode")) {
            return true;
        }

        String gameMode = args[0];
        EventGameMode eventGameMode = Main.getInstance().getGameManager().getGameMode(gameMode);

        if (args[0].equalsIgnoreCase("clear")) {
            Main.getInstance().getGameManager().clearGamemode();
            sender.sendMessage(MessageManager.getPrefix().append(
                    Component.text("Der Spielmodus wurde beendet!", MessageManager.SUCCESS)));
            return true;
        }

        if (eventGameMode == null) {
            sender.sendMessage(MessageManager.getPrefix().append(
                    Component.text("Der Spielmodus " + gameMode + " existiert nicht!", MessageManager.ERROR)));
            return true;
        }

        if (args.length >= 2 && args[1].equalsIgnoreCase("start")) {
            Main.getInstance().getGameManager().loadGamemode(gameMode);
            Main.getInstance().getGameManager().enableGamemode(gameMode);
            Main.getInstance().getGameManager().startGamemode(gameMode);
            sender.sendMessage(MessageManager.getPrefix().append(
                    Component.text("Der Spielmodus " + gameMode + " wurde gestartet!", MessageManager.SUCCESS)));
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String label, String[] args) {
        List<String> suggestions = new ArrayList<String>();

        if (args.length == 1) {
            ArrayList<String> gameModes = new ArrayList<String>();
            Main.getInstance().getGameManager().getGameModes().forEach(gameMode -> {
                gameModes.add(gameMode.getName());
            });

            gameModes.add("clear");

            for (String mode : gameModes) {
                if (args[0].length() == 0 || mode.toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(mode);
                }
            }
        } else if (args.length == 2) {
            for (String mode : Arrays.asList("start", "pause")) {
                if (args[1].length() == 0 || mode.toLowerCase().startsWith(args[1].toLowerCase())) {
                    suggestions.add(mode);
                }
            }
        }

        return suggestions;
    }

}
