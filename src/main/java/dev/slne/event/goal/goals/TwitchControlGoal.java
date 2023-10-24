package dev.slne.event.goal.goals;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import dev.slne.event.Main;
import dev.slne.event.goal.Goal;
import dev.slne.event.goal.nonGoals.NonGoal;
import dev.slne.event.twitch.TwitchIgnitor;
import dev.slne.event.twitch.events.TwitchCharityEvent;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class TwitchControlGoal extends Goal implements Listener {

    private final double DISTANCE = 1;
    private boolean activated = false;
    private BukkitTask controlTask;

    public TwitchControlGoal() {
        super("chat", Component.text("Twitch-Steuerung", NamedTextColor.AQUA, TextDecoration.BOLD),
                Component.text("Imperio", NamedTextColor.RED, TextDecoration.BOLD),
                "Erlaubt dem Twitch-Chat den Charakter für " + 30 + " Sekunden zu steuern", 150);

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @Override
    public void executeGoal() {
        if (activated) {
            return;
        }

        if (this.controlTask != null && !this.controlTask.isCancelled()) {
            this.controlTask.cancel();
        }

        for (Player player : NonGoal.getTargetedPlayers()) {
            player.setWalkSpeed(0);
        }

        TwitchIgnitor ignitor = (TwitchIgnitor) Main.getInstance().getIgnitor(TwitchIgnitor.class);

        if (ignitor == null) {
            return;
        }

        // Send message to twitch chat
        ignitor.getTwitchClient().getClient().getChat().sendMessage("castcrafter", "!twitch-input-on");

        this.activated = true;
        this.controlTask = new BukkitRunnable() {
            public void run() {
                activated = false;

                for (Player player : NonGoal.getTargetedPlayers()) {
                    player.setWalkSpeed(0.2f);
                    player.sendMessage(MessageManager.getPrefix().append(getSpellName())
                            .append(Component.text(" wurde beendet!", MessageManager.SUCCESS)));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
                }

                // Send message to twitch chat
                ignitor.getTwitchClient().getClient().getChat().sendMessage("castcrafter",
                        "!twitch-input-off");
            };
        }.runTaskLater(Main.getInstance(), 30 * 20);
    }

    @EventHandler
    public void onTwitchInput(TwitchCharityEvent event) {
        if (!activated) {
            return;
        }

        TwitchChatInput twitchChatInput = event.getTwitchChatInput();

        for (Player player : NonGoal.getTargetedPlayers()) {
            Vector facingVector = player.getEyeLocation().getDirection();
            Vector finalVector = new Vector();

            if (twitchChatInput.equals(TwitchChatInput.JUMP)) {
                @SuppressWarnings("deprecation")
                boolean onGround = player.isOnGround();
                finalVector = new Vector(0, onGround ? 0.5 : 0, 0);
            } else {
                if (twitchChatInput.equals(TwitchChatInput.W)) {
                    finalVector = facingVector.multiply(DISTANCE);
                } else if (twitchChatInput.equals(TwitchChatInput.A)) {
                    finalVector = facingVector.multiply(DISTANCE).rotateAroundY(Math.PI / 2);
                } else if (twitchChatInput.equals(TwitchChatInput.S)) {
                    finalVector = facingVector.multiply(DISTANCE).rotateAroundY(Math.PI);
                } else if (twitchChatInput.equals(TwitchChatInput.D)) {
                    finalVector = facingVector.multiply(DISTANCE).rotateAroundY(-Math.PI / 2);
                }
            }

            player.sendMessage(Component
                    .text("You have been pushed by "
                            + event.getTwitchMessageEvent().getMessageEvent().getUser().getName()
                            + " in the direction of "
                            + twitchChatInput.name(), NamedTextColor.GRAY));
            player.setVelocity(finalVector);
        }
    }

    @EventHandler
    public void onMove(PlayerJumpEvent event) {
        if (!activated) {
            return;
        }

        Player player = event.getPlayer();
        if (!NonGoal.getTargetedPlayers().contains(player)) {
            return;
        }

        event.setCancelled(activated);
    }
}
