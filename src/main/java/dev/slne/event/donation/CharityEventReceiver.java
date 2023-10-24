package dev.slne.event.donation;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import dev.slne.event.Main;

public class CharityEventReceiver {

    private final PusherOptions PUSHER_OPTIONS;
    private final Pusher PUSHER;

    private boolean firstConnection = true;

    public CharityEventReceiver() {
        PUSHER_OPTIONS = new PusherOptions();
        PUSHER_OPTIONS.setCluster("eu");

        PUSHER = new Pusher("key", PUSHER_OPTIONS);
    }

    public void connectFirst() {
        PUSHER.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange change) {
                Main.getInstance().getLogger().info("Pusher connection state changed from " + change.getPreviousState()
                        + " to " + change.getCurrentState());
            }

            @Override
            public void onError(String message, String code, Exception e) {
                Main.getInstance().getLogger().severe("Pusher connection error: " + message + " (" + code + ")");
            }
        }, ConnectionState.ALL);

        Channel channel = PUSHER.subscribe("donations");
        channel.bind("App\\Events\\DonationCompleted", new SubscriptionEventListener() {
            @Override
            public void onEvent(PusherEvent event) {
                String data = event.getData();

                Gson gson = new Gson();
                CharityDonation charityDonation = gson.fromJson(data, CharityDonation.class);

                // Can happen if the user loses internet connection whilst donating on
                // make-a-wish.at
                if (charityDonation.getStreamerSlug() == null) {
                    return;
                }

                // Can be null if the user doesnt supply a username whilst donating
                if (charityDonation.getUsername() == null) {
                    charityDonation.setUsername("Anonym");
                }

                String streamerSlug = charityDonation.getStreamerSlug();
                if (!(streamerSlug.equals("castcrafter"))) {
                    return;
                }

                new BukkitRunnable() {
                    public void run() {
                        // Call bukkit event for further processing
                        CharityEvent charityEvent = new CharityEvent(charityDonation);
                        Bukkit.getPluginManager().callEvent(charityEvent);
                    };
                }.runTaskLater(Main.getInstance(), 1);
            }
        });

        firstConnection = false;
    }

    public void connect() {
        PUSHER.connect();
    }

    public void disconnect() {
        PUSHER.disconnect();
    }

    public void reconnect() {
        if (PUSHER != null && PUSHER.getConnection() != null
                && !(PUSHER.getConnection().getState() == ConnectionState.DISCONNECTED || PUSHER
                        .getConnection().getState() == ConnectionState.DISCONNECTING)) {
            PUSHER.disconnect();
        }

        if (this.firstConnection) {
            this.connectFirst();
        } else {
            this.connect();
        }
    }
}
