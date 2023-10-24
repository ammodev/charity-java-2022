package dev.slne.event.wand;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class WandUtils {

    private static ItemStack getItemStack(Material material, int amount, int damage, Component displayName,
            Component... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof Damageable) {
            ((Damageable) itemMeta).setDamage(damage);
        }

        itemMeta.displayName(displayName);

        if (lore != null && lore.length > 0) {
            itemMeta.lore(Arrays.asList(lore));
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private static ItemStack glowItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack applyWandNBT(ItemStack itemStack, String wandType) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.getPersistentDataContainer().set(Wand.WAND_KEY, PersistentDataType.STRING, wandType);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getWololoWandItemStack() {
        ItemStack itemStack = getItemStack(Material.STICK, 1, 0,
                Component.text("Wololo Zauberstab", MessageManager.PRIMARY),
                Component.text("Rechtklick zum auslösen ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,
                        false),
                Component.text("der Zauberwirkung", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));

        glowItemStack(itemStack);
        applyWandNBT(itemStack, "wololo_wand");

        return itemStack;
    }

    public static ItemStack getIceWandItemStack() {
        ItemStack itemStack = getItemStack(Material.STICK, 1, 0,
                Component.text("Eis Zauberstab", MessageManager.PRIMARY),
                Component.text("Rechtklick zum auslösen ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,
                        false),
                Component.text("der Zauberwirkung", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));

        glowItemStack(itemStack);
        applyWandNBT(itemStack, "ice_wand");

        return itemStack;
    }

    public static ItemStack getEndProtectorWandItemStack() {
        ItemStack itemStack = getItemStack(Material.STICK, 1, 0,
                Component.text("Protector Zauberstab", MessageManager.PRIMARY),
                Component.text("Rechtklick zum auslösen ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,
                        false),
                Component.text("der Zauberwirkung", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));

        glowItemStack(itemStack);
        applyWandNBT(itemStack, "protector_wand");

        return itemStack;
    }

    public static ItemStack getSoulfireWandItemStack() {
        ItemStack itemStack = getItemStack(Material.STICK, 1, 0,
                Component.text("Soulfire Zauberstab", MessageManager.PRIMARY),
                Component.text("Rechtklick zum auslösen ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,
                        false),
                Component.text("der Zauberwirkung", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));

        glowItemStack(itemStack);
        applyWandNBT(itemStack, "soulfire_wand");

        return itemStack;
    }

    public static ItemStack getShriekWandItemStack() {
        ItemStack itemStack = getItemStack(Material.STICK, 1, 0,
                Component.text("Shriek Zauberstab", MessageManager.PRIMARY),
                Component.text("Rechtklick zum auslösen ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC,
                        false),
                Component.text("der Zauberwirkung", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false));

        glowItemStack(itemStack);
        applyWandNBT(itemStack, "shriek_wand");

        return itemStack;
    }

}
