package dev.slne.event.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MessageManager {

    public static TextColor PRIMARY = TextColor.fromHexString("#7DF8FF");
    public static TextColor SECONDARY = TextColor.fromHexString("#FF7D7D");

    public static TextColor ERROR = TextColor.fromHexString("#d9534f");
    public static TextColor WARNING = TextColor.fromHexString("#f0ad4e");
    public static TextColor SUCCESS = TextColor.fromHexString("#5cb85c");
    public static TextColor INFO = TextColor.fromHexString("#5bc0de");

    public static Component getPrefix() {
        return Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text("Event", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY));
    }

}
