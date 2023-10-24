package dev.slne.event.book;

import java.util.Collection;

import dev.slne.event.utils.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public abstract class EventBookPage {
    protected final TextColor PRIMARY = MessageManager.PRIMARY;
    protected final TextColor SECONDARY = MessageManager.SECONDARY;

    public abstract Collection<Component> getComponents();

    public Component getComponent() {
        TextComponent.Builder componentBuilder = Component.text();

        for (Component component : getComponents()) {
            componentBuilder.append(component);
        }

        return componentBuilder.build();
    }
}
