package dev.slne.event.goal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.slne.event.Main;
import dev.slne.event.donation.DonationIgnitor;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class GoalCommand implements TabCompleter, CommandExecutor {

    public GoalCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {

        if (!sender.hasPermission("charity.test-goal")) {
            return true;
        }

        DonationIgnitor ignitor = (DonationIgnitor) Main.getInstance().getIgnitor(DonationIgnitor.class);

        if (ignitor == null) {
            sender.sendMessage(
                    MessageManager.getPrefix().append(Component.text("Donations sind aktuell ausgeschaltet.")));
            return true;
        }

        String goalName = args[0];
        Goal goal = ignitor.getGoalManager().getGoal(goalName);

        if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("disable")) {
            if (args[1].equalsIgnoreCase("enable")) {
                goal.setEnabled(true);
            } else if (args[1].equalsIgnoreCase("disable")) {
                goal.setEnabled(false);
            }

            TextComponent.Builder builder = Component.text();
            builder.append(MessageManager.getPrefix());
            builder.append(Component.text(goal.getName(), NamedTextColor.GOLD));
            builder.append(Component.text(" ist nun ", NamedTextColor.GRAY));

            if (goal.isEnabled()) {
                builder.append(Component.text("aktiviert", MessageManager.SUCCESS));
            } else {
                builder.append(Component.text("deaktiviert", MessageManager.ERROR));
            }

            builder.append(Component.text("!", NamedTextColor.GRAY));
            Bukkit.broadcast(builder.build(), "charity.goal.toggle");
        } else {
            int amount = Integer.parseInt(args[1]);
            goal.addAmount(amount);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command,
            String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        DonationIgnitor ignitor = (DonationIgnitor) Main.getInstance().getIgnitor(DonationIgnitor.class);

        if (ignitor == null) {
            return suggestions;
        }

        if (args.length == 1) {
            for (Goal goal : ignitor.getGoalManager().getGoals()) {
                if (args[0].length() == 0 || goal.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(goal.getName());
                }
            }
        } else if (args.length == 2) {
            for (String string : Arrays.asList("enable", "disable")) {
                if (args[1].length() == 0 || string.toLowerCase().startsWith(args[1].toLowerCase())) {
                    suggestions.add(string);
                }
            }
        }

        return suggestions;
    }
}
