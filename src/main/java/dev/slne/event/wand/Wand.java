package dev.slne.event.wand;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.slne.event.Main;

public abstract class Wand {

    public static final NamespacedKey WAND_KEY = new NamespacedKey(Main.getInstance(), "wand");
    public static final NamespacedKey WAND_OWNER = new NamespacedKey(Main.getInstance(), "wand_owner");
    public static final NamespacedKey WAND_OBTAINED = new NamespacedKey(Main.getInstance(), "wand_optained");

    public static final String WOLOLO_WAND = "wololo_wand";
    public static final String ICE_WAND = "ice_wand";
    public static final String PROTECTOR_WAND = "protector_wand";
    public static final String SOULFIRE_WAND = "soulfire_wand";
    public static final String SHRIEK_WAND = "shriek_wand";

    private String wandName;
    private ItemStack wandItemStack;

    public Wand(String wandName, ItemStack wandItemStack) {
        this.wandItemStack = wandItemStack;
        this.wandName = wandName;
    }

    public abstract void castWand(Player caster);

    public ItemStack getWandItemStack() {
        return wandItemStack;
    }

    public String getWandName() {
        return wandName;
    }

}
