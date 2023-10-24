package dev.slne.event.goal.nonGoals;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class LevitationNonGoal extends NonGoal {

    public LevitationNonGoal() {
        super(Component.text("Wingardium Leviosa", NamedTextColor.GOLD, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        for (Player player : getTargetedPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 4 * 20, 2, false, false, false));
        }
    }

}
