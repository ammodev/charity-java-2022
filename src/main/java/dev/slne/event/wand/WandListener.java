package dev.slne.event.wand;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.slne.event.Main;

public class WandListener implements Listener {

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        if (event.getItem() == null) {
            return;
        }

        ItemStack itemStack = event.getItem();
        if (!itemStack.hasItemMeta()) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.getPersistentDataContainer() == null) {
            return;
        }

        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (!persistentDataContainer.has(Wand.WAND_KEY, PersistentDataType.STRING)) {
            return;
        }

        String wandType = persistentDataContainer.get(Wand.WAND_KEY, PersistentDataType.STRING);
        Player player = event.getPlayer();

        if (player.hasCooldown(Material.STICK)) {
            return;
        }

        // player.setCooldown(Material.STICK, 5 * 20);

        for (Wand wand : Main.getInstance().getWandManager().getWands()) {
            if (wand.getWandName().toLowerCase().equals(wandType.toLowerCase())) {
                wand.castWand(player);
                return;
            }
        }
    }

}
