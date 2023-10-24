package dev.slne.event.twitch.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;

public class TwitchMessageEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private ChannelMessageEvent messageEvent;

    public TwitchMessageEvent(ChannelMessageEvent messageEvent) {
        super(false);

        this.messageEvent = messageEvent;
    }

    public ChannelMessageEvent getMessageEvent() {
        return messageEvent;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
