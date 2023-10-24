package dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.slne.event.book.EventBookPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BuildWelcomePage extends EventBookPage {

    @Override
    public Collection<Component> getComponents() {
        List<Component> components = new ArrayList<Component>();

        components.add(Component.newline());
        components.add(Component.newline());
        components.add(Component.newline());
        components.add(Component.newline());
        components.add(Component.newline());
        components.add(Component.newline());
        components.add(Component.text("    ABBAU EVENT", NamedTextColor.RED, TextDecoration.BOLD));

        return components;
    }

}
