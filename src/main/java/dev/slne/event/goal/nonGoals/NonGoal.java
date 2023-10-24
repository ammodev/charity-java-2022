package dev.slne.event.goal.nonGoals;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;

public abstract class NonGoal {

    private boolean enabled;
    private Component spellName;

    public NonGoal(Component spellName) {
        this.spellName = spellName;

        this.enabled = true;
    }

    public Component getSpellName() {
        return spellName;
    }

    public abstract void execute();

    public static List<Player> getTargetedPlayers() {
        return Bukkit.getOnlinePlayers().stream().filter(player -> {
            List<String> playerNames = Arrays.asList("CastCrafter");
            return playerNames.contains(player.getName());
        }).map(player -> (Player) player).toList();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
