package dev.slne.event.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class PlayerListUtils {

        public static Component getTabListHeader(Player player) {
                String dateString = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
                                .format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
                String timeString = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
                                .format(DateTimeFormatter.ofPattern("HH:mm"));
                Component dateTimeComponent = Component.text().append(Component.text(dateString,
                                NamedTextColor.GOLD))
                                .append(Component.text(" - ", NamedTextColor.GRAY))
                                .append(Component.text(timeString, NamedTextColor.GOLD)).build();

                String currentPlayersString = String.valueOf(
                                Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> player.canSee(onlinePlayer))
                                                .toList()
                                                .size());

                String maxPlayersString = String.valueOf(Bukkit.getServer().getMaxPlayers());
                Component playersComponent = Component.text()
                                .append(Component.text(currentPlayersString,
                                                NamedTextColor.GOLD))
                                .append(Component.text(" / ", NamedTextColor.GRAY))
                                .append(Component.text(maxPlayersString, NamedTextColor.GOLD)).build();

                Component infoComponent = Component.text().append(Component.space()).append(dateTimeComponent)
                                .append(Component.text(" §7<> ", NamedTextColor.GRAY)).append(playersComponent)
                                .append(Component.space()).build();

                Component headerComponent = Component.text().append(Component.newline())
                                .append(Component.text(" CASTCRAFTER ", MessageManager.PRIMARY, TextDecoration.BOLD))
                                .append(Component.newline())
                                .append(Component.text(" EVENT ", MessageManager.PRIMARY))
                                .append(Component.newline())
                                .append(Component.newline()).append(infoComponent).append(Component.newline()).build();

                return headerComponent;
        }

        public static Component getTabListFooter(Player player) {
                long ping = PingUtils.getPing(player);
                int intPing = (int) ping;

                String pingString = getPingColorString(intPing) + intPing + "ms";

                double[] tpsObject = Bukkit.getServer().getTPS();
                long oneMinuteTPS = Math.round(Math.min(Math.round(tpsObject[0] * 100) / 100d, 20f));
                long fiveMinuteTPS = Math.round(Math.min(Math.round(tpsObject[1] * 100) / 100d, 20f));
                long fiveteenMinuteTPS = Math.round(Math.min(Math.round(tpsObject[2] * 100) / 100d, 20f));

                String tpsColor1 = getTPSColorString(oneMinuteTPS);
                String tpsColor2 = getTPSColorString(fiveMinuteTPS);
                String tpsColor3 = getTPSColorString(fiveteenMinuteTPS);

                String tpsString = String.format("%s%s §7/ %s%s §7/ %s%s", tpsColor1, oneMinuteTPS, tpsColor2,
                                fiveMinuteTPS,
                                tpsColor3, fiveteenMinuteTPS);
                Component performanceComponent = Component.text().append(Component.space())
                                .append(Component.text(pingString))
                                .append(Component.text(" <> ", NamedTextColor.GRAY)).append(Component.text(tpsString))
                                .append(Component.space()).build();

                Component socialComponent = Component.text()
                                .append(Component.text("twitch.tv/CastCrafter", NamedTextColor.GRAY))
                                .append(Component.newline())
                                .append(Component.text("youtube.com/c/CastCrafter", NamedTextColor.GRAY))
                                .append(Component.newline())
                                .append(Component.text("twitter.com/CastCrafter", NamedTextColor.GRAY))
                                .append(Component.newline())
                                .append(Component.text("discord.gg/CastCrafter", NamedTextColor.GRAY)).build();

                Component footerComponent = Component.text().append(Component.newline()).append(socialComponent)
                                .append(Component.newline()).append(Component.newline()).append(performanceComponent)
                                .append(Component.newline()).build();

                return footerComponent;
        }

        public static String getPingColorString(int ping) {
                String colorString;

                if (ping <= 80) {
                        colorString = "§a";
                } else if (ping > 80 && ping <= 120) {
                        colorString = "§e";
                } else {
                        colorString = "§c";
                }

                return colorString;
        }

        public static String getTPSColorString(double tps) {
                String colorString;

                if (tps >= 18) {
                        colorString = "§a";
                } else if (tps < 18 && tps >= 14) {
                        colorString = "§e";
                } else {
                        colorString = "§c";
                }

                return colorString;
        }

}
