package dev.slne.event.twitch;

import org.bukkit.plugin.PluginManager;

import dev.slne.event.Ignitor;

public class TwitchIgnitor extends Ignitor {

    private CharityTwitchClient twitchClient;

    @Override
    public void onLoad(PluginManager manager) {
        this.twitchClient.ignite();
        this.twitchClient = new CharityTwitchClient();

        new TwitchEventHandler().listenForTwitchEvents();
    }

    @Override
    public void onEnable(PluginManager manager) {

    }

    @Override
    public void onDisable(PluginManager manager) {

        this.twitchClient.getClient().getEventManager().close();
        this.twitchClient.getClient().getChat().disconnect();
        this.twitchClient.getClient().getClientHelper().close();
    }

    public CharityTwitchClient getTwitchClient() {
        return twitchClient;
    }

}
