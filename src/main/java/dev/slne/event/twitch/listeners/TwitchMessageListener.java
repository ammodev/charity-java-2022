package dev.slne.event.twitch.listeners;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.twitch4j.common.events.domain.EventChannel;

import dev.slne.event.goal.goals.TwitchChatInput;
import dev.slne.event.twitch.events.TwitchCharityEvent;
import dev.slne.event.twitch.events.TwitchMessageEvent;

public class TwitchMessageListener implements Listener {

    @EventHandler
    public void onTwitchMessage(TwitchMessageEvent event) {
        EventChannel channel = event.getMessageEvent().getChannel();

        if (channel.getName().equals("castcrafter") || channel.getName().equals("ammo_dev")) {
            String message = event.getMessageEvent().getMessage();
            String messageLowercase = message.toLowerCase().strip();

            TwitchChatInput twitchChatInput = Arrays.asList(TwitchChatInput.values()).stream()
                    .filter(twitchChatInputItem -> {
                        return messageLowercase.equals(twitchChatInputItem.name().toLowerCase());
                    }).findFirst().orElse(null);

            if (twitchChatInput == null) {
                return;
            }

            TwitchCharityEvent charityEvent = new TwitchCharityEvent(twitchChatInput, event);
            Bukkit.getPluginManager().callEvent(charityEvent);
        }
    }

}
