package dev.slne.event.wand.wands;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import dev.slne.event.Main;
import dev.slne.event.wand.Wand;
import dev.slne.event.wand.WandUtils;

public class WololoWand extends Wand {

    public WololoWand() {
        super(Wand.WOLOLO_WAND, WandUtils.getWololoWandItemStack());
    }

    @Override
    public void castWand(Player caster) {
        List<LivingEntity> nearEntities = caster.getWorld().getNearbyLivingEntities(caster.getLocation(), 10, 5, 10)
                .stream().filter(entity -> {
                    if (entity instanceof Player player) {
                        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                            return false;
                        }
                    }

                    if (entity instanceof ArmorStand) {
                        return false;
                    }

                    return entity != caster;
                }).toList();

        for (LivingEntity entity : nearEntities) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 10, false, false, false));
                player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 0);
            }
        }

        new BukkitRunnable() {
            private int ticks = 0;
            private Sound witchSound = Sound.ENTITY_WITCH_CELEBRATE;
            private Sound wololoSound = Sound.ENTITY_EVOKER_PREPARE_WOLOLO;

            public void run() {
                for (LivingEntity entity : nearEntities) {
                    if (ticks == 0) {
                        caster.getWorld().playSound(entity.getLocation(), witchSound, 1f, 2f);
                    } else if (ticks == 6) {
                        caster.getWorld().playSound(entity.getLocation(), witchSound, 1f, 2f);
                    } else if (ticks == 10) {
                        caster.getWorld().playSound(entity.getLocation(), wololoSound, 1f, 2f);
                        this.cancel();
                    }
                }

                ticks = ticks + 1;
            };
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

}
