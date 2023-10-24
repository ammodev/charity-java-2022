package dev.slne.event.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import dev.slne.event.Main;

public class ListenerManager {

    public void ignite() {
        PluginManager manager = Bukkit.getPluginManager();
        Plugin plugin = Main.getInstance();

        manager.registerEvents(new OnlineListener(), plugin);
        manager.registerEvents(new ChatListener(), plugin);
        manager.registerEvents(new HeadListener(), plugin);
        manager.registerEvents(new SignListener(), plugin);
        // manager.registerEvents(new TwitchMessageListener(), plugin);
    }

}
