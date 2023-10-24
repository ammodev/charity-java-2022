package dev.slne.event.goal.goals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import dev.slne.event.Main;
import dev.slne.event.goal.Goal;
import dev.slne.event.goal.nonGoals.NonGoal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class JumpscareGoal extends Goal {

    public JumpscareGoal() {
        super("jumpscare", Component.text("Jumpscare", NamedTextColor.RED, TextDecoration.BOLD), null,
                "Ein Jumpscare mittels Pumpkin und/oder Totem", 100);
    }

    @Override
    public void executeGoal() {
        Random random = new Random();
        int randomInt = random.nextInt(2);

        boolean totem = false;
        Map<Player, ItemStack> itemStacks = new HashMap<>();

        for (Player player : NonGoal.getTargetedPlayers()) {
            if (randomInt == 0) {
                ItemStack offHandItem = player.getInventory().getItemInOffHand();
                ItemStack totemOfUndying = new ItemStack(Material.TOTEM_OF_UNDYING);

                player.getInventory().setItemInOffHand(totemOfUndying);
                player.setHealth(0.5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 1, 255));

                itemStacks.put(player, offHandItem);

                totem = true;
            } else if (randomInt == 1) {
                player.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
                player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 0);
            }

            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 2f, 2f);
            player.addPotionEffect(
                    new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 10, false, false, false));
        }

        if (totem) {
            new BukkitRunnable() {
                public void run() {
                    for (Map.Entry<Player, ItemStack> entry : itemStacks.entrySet()) {
                        Player player = entry.getKey();

                        player.getInventory().setItemInOffHand(entry.getValue());
                        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
                        player.getActivePotionEffects().clear();
                    }
                };
            }.runTaskLater(Main.getInstance(), 5);
        }
    }

}
