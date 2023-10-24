package dev.slne.event;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;

public abstract class Ignitor {

    public abstract void onLoad(PluginManager manager);

    public abstract void onEnable(PluginManager manager);

    public abstract void onDisable(PluginManager manager);

    public PluginCommand getCommand(String name) {
        return Main.getInstance().getCommand(name);
    }
}
