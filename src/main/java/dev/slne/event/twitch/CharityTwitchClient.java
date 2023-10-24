package dev.slne.event.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.reactor.ReactorEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

public class CharityTwitchClient {
    private TwitchClient client;

    public void ignite() {
        OAuth2Credential credential = new OAuth2Credential("twitch", "6tpa7cq17myt51cxznc8i9cs85c2qr");

        this.client = TwitchClientBuilder.builder().withEnableChat(true).withChatAccount(
                credential).withEnableHelix(true).withDefaultEventHandler(ReactorEventHandler.class).build();
        this.client.getChat().joinChannel("ammo_dev");
        this.client.getChat().joinChannel("castcrafter");
    }

    public TwitchClient getClient() {
        return client;
    }
}
