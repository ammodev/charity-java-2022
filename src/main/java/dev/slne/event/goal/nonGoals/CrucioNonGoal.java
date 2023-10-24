package dev.slne.event.goal.nonGoals;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class CrucioNonGoal extends NonGoal {

    public CrucioNonGoal() {
        super(Component.text("Crucio", NamedTextColor.RED, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        for (Player player : getTargetedPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 1, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 14 * 20, 1, false, false, false));
        }
    }

}
