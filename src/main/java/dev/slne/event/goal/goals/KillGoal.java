package dev.slne.event.goal.goals;

import org.bukkit.entity.Player;

import dev.slne.event.goal.Goal;
import dev.slne.event.goal.nonGoals.NonGoal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class KillGoal extends Goal {

    public KillGoal() {
        super("kill", Component.text("Kill", NamedTextColor.RED, TextDecoration.BOLD),
                Component.text("Avada Kedavra", NamedTextColor.RED, TextDecoration.BOLD), "Tötet den Spieler", 150);
    }

    @Override
    public void executeGoal() {
        for (Player player : NonGoal.getTargetedPlayers()) {
            player.setHealth(0);
        }
    }

}
