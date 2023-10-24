package dev.slne.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.slne.event.credits.Credits;
import dev.slne.event.credits.CreditsCommand;
import dev.slne.event.gamemode.GameManager;
import dev.slne.event.gamemode.GameModeListeners;
import dev.slne.event.listeners.ListenerManager;
import dev.slne.event.user.User;
import dev.slne.event.user.UserManager;
import dev.slne.event.utils.PingUtils;
import dev.slne.event.utils.PlayerListUtils;
import dev.slne.event.utils.TeamUtils;
import dev.slne.event.wand.WandCommand;
import dev.slne.event.wand.WandManager;

public class Main extends JavaPlugin {

    private static Main instance;
    private List<Ignitor> ignitors;

    private UserManager userManager;

    private WandManager wandManager;
    private GameManager gameManager;
    private TeamUtils teamUtils;

    @Override
    public void onLoad() {
        instance = this;

        this.igniteIgnitors();
        this.loadIgnitors();
    }

    @Override
    public void onEnable() {
        this.enableIgnitors();

        this.userManager = new UserManager();
        this.userManager.ignite();

        this.wandManager = new WandManager();
        this.wandManager.ignite();

        this.gameManager = new GameManager();

        new ListenerManager().ignite();

        new WandCommand(getCommand("wand"));

        new CreditsCommand(getCommand("credits"));
        Credits.init();

        PingUtils pingUtils = new PingUtils();
        pingUtils.ignite();
        Bukkit.getPluginManager().registerEvents(pingUtils, instance);
        Bukkit.getPluginManager().registerEvents(new GameModeListeners(), instance);

        this.teamUtils = new TeamUtils();

        new BukkitRunnable() {
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendPlayerListHeaderAndFooter(PlayerListUtils.getTabListHeader(
                            all),
                            PlayerListUtils.getTabListFooter(all));
                }
            };
        }.runTaskTimer(instance, 0, 20);
    }

    @Override
    public void onDisable() {
        this.disableIgnitors();
    }

    private void igniteIgnitors() {
        this.ignitors = new ArrayList<Ignitor>();
        // this.ignitors.add(new DonationIgnitor());
        // this.ignitors.add(new TwitchIgnitor());
    }

    private void loadIgnitors() {
        for (Ignitor ignitor : this.ignitors) {
            ignitor.onLoad(Bukkit.getPluginManager());
        }
    }

    private void enableIgnitors() {
        for (Ignitor ignitor : this.ignitors) {
            ignitor.onEnable(Bukkit.getPluginManager());
        }
    }

    private void disableIgnitors() {
        for (Ignitor ignitor : this.ignitors) {
            ignitor.onDisable(Bukkit.getPluginManager());
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Ignitor> Ignitor getIgnitor(Class<? extends T> ignitorClass) {
        for (Ignitor ignitor : this.ignitors) {
            if (ignitor.getClass().equals(ignitorClass)) {
                return (T) ignitor;
            }
        }

        return null;
    }

    public static Main getInstance() {
        return instance;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public User getUser(Player player) {
        return userManager.getUser(player);
    }

    public List<User> getOnlineUsers() {
        return userManager.getUsers();
    }

    public WandManager getWandManager() {
        return wandManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TeamUtils getTeamUtils() {
        return teamUtils;
    }

}
