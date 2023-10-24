package dev.slne.event.donation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class DonationCommand implements CommandExecutor, TabCompleter {

    public DonationCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!sender.hasPermission("charity.donation")) {
            return true;
        }

        String hastag = UUID.randomUUID().toString();
        double donationAmount = Double.parseDouble(args[0]);

        String[] messageArray = Arrays.copyOfRange(args, 1, args.length);
        String message = String.join(" ", messageArray);

        CharityDonation charityDonation = new CharityDonation(hastag, sender.getName(), donationAmount,
                donationAmount, message, "castcrafter", "thomas-harrypotter",
                Instant.now().getEpochSecond() + "");
        CharityEvent charityEvent = new CharityEvent(charityDonation);
        Bukkit.getPluginManager().callEvent(charityEvent);

        TextComponent.Builder builder = Component.text();
        builder.append(MessageManager.getPrefix());
        builder.append(Component.text("Du hast erfolgreich eine Test-Spende von ", MessageManager.SUCCESS));
        builder.append(Component.text(donationAmount + "€", NamedTextColor.GOLD));
        builder.append(Component.text(" abgesendet!", MessageManager.SUCCESS));
        sender.sendMessage(builder.build());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 2) {
            for (String suggestion : Arrays.asList("#levitation", "#blindness", "#freeze", "#burn", "#tnt",
                    "#crucio")) {
                if (args[0].length() == 0 || suggestion.toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(suggestion);
                }
            }
        }

        return suggestions;
    }
}
