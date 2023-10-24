package dev.slne.event.twitch;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

import dev.slne.event.Main;
import dev.slne.event.twitch.events.TwitchMessageEvent;

public class TwitchEventHandler {

    public void listenForTwitchEvents() {
        TwitchIgnitor ignitor = (TwitchIgnitor) Main.getInstance().getIgnitor(TwitchIgnitor.class);

        if (ignitor == null) {
            return;
        }

        EventManager eventManager = ignitor.getTwitchClient().getClient()
                .getEventManager();

        eventManager.onEvent(ChannelMessageEvent.class, event -> {
            new BukkitRunnable() {
                public void run() {
                    TwitchMessageEvent messageEvent = new TwitchMessageEvent(event);
                    Main.getInstance().getServer().getPluginManager().callEvent(messageEvent);
                }
            }.runTask(Main.getInstance());
        });
    }
}
