package dev.slne.event.wand.wands;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev.slne.event.Main;
import dev.slne.event.wand.Wand;
import dev.slne.event.wand.WandUtils;

public class ProtectorWand extends Wand {

    public ProtectorWand() {
        super("protector_wand", WandUtils.getEndProtectorWandItemStack());
    }

    @Override
    public void castWand(Player caster) {
        new BukkitRunnable() {
            private double phi = 0;
            private int i = 0;
            private boolean first = true;

            public void run() {
                Location casterLocation = caster.getLocation();

                phi += Math.PI / 10;

                if (first) {
                    casterLocation.getWorld().playSound(casterLocation, Sound.ENTITY_BLAZE_SHOOT, .25f, 0.1f);
                    first = false;
                }

                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 20) {
                    double r = 1;

                    double x = r * Math.cos(theta) * Math.sin(phi);
                    double y = r * Math.cos(phi) + 0.5;
                    double z = r * Math.sin(theta) * Math.sin(phi);

                    casterLocation.add(x, y, z);

                    if (i % 256 == 0) {
                        casterLocation.getWorld().playSound(casterLocation, Sound.ENTITY_BLAZE_BURN, .25f, 0.01f);
                    }

                    spawnParticle(casterLocation, x, y, z, i);
                    casterLocation.subtract(x, y, z);

                    i++;
                }

                if (phi > Math.PI * 10) {
                    this.cancel();
                }
            };
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    private void spawnParticle(Location location, double x, double y, double z, int i) {
        location.add(x, y, z);
        location.getWorld().spawnParticle(i % 2 == 0 ? Particle.FLAME : Particle.SOUL_FIRE_FLAME, location, 0);
        location.subtract(x, y, z);
    }

}
