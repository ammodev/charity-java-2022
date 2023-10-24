package dev.slne.event.wand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import dev.slne.event.Main;
import dev.slne.event.wand.wands.IceWand;
import dev.slne.event.wand.wands.ProtectorWand;
import dev.slne.event.wand.wands.ShriekWand;
import dev.slne.event.wand.wands.WololoWand;

public class WandManager {

    private List<Wand> wands;

    public void ignite() {
        this.wands = new ArrayList<Wand>();

        Bukkit.getPluginManager().registerEvents(new WandListener(), Main.getInstance());

        registerWands();
    }

    public List<Wand> getWands() {
        return wands;
    }

    public void registerWands() {
        this.wands.add(new IceWand());
        this.wands.add(new ShriekWand());
        this.wands.add(new WololoWand());
        this.wands.add(new ProtectorWand());
    }

}
