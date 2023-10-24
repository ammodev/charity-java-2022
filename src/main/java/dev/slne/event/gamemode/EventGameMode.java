package dev.slne.event.gamemode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.slne.event.Main;
import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class EventGameMode {

    private Book bookDescription;

    private Logger logger;
    private String name;
    private Component componentName;
    private int startTime;
    protected GameMode gameMode;
    private boolean loaded = false, enabled = false, started = false, disabled = true;
    private List<GameModeListener> unmodifiedListeners;
    private List<GameModeListener> listeners;
    private GameModeHandler beforeStartHandler;
    private BukkitTask startTask;

    public EventGameMode(String name, Component componentName, int startTime) {
        this.logger = Main.getInstance().getLogger();

        this.name = name;
        this.componentName = componentName;
        this.startTime = startTime;
        this.gameMode = GameMode.ADVENTURE;

        this.listeners = new ArrayList<GameModeListener>();
        this.unmodifiedListeners = new ArrayList<GameModeListener>();
    }

    public abstract void onLoad();

    public abstract void onStart();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract Location getLobbyLocation();

    public abstract Book getDescriptionBook();

    public Book getDescriptionBookImplementation() {
        return this.bookDescription != null ? this.bookDescription : (this.bookDescription = this.getDescriptionBook());
    }

    public void onLoaded() {
        logger.info("Loading gamemode " + this.name);
        this.onLoad();
        logger.info("Loaded gamemode " + this.name);
    }

    public void onEnabled() {
        logger.info("Enabling gamemode " + this.name);
        this.onEnable();
        logger.info("Enabled gamemode " + this.name);
    }

    public void onStarted() {
        logger.info("Starting gamemode " + this.name);

        if (this.beforeStartHandler != null) {
            this.beforeStartHandler.handle();
        }

        this.startGamemodeTimer();
        logger.info("Started gamemode " + this.name);
    }

    public void onDisabled() {
        logger.info("Disabling gamemode " + this.name);
        this.onDisable();
        logger.info("Disabled gamemode " + this.name);
    }

    public String getName() {
        return name;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public List<GameModeListener> getListeners() {
        return listeners;
    }

    public void startGamemodeTimer() {
        if (startTask != null) {
            startTask.cancel();
            startTask = null;
        }

        this.startTask = new BukkitRunnable() {
            private int time = startTime;

            public void run() {
                List<Integer> times = Arrays.asList(60, 30, 15, 10, 5, 4, 3, 2, 1, 0);

                if (times.contains(time)) {
                    TextComponent.Builder builder = Component.text();
                    builder.append(Component.text("Der Spielmodus ", NamedTextColor.GRAY));
                    builder.append(componentName);
                    builder.append(Component.text(" startet ", NamedTextColor.GRAY));

                    if (time == 0) {
                        builder.append(Component.text("Jetzt", NamedTextColor.GOLD));

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, .25f, 1f);
                        }
                    } else {
                        builder.append(Component.text("in ", NamedTextColor.GRAY));
                        builder.append(Component.text(time, NamedTextColor.GOLD));
                        builder.append(Component.text(time == 1 ? " Sekunde" : " Sekunden", NamedTextColor.GRAY));

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, .25f, 1f);
                        }
                    }

                    builder.append(Component.text("!", NamedTextColor.GRAY));

                    Bukkit.broadcast(MessageManager.getPrefix().append(builder.build()));
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (time == 0) {
                        player.sendActionBar(componentName.append(Component.text(" startet ", NamedTextColor.GRAY))
                                .append(Component.text("Jetzt!", NamedTextColor.GOLD)));
                    } else {
                        Component two = Component
                                .text(" startet in ", NamedTextColor.GRAY)
                                .append(Component.text(time, NamedTextColor.GOLD))
                                .append(Component.text(time == 1 ? " Sekunde" : " Sekunden", NamedTextColor.GRAY));
                        player.sendActionBar(componentName.append(two));
                    }
                }

                if (time <= 0) {
                    started = true;
                    preparePlayers();
                    onStart();
                    setStarted(true);
                    this.cancel();
                }

                time--;
            };
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    public BukkitTask getStartTask() {
        return startTask;
    }

    public void addListener(GameModeListener listener) {
        this.listeners.add(listener);
        this.unmodifiedListeners.add(listener);
    }

    public void removeListener(GameModeListener listener) {
        this.listeners.remove(listener);
    }

    public void bukkitRegisterListener(GameModeListener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
    }

    public void bukkitUnregisterListener(GameModeListener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void bukkitRegisterListeners() {
        for (GameModeListener listener : listeners) {
            bukkitRegisterListener(listener);
        }
    }

    public void bukkitUnregisterListeners() {
        for (GameModeListener listener : unmodifiedListeners) {
            if (listener == null) {
                continue;
            }

            bukkitUnregisterListener(listener);
        }

        this.listeners.clear();
        this.unmodifiedListeners.clear();
    }

    public void preparePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!(player.getGameMode().equals(GameMode.ADVENTURE) || player.getGameMode().equals(GameMode.SURVIVAL))) {
                continue;
            }

            player.setGameMode(gameMode);

            player.setExp(0);
            player.setLevel(0);
            player.getInventory().clear();

            player.setAllowFlight(false);
            player.setFlying(false);

            player.setArrowsInBody(0);
            player.setArrowsStuck(0);
            player.setBeeStingersInBody(0);
            player.setBeeStingerCooldown(0);

            player.setSaturation(20);
            player.setFoodLevel(20);
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());

            player.setInvulnerable(false);
            player.setInvisible(false);
        }
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setBeforeStartHandler(GameModeHandler beforeStartHandler) {
        this.beforeStartHandler = beforeStartHandler;
    }

    public GameModeHandler getBeforeStartHandler() {
        return beforeStartHandler;
    }

    public Component getComponentName() {
        return componentName;
    }

}
