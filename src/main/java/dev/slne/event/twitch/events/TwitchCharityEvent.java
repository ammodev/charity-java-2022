package dev.slne.event.twitch.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import dev.slne.event.goal.goals.TwitchChatInput;

public class TwitchCharityEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private TwitchChatInput twitchChatInput;
    private TwitchMessageEvent twitchMessageEvent;

    public TwitchCharityEvent(TwitchChatInput twitchChatInput, TwitchMessageEvent twitchMessageEvent) {
        this.twitchChatInput = twitchChatInput;
        this.twitchMessageEvent = twitchMessageEvent;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public TwitchChatInput getTwitchChatInput() {
        return twitchChatInput;
    }

    public TwitchMessageEvent getTwitchMessageEvent() {
        return twitchMessageEvent;
    }

}
