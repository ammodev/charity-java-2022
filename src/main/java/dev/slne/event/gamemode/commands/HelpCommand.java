package dev.slne.event.gamemode.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import dev.slne.event.Main;
import dev.slne.event.gamemode.EventGameMode;

public class HelpCommand implements CommandExecutor {

    public HelpCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setAliases(Arrays.asList("hp"));
        command.setDescription("Stop. Get some help.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        EventGameMode currentGameMode = Main.getInstance().getGameManager().getCurrentGameMode();

        if (currentGameMode == null) {
            return true;
        }

        player.openBook(currentGameMode.getDescriptionBookImplementation());

        return true;
    }

}
