package dev.slne.event.wand.wands;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import dev.slne.event.Main;
import dev.slne.event.wand.Wand;
import dev.slne.event.wand.WandUtils;

public class ShriekWand extends Wand {

    public ShriekWand() {
        super(Wand.SHRIEK_WAND, WandUtils.getShriekWandItemStack());
    }

    @Override
    public void castWand(Player caster) {
        new BukkitRunnable() {
            private double ticks = 0;

            public void run() {
                Location eyeLocation = caster.getEyeLocation();
                Vector direction = eyeLocation.getDirection().normalize();

                ticks = ticks + 1;

                double x = direction.getX() * ticks;
                double y = direction.getY() * ticks;
                double z = direction.getZ() * ticks;

                eyeLocation.add(x, y, z);

                List<LivingEntity> collidesWithEntityList = eyeLocation.getWorld()
                        .getNearbyLivingEntities(eyeLocation, 0.5, 1, 0.5).stream()
                        .filter(entity -> {
                            return entity != caster;
                        }).toList();

                boolean collidesWithEntity = collidesWithEntityList.size() > 0;

                if ((eyeLocation.getBlock() != null && !eyeLocation.getBlock().getType().equals(Material.AIR))
                        || collidesWithEntity) {
                    Particle.SONIC_BOOM.builder().location(eyeLocation).receivers(50)
                            .count(3).spawn();
                    eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_WARDEN_ROAR, 2f, 1f);

                    if (collidesWithEntity) {
                        for (LivingEntity entity : collidesWithEntityList) {
                            entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1 * 20, 2, false, false,
                                    false));
                        }
                    }

                    this.cancel();
                    return;
                } else {
                    Particle.REDSTONE.builder().location(eyeLocation).color(Color.AQUA).receivers(50)
                            .count(5).spawn();
                    eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_WARDEN_ATTACK_IMPACT, 1f, 1f);
                    eyeLocation.subtract(x, y, z);
                }

                if (ticks > 20) {
                    eyeLocation.add(x, y, z);
                    Particle.SONIC_BOOM.builder().location(eyeLocation).receivers(50)
                            .count(3).spawn();
                    eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_WARDEN_ROAR, 2f, 1f);
                    eyeLocation.subtract(x, y, z);

                    this.cancel();
                }
            };
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

}
