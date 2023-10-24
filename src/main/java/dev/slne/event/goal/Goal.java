package dev.slne.event.goal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.slne.event.Main;
import dev.slne.event.toast.CustomAdvancement;
import dev.slne.event.toast.FrameType;
import dev.slne.event.toast.Toast;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class Goal {

    private boolean enabled;

    private String name;
    private Component displayName;
    private Component spellName;
    private String description;
    private boolean active = true;

    private double currentAmount = 0;
    private double executeAtAmount;

    public Goal(String name, Component displayName, Component spellName, String description, double executeAtAmount) {
        this.name = name;
        this.displayName = displayName;
        this.spellName = spellName;
        this.description = description;
        this.executeAtAmount = executeAtAmount;

        this.enabled = true;
    }

    public void addAmount(double amount) {
        if (!this.isActive()) {
            return;
        }

        this.currentAmount += amount;
        this.broadcastGoalAdded(amount);

        if (this.currentAmount >= this.executeAtAmount) {
            double difference = this.currentAmount - this.executeAtAmount;
            this.currentAmount = difference;

            if (difference > 0) {
                this.broadcastGoalAdded(difference);
            }

            this.execute();
        }
    }

    public void execute() {
        Main.getInstance().getLogger().info("Executing and resetting goal " + this.name);

        // Execute goal
        this.executeGoal();
        this.broadcastGoalReached();

        Main.getInstance().getLogger().info("Executed and resetted goal " + this.name);
    }

    public abstract void executeGoal();

    private void broadcastGoalAdded(double amount) {
        TextComponent.Builder builder = Component.text();
        builder.append(MessageManager.getPrefix());
        builder.append(Component.text("[", NamedTextColor.DARK_GRAY));
        builder.append(this.spellName != null ? this.spellName : this.displayName);
        builder.append(Component.text("]", NamedTextColor.DARK_GRAY));
        builder.append(Component.space());

        builder.append(Component.text(this.currentAmount + "€", NamedTextColor.GOLD));
        builder.append(Component.text(" / ", NamedTextColor.GRAY));
        builder.append(Component.text(this.executeAtAmount + "€", NamedTextColor.GOLD));

        Bukkit.broadcast(builder.build());
    }

    private void broadcastGoalReached() {
        TextComponent.Builder builder = Component.text();

        builder.append(MessageManager.getPrefix());
        builder.append(Component.text("Das Ziel ", MessageManager.SUCCESS));
        builder.append(this.spellName != null ? this.spellName : this.displayName);
        builder.append(Component.text(" wurde erreicht!", MessageManager.SUCCESS));

        Bukkit.broadcast(builder.build());

        Toast toast = new Toast();
        toast.setFrameType(FrameType.CHALLENGE);
        toast.setMaterial(Material.GOAT_HORN);
        toast.setTitle(Component.text("Ziel - ", MessageManager.SUCCESS).append(this.spellName != null ? this.spellName
                : this.displayName)
                .append(Component.text(" - erreicht!", MessageManager.SUCCESS)));

        CustomAdvancement advancement = toast.build();
        for (Player player : Bukkit.getOnlinePlayers()) {
            advancement.show(Main.getInstance(), player);
        }
    }

    public String getName() {
        return name;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getExecuteAtAmount() {
        return executeAtAmount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Component getSpellName() {
        return spellName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
