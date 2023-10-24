package dev.slne.event.toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class CustomAdvancement {
    private static final Gson gson = new Gson();

    private NamespacedKey id;
    private String parent, background;
    private ItemStack icon;
    private Component title, description;
    private FrameType frame;
    private boolean announce = true, toast = true, hidden = true;

    private Set<Trigger.TriggerBuilder> triggers;

    private CustomAdvancement(NamespacedKey id, String parent,
            ItemStack icon, String background,
            Component title,
            Component description, FrameType frame, boolean announce, boolean toast, boolean hidden,
            Set<Trigger.TriggerBuilder> triggers) {

        this.id = id;
        this.parent = parent;
        this.icon = icon;
        this.background = background;
        this.title = title;
        this.description = description;
        this.frame = frame;
        this.announce = announce;
        this.toast = toast;
        this.hidden = hidden;
        this.triggers = triggers;
    }

    public static CustomAdvancementBuilder builder(NamespacedKey id) {
        return new CustomAdvancementBuilder().id(id);
    }

    @Deprecated
    public void save(String world) {
        this.save(Bukkit.getWorld(world));
    }

    @Deprecated
    public void save(World world) {
        File file = new File(world.getWorldFolder(), "data" + File.separator + "advancements" + File.separator
                + id.getNamespace() + File.separator + id.getKey() + ".json");

        File dir = file.getParentFile();

        if (dir.mkdirs() || dir.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(getJSON());
                Bukkit.getLogger().info("[AdvancementAPI] Created " + id.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getJSON() {
        JsonObject json = new JsonObject();

        JsonObject icon = new JsonObject();
        // lets take only type for now
        String item = getIcon().getType().getKey().toString();
        icon.addProperty("item", item);

        JsonObject display = new JsonObject();
        display.add("icon", icon);
        display.add("title", getJsonFromComponent(getTitle()));
        display.add("description", getJsonFromComponent(getDescription()));
        display.addProperty("background", getBackground());
        display.addProperty("frame", getFrame().toString());
        display.addProperty("announce_to_chat", announce);
        display.addProperty("show_toast", toast);
        display.addProperty("hidden", hidden);

        json.addProperty("parent", getParent());

        JsonObject criteria = new JsonObject();

        // Changed to normal comment as JavaDocs are not displayed here @PROgrm_JARvis
        /*
         * Define each criteria, for each criteria in list, add items, trigger and
         * conditions
         */

        for (Trigger.TriggerBuilder triggerBuilder : getTriggers()) {
            Trigger trigger = triggerBuilder.build();
            criteria.add(trigger.name, trigger.toJsonObject());
        }

        json.add("criteria", criteria);
        json.add("display", display);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(json);
    }

    public ItemStack getIcon() {
        return icon;
    }

    public static JsonElement getJsonFromComponent(Component textComponent) {
        return gson.fromJson(GsonComponentSerializer.gson().serialize(textComponent), JsonElement.class);
    }

    public Component getTitle() {
        return this.title;
    }

    public Component getDescription() {
        return this.description;
    }

    public String getBackground() {
        return this.background;
    }

    public FrameType getFrame() {
        return this.frame;
    }

    public String getParent() {
        return this.parent;
    }

    public Set<Trigger.TriggerBuilder> getTriggers() {
        return this.triggers;
    }

    /**
     * Shows the {@link CustomAdvancement} to a list of players
     */
    public CustomAdvancement show(JavaPlugin plugin, Player... players) {
        add();
        grant(players);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            revoke(players);
            remove();
        }, 2);

        return this;
    }

    @SuppressWarnings("deprecation")
    public CustomAdvancement add() {
        try {
            Bukkit.getUnsafe().loadAdvancement(id, getJSON());
        } catch (IllegalArgumentException e) {
            // happens when already registered
        }
        return this;
    }

    public CustomAdvancement grant(Player... players) {
        Advancement advancement = getAdvancement();
        for (Player player : players) {
            if (!player.getAdvancementProgress(advancement).isDone()) {
                Collection<String> remainingCriteria = player.getAdvancementProgress(advancement)
                        .getRemainingCriteria();

                for (String remainingCriterion : remainingCriteria) {

                    player.getAdvancementProgress(getAdvancement()).awardCriteria(remainingCriterion);
                }
            }
        }
        return this;
    }

    public CustomAdvancement revoke(Player... players) {
        Advancement advancement = getAdvancement();
        for (Player player : players) {
            if (player.getAdvancementProgress(advancement).isDone()) {
                Collection<String> awardedCriteria = player.getAdvancementProgress(advancement).getAwardedCriteria();
                for (String awardedCriterion : awardedCriteria)
                    player.getAdvancementProgress(getAdvancement()).revokeCriteria(awardedCriterion);
            }
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public CustomAdvancement remove() {
        Bukkit.getUnsafe().removeAdvancement(id);
        return this;
    }

    public Advancement getAdvancement() {
        return Bukkit.getAdvancement(id);
    }

    public boolean counterUp(Player player) {
        String criteriaString = null;
        for (String criteria : getAdvancement().getCriteria()) {
            if (player.getAdvancementProgress(getAdvancement()).getDateAwarded(criteria) != null) {
                criteriaString = criteria;
            } else {
                break;
            }
        }
        if (criteriaString == null)
            return false;
        player.getAdvancementProgress(getAdvancement()).awardCriteria(criteriaString);
        return true;
    }

    public boolean counterDown(Player player) {
        String criteriaString = null;
        for (String criteria : getAdvancement().getCriteria()) {
            if (player.getAdvancementProgress(getAdvancement()).getDateAwarded(criteria) != null) {
                criteriaString = criteria;
            } else {
                break;
            }
        }
        if (criteriaString == null)
            return false;
        player.getAdvancementProgress(getAdvancement()).revokeCriteria(criteriaString);
        return true;
    }

    public void counterReset(Player player) {
        for (String criteria : getAdvancement().getCriteria()) {
            if (player.getAdvancementProgress(getAdvancement()).getDateAwarded(criteria) != null) {
                player.getAdvancementProgress(getAdvancement()).revokeCriteria(criteria);
            }
        }
    }

    public NamespacedKey getId() {
        return this.id;
    }

    public boolean isAnnounce() {
        return this.announce;
    }

    public boolean isToast() {
        return this.toast;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public static class CustomAdvancementBuilder {
        private NamespacedKey id;
        private String parent;
        private ItemStack icon;
        private String background;
        private Component title;
        private Component description;
        private FrameType frame;
        private boolean announce;
        private boolean toast;
        private boolean hidden;
        private int counter;
        private ArrayList<Trigger.TriggerBuilder> triggers;

        CustomAdvancementBuilder() {
        }

        public CustomAdvancementBuilder title(String title) {
            this.title = Component.text(title);

            return this;
        }

        public CustomAdvancementBuilder title(Component title) {
            this.title = title;

            return this;
        }

        public CustomAdvancementBuilder description(String description) {
            this.description = Component.text(description);
            return this;
        }

        public CustomAdvancementBuilder description(Component description) {
            this.description = description;
            return this;
        }

        public CustomAdvancementBuilder id(NamespacedKey id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the parent
         */
        public CustomAdvancementBuilder parent(String parent) {
            this.parent = parent;
            return this;
        }

        /**
         * Sets the display icon
         */
        public CustomAdvancementBuilder icon(ItemStack icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Sets the background
         *
         * @param background the background
         */
        public CustomAdvancementBuilder background(String background) {
            this.background = background;
            return this;
        }

        /**
         * Type of Frame
         *
         * @param frame the {@link FrameType}
         */
        public CustomAdvancementBuilder frame(FrameType frame) {
            this.frame = frame;
            return this;
        }

        /**
         * If the {@link Advancement} should be announced in chat
         *
         * @param announce true if announce
         */
        public CustomAdvancementBuilder announce(boolean announce) {
            this.announce = announce;
            return this;
        }

        /**
         * If there should be a toast
         *
         * @param toast the value
         */
        public CustomAdvancementBuilder toast(boolean toast) {
            this.toast = toast;
            return this;
        }

        /**
         * If the advancement is hidden
         */
        public CustomAdvancementBuilder hidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        /**
         * Adds the given trigger to trigger list
         *
         * @param trigger the trigger to add
         * @return
         */
        public CustomAdvancementBuilder trigger(Trigger.TriggerBuilder trigger) {
            if (this.triggers == null)
                this.triggers = new ArrayList<Trigger.TriggerBuilder>();
            this.triggers.add(trigger);
            return this;
        }

        /**
         * Adds a list of triggers
         *
         * @param triggers the triggers to add
         * @return
         */
        public CustomAdvancementBuilder triggers(Collection<? extends Trigger.TriggerBuilder> triggers) {
            if (this.triggers == null)
                this.triggers = new ArrayList<Trigger.TriggerBuilder>();
            this.triggers.addAll(triggers);
            return this;
        }

        /**
         * Clears all triggers
         *
         * @return
         */
        public CustomAdvancementBuilder clearTriggers() {
            if (this.triggers != null)
                this.triggers.clear();

            return this;
        }

        /**
         * Build a instance of {@link CustomAdvancement}
         *
         * @return the new advancement
         */
        public CustomAdvancement build() {
            Set<Trigger.TriggerBuilder> triggers;
            switch (this.triggers == null ? 0 : this.triggers.size()) {
                case 0:
                    triggers = java.util.Collections
                            .singleton(Trigger.builder(Trigger.TriggerType.IMPOSSIBLE, "default"));
                    break;
                case 1:
                    triggers = java.util.Collections.singleton(this.triggers.get(0));
                    break;
                default:
                    triggers = new java.util.LinkedHashSet<Trigger.TriggerBuilder>(this.triggers.size() < 1073741824
                            ? 1 + this.triggers.size() + (this.triggers.size() - 3) / 3
                            : Integer.MAX_VALUE);
                    triggers.addAll(this.triggers);
                    triggers = java.util.Collections.unmodifiableSet(triggers);
            }

            return new CustomAdvancement(id, parent, icon, background, title, description, frame, announce, toast,
                    hidden, triggers);
        }

        public String toString() {
            return (new StringBuilder().append("CustomAdvancementBuilder(id=").append(this.id).append(", parent=")
                    .append(this.parent).append(", icon=").append(this.icon).append(", background=")
                    .append(this.background).append(", title=").append(this.title).append(", description=")
                    .append(this.description).append(", frame=").append(this.frame).append(", announce=")
                    .append(this.announce).append(", toast=").append(this.toast).append(", hidden=").append(this.hidden)
                    .append(", counter=").append(this.counter).append(", triggers=").append(this.triggers).append(")")
                    .toString());
        }
    }
}
