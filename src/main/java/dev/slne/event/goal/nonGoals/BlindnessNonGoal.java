package dev.slne.event.goal.nonGoals;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BlindnessNonGoal extends NonGoal {

    public BlindnessNonGoal() {
        super(Component.text("Nox", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        for (Player player : getTargetedPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 4 * 20, 10, false, false, false));
        }
    }

}
