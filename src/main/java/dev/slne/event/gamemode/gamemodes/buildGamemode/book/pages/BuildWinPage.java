package dev.slne.event.gamemode.gamemodes.buildGamemode.book.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.slne.event.book.EventBookPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class BuildWinPage extends EventBookPage {

        @Override
        public Collection<Component> getComponents() {
                List<Component> components = new ArrayList<Component>();

                components.add(Component.text(
                                "Als Gewinn erhaltet ihr die neuen ",
                                NamedTextColor.BLACK));
                components.add(Component.text(
                                "EventCoins",
                                NamedTextColor.GOLD, TextDecoration.BOLD));
                components.add(Component.text(
                                ". Diese könnt ihr in Zukunft gegen ",
                                NamedTextColor.BLACK));
                components.add(Component.text(
                                "besondere",
                                NamedTextColor.BLACK, TextDecoration.BOLD));
                components.add(Component.text(
                                " Items eintauschen.",
                                NamedTextColor.BLACK));
                components.add(Component.newline());
                components.add(Component.newline());

                Map<Integer, Integer> coins = new HashMap<>();
                coins.put(1, 1500);
                coins.put(2, 1000);
                coins.put(3, 500);

                for (int i = 1; i <= 3; i++) {
                        components.add(Component.text(
                                        "Platz " + i + ": ",
                                        NamedTextColor.BLACK));
                        components.add(Component.text(
                                        coins.get(i) + " ",
                                        NamedTextColor.GOLD, TextDecoration.BOLD));
                        components.add(Component.text(
                                        "EventCoins",
                                        NamedTextColor.BLACK));
                        components.add(Component.newline());
                }

                return components;
        }

}
