package dev.slne.event.toast;

import org.bukkit.inventory.ItemStack;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;

public class Condition {
    protected String name;
    protected JsonObject set;

    /**
     * @param name the name
     * @param set  the set
     */
    private Condition(String name, JsonObject set) {
        this.name = name;
        this.set = set;
    }

    /**
     * Returns a {@link ConditionBuilder} for the given name and {@link
     * JsonObject}
     *
     * @param name      the name
     * @param itemStack the {@link JsonObject}
     * @return a {@link ConditionBuilder} for the given name and {@link
     *         JsonObject}
     */
    public static ConditionBuilder builder(String name, JsonObject itemStack) {
        return new Condition.ConditionBuilder().name(name).set(itemStack);
    }

    /**
     * Returns a {@link ConditionBuilder} for the given name and {@link
     * ItemStack}
     *
     * @param name      the name
     * @param itemStack the {@link ItemStack}
     * @return a {@link ConditionBuilder} for the given name and {@link
     *         ItemStack}
     */
    public static ConditionBuilder builder(String name, ItemStack itemStack) {
        return Condition.builder(name, convertItemToJSON(itemStack));
    }

    /**
     * Converts an {@link ItemStack} to a {@link JsonObject}
     *
     * @param item the {@link ItemStack}
     * @return the {@link JsonObject}
     */
    @SuppressWarnings("deprecation")
    private static JsonObject convertItemToJSON(ItemStack item) {
        JsonObject itemJSON = new JsonObject();
        itemJSON.addProperty("item", "minecraft:" + item.getType().name().toLowerCase());
        itemJSON.addProperty("amount", item.getAmount());
        itemJSON.addProperty("data", item.getData().getData());
        return itemJSON;
    }

    public static class ConditionBuilder {
        private String name;
        private JsonObject set;

        ConditionBuilder() {
        }

        /**
         * Sets the name of the {@link Condition}
         *
         * @param name the name
         * @return the current {@link ConditionBuilder}
         */
        public Condition.ConditionBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the set of the {@link Condition}
         *
         * @param set the set
         * @return the current {@link ConditionBuilder}
         */
        public Condition.ConditionBuilder set(JsonObject set) {
            this.set = set;
            return this;
        }

        /**
         * Build the current {@link Condition}
         *
         * @return the {@link Condition}
         */
        public Condition build() {
            return new Condition(name, set);
        }

        /**
         * Returns a {@link String} representative of the {@link Condition}
         *
         * @return a @link String} representative of the {@link Condition}
         */
        public String toString() {
            return MoreObjects.toStringHelper(this).add("name", name).add("set", set).toString();
        }
    }
}
