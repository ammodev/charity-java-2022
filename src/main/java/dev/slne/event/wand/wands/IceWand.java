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

public class IceWand extends Wand {

    public IceWand() {
        super(Wand.ICE_WAND, WandUtils.getIceWandItemStack());
    }

    @Override
    public void castWand(Player caster) {
        new BukkitRunnable() {
            private Sound sound = Sound.BLOCK_GLASS_BREAK;
            private double ticks = 0;
            private boolean loaded = false;

            Location eyeLocation = caster.getEyeLocation();
            Vector direction = eyeLocation.getDirection().normalize();

            int initialMultiplier = 2;

            public void run() {
                if (!loaded) {

                    eyeLocation.add(direction.getX() * initialMultiplier, direction.getY() * initialMultiplier,
                            direction.getZ() * initialMultiplier);

                    if (ticks == 10) {
                        Particle.EXPLOSION_NORMAL.builder().location(eyeLocation).receivers(50)
                                .count(5).spawn();
                        loaded = true;
                        ticks = initialMultiplier;
                    } else {
                        Particle.REDSTONE.builder().location(eyeLocation).color(Color.WHITE).receivers(50)
                                .count(1).spawn();
                    }

                    eyeLocation.getWorld().playSound(eyeLocation, sound, .25f, 1f);
                    eyeLocation.subtract(direction.getX() * initialMultiplier, direction.getY() * initialMultiplier,
                            direction.getZ() * initialMultiplier);
                }

                if (loaded) {
                    double x = direction.getX() * ticks;
                    double y = direction.getY() * ticks;
                    double z = direction.getZ() * ticks;

                    if (ticks <= 15 + initialMultiplier) {
                        eyeLocation.add(x, y, z);
                        List<LivingEntity> collidesWithEntityList = eyeLocation.getWorld()
                                .getNearbyLivingEntities(eyeLocation, 0.5, 1, 0.5).stream()
                                .filter(entity -> {
                                    return entity != caster;
                                }).toList();

                        boolean collidesWithEntity = collidesWithEntityList.size() > 0;

                        if ((eyeLocation.getBlock() != null && !eyeLocation.getBlock().getType().equals(Material.AIR))
                                || collidesWithEntity) {
                            Particle.EXPLOSION_LARGE.builder().location(eyeLocation).receivers(50)
                                    .count(2).spawn();
                            eyeLocation.getWorld().playSound(eyeLocation, sound, 1f, 1f);

                            if (collidesWithEntity) {
                                for (LivingEntity entity : collidesWithEntityList) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1 * 20, 2, false,
                                            false, false));
                                }
                            }

                            this.cancel();
                            return;
                        } else {
                            Particle.REDSTONE.builder().location(eyeLocation).color(Color.WHITE).receivers(50)
                                    .count(1).spawn();
                            eyeLocation.getWorld().playSound(eyeLocation, sound, 1f, 1f);
                        }

                        eyeLocation.subtract(x, y, z);
                    } else {
                        eyeLocation.add(x, y, z);
                        Particle.EXPLOSION_LARGE.builder().location(eyeLocation).receivers(50)
                                .count(2).spawn();
                        eyeLocation.getWorld().playSound(eyeLocation, sound, 1f, 1f);
                        eyeLocation.subtract(x, y, z);
                        this.cancel();
                    }
                }

                ticks = ticks + 1;
            };
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

}
