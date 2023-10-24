package dev.slne.event.goal.nonGoals;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class FreezeNonGoal extends NonGoal {

    public FreezeNonGoal() {
        super(Component.text("Petrificus Totalus", NamedTextColor.AQUA, TextDecoration.BOLD));
    }

    @Override
    public void execute() {
        for (Player player : getTargetedPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4 * 20, 10, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 4 * 20, 10, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 4 * 20, 10, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0 * 20, 10, false, false, false));
        }
    }

}