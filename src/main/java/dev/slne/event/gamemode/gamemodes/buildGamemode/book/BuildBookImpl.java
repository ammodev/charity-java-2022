package dev.slne.event.gamemode.gamemodes.buildGamemode.book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.slne.event.book.EventBook;
import dev.slne.event.book.EventBookPage;
import dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages.BuildInformationPage;
import dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages.BuildWelcomePage;
import dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages.BuildWinPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BuildBookImpl extends EventBook {

    public BuildBookImpl() {
        super(Component.text("Block Sammler", NamedTextColor.AQUA, TextDecoration.BOLD),
                Component.text("SLNE Dev Team"));
    }

    @Override
    public Collection<EventBookPage> getBookPages() {
        List<EventBookPage> pages = new ArrayList<>();

        pages.add(new BuildWelcomePage());
        pages.add(new BuildInformationPage());
        pages.add(new BuildWinPage());

        return pages;
    }

}
