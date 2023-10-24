package dev.slne.event.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.slne.event.Main;
import dev.slne.event.donation.CharityDonation;
import dev.slne.event.toast.CustomAdvancement;
import dev.slne.event.toast.FrameType;
import dev.slne.event.toast.Toast;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class DonationUtils {

    public static void broadcastDonationMessage(CharityDonation donation, Component spellName) {
        TextComponent.Builder builder = Component.text();
        builder.append(MessageManager.getPrefix());
        builder.append(Component.text(donation.getUsername(), MessageManager.PRIMARY)
                .hoverEvent(HoverEvent.showText(Component.text(donation.getMessage(), NamedTextColor.GRAY))));

        if (spellName != null) {
            builder.append(Component.text(" verwendet ", NamedTextColor.GRAY));
            builder.append(spellName);
            builder.append(Component.text(" mit der Hilfe von ", NamedTextColor.GRAY));
        } else {
            builder.append(Component.text(" spendet ", NamedTextColor.GRAY));
            builder.append(Component.text(donation.getAmountNet() + "€", MessageManager.PRIMARY));
            builder.append(Component.text(" an ", NamedTextColor.GRAY));
        }

        builder.append(Component.text("Thomas", MessageManager.PRIMARY, TextDecoration.BOLD)
                .clickEvent(ClickEvent.openUrl("https://charityroyale.at/donate/castcrafter/thomas-harrypotter"))
                .hoverEvent(HoverEvent
                        .showText(Component.text("Klicke hier, um auch zu spenden!", MessageManager.PRIMARY))));
        builder.append(Component.text("!", NamedTextColor.GRAY));

        Bukkit.broadcast(builder.build());
    }

    public static void broadcastDonationToast(CharityDonation donation) {
        Toast toast = new Toast("donation_" + donation.getId());
        toast.setFrameType(FrameType.TASK);
        toast.setMaterial(Material.RAW_GOLD);
        toast.setTitle(Component.text(donation.getUsername(), MessageManager.PRIMARY)
                .append(Component.text(" - ", NamedTextColor.GRAY))
                .append(Component.text(donation.getAmountNet() + "€", MessageManager.PRIMARY)));

        CustomAdvancement advancement = toast.build();
        for (Player player : Bukkit.getOnlinePlayers()) {
            advancement.show(Main.getInstance(), player);
        }
    }

}
