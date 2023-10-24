package dev.slne.event.donation;

import org.bukkit.plugin.PluginManager;

import dev.slne.event.Ignitor;
import dev.slne.event.Main;
import dev.slne.event.donation.listeners.DonationListener;
import dev.slne.event.goal.GoalCommand;
import dev.slne.event.goal.GoalManager;

public class DonationIgnitor extends Ignitor {

    private CharityEventReceiver eventReceiver;
    private GoalManager goalManager;

    @Override
    public void onLoad(PluginManager manager) {
        this.eventReceiver = new CharityEventReceiver();
        this.eventReceiver.connectFirst();

        this.goalManager = new GoalManager();
    }

    @Override
    public void onEnable(PluginManager manager) {
        new DonationCommand(getCommand("donation"));
        new GoalCommand(getCommand("goal"));

        manager.registerEvents(new DonationListener(), Main.getInstance());
    }

    @Override
    public void onDisable(PluginManager manager) {
        this.eventReceiver.disconnect();
    }

    public GoalManager getGoalManager() {
        return goalManager;
    }

}
