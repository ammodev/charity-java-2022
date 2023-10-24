package dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.slne.event.book.EventBookPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BuildInformationPage extends EventBookPage {

    @Override
    public Collection<Component> getComponents() {
        List<Component> components = new ArrayList<Component>();

        components.add(Component.text(
                "Das Event funktioniert so, dass ihr möglichst viele Blöcke mit den vorgegebenen Tools sammelt.",
                NamedTextColor.BLACK));

        components.add(Component.text(" Am Ende des Events ist allerdings nur ", NamedTextColor.BLACK));
        components.add(Component.text("EINE", NamedTextColor.BLACK, TextDecoration.BOLD));
        components.add(Component.text(" Block-Art wichtig. ", NamedTextColor.BLACK));

        components.add(Component.text(
                " Die gesuchte Block-Art ist allerdings streng geheim und wird erst nach dem Event bekanntgegeben.",
                NamedTextColor.BLACK));

        return components;
    }

}
