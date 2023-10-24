package dev.slne.event.donation;

public class AsyncCharityEvent extends CharityEvent {

    public AsyncCharityEvent(CharityDonation charityDonation) {
        super(charityDonation, true);
    }

}
