package dev.slne.event.goal.nonGoals;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BurnNonGoal extends NonGoal {

    public BurnNonGoal() {
        super(Component.text("Incendio", NamedTextColor.RED, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        for (Player player : getTargetedPlayers()) {
            player.setFireTicks(5 * 20);
        }
    }

}
