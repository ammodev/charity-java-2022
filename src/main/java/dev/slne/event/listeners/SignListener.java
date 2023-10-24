package dev.slne.event.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class SignListener implements Listener {

    public SignListener() {

    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        Block block = event.getBlock();
        BlockState state = block.getState();

        if (state instanceof Sign sign) {
            sign.setEditable(true);

            for (int i = 0; i < event.lines().size(); i++) {
                Component line = event.lines().get(i);

                LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().extractUrls().hexColors()
                        .character('&').build();
                Component newLineComponent = serializer.deserialize(serializer.serialize(line));

                event.line(i, newLineComponent);
            }

            sign.update(true);
        }
    }

}
