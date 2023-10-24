package dev.slne.event.donation;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

public class CharityDonation {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("amount")
    private double amount;

    @SerializedName("amount_net")
    private double amountNet;

    @SerializedName("message")
    private String message;

    @SerializedName("streamer_slug")
    private String streamerSlug;

    @SerializedName("wish_slug")
    private String wishSlug;

    @SerializedName("date")
    private String date;

    public CharityDonation(String id, String username, double amount, double amountNet, String message,
            String streamerSlug, String wishSlug, String date) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.amountNet = amountNet;
        this.message = message;
        this.streamerSlug = streamerSlug;
        this.wishSlug = wishSlug;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountNet() {
        return amountNet;
    }

    public String getMessage() {
        return message;
    }

    public String getStreamerSlug() {
        return streamerSlug;
    }

    public String getWishSlug() {
        return wishSlug;
    }

    public String getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAmountNet(double amountNet) {
        this.amountNet = amountNet;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStreamerSlug(String streamerSlug) {
        this.streamerSlug = streamerSlug;
    }

    public void setWishSlug(String wishSlug) {
        this.wishSlug = wishSlug;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(CharityDonation.class).add("id", this.id).add("username", this.username)
                .add("amount", this.amount).add("amountNet", this.amountNet).add("message", this.message)
                .add("streamerSlug", this.streamerSlug).add("wishSlug", this.wishSlug).add("date", this.date)
                .toString();
    }

}
