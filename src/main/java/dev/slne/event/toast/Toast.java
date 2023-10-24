package dev.slne.event.toast;

import javax.swing.Icon;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import dev.slne.event.toast.CustomAdvancement.CustomAdvancementBuilder;
import net.kyori.adventure.text.Component;

public class Toast {
    protected CustomAdvancementBuilder builder;

    /**
     * Creates a toast at the current time
     */
    public Toast() {
        // lets use the current time
        this(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * Creates a toast
     *
     * @param key a key to identify the key with
     */
    public Toast(String key) {
        builder = CustomAdvancement.builder(new NamespacedKey("toasts", key));
        builder.frame(FrameType.GOAL);
        builder.icon(new ItemStack(Material.LIME_DYE));
        builder.description("");
        builder.toast(true); // of course toast
    }

    /**
     * Sets the title of the toast
     *
     * @param components the chat components
     * @return this
     */
    public Toast setTitle(Component title) {
        builder.title(title);
        return this;
    }

    /**
     * Sets the title from the given text
     *
     * @param text the text
     * @return this
     */
    public Toast setTitle(String text) {
        return setTitle(Component.text(text));
    }

    /**
     * Sets the icon with the given material
     *
     * @param material the material to set
     * @return this
     */
    public Toast setMaterial(Material material) {
        return setIcon(new ItemStack(material));
    }

    /**
     * Sets the icon to display
     *
     * @param icon the {@link Icon}
     * @return this
     */
    public Toast setIcon(ItemStack icon) {
        builder.icon(icon);
        return this;
    }

    /**
     * Sets the frame type of this toast
     *
     * @param type the frame type
     * @return this
     */
    public Toast setFrameType(FrameType type) {
        builder.frame(type);
        return this;
    }

    /**
     * Builds this toast to a {@link CustomAdvancement}
     *
     * @return the new {@link CustomAdvancement}
     */
    public CustomAdvancement build() {
        return builder.build();
    }

    /**
     * @return the builder
     */
    public CustomAdvancementBuilder getBuilder() {
        return builder;
    }
}
