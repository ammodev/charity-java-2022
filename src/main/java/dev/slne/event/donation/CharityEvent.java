package dev.slne.event.donation;

import java.time.Instant;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CharityEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private CharityDonation charityDonation;
    private Instant timestamp;

    public CharityEvent(CharityDonation charityDonation) {
        this(charityDonation, false);
    }

    public CharityEvent(CharityDonation charityDonation, boolean async) {
        super(async);

        this.charityDonation = charityDonation;
        this.timestamp = Instant.now();
    }

    public CharityDonation getCharityDonation() {
        return charityDonation;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
