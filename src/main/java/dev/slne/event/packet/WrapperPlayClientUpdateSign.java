package dev.slne.event.packet;

import java.util.Arrays;
import java.util.List;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import net.kyori.adventure.text.Component;

public class WrapperPlayClientUpdateSign extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Client.UPDATE_SIGN;

    public WrapperPlayClientUpdateSign() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayClientUpdateSign(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve Location.
     * <p>
     * Notes: block Coordinates
     * 
     * @return The current Location
     */
    public BlockPosition getLocation() {
        return handle.getBlockPositionModifier().read(0);
    }

    /**
     * Set Location.
     * 
     * @param value - new value.
     */
    public void setLocation(BlockPosition value) {
        handle.getBlockPositionModifier().write(0, value);
    }

    /**
     * Retrieve this sign's lines of text.
     * 
     * @return The current lines
     */
    public String[] getLines() {
        return handle.getStringArrays().read(0);
    }

    public Component[] getLinesAsComponentArray() {
        String[] lines = getLines();

        Component[] components = new Component[lines.length];

        for (int i = 0; i < lines.length; i++) {
            components[i] = Component.text(lines[i]);
        }

        return components;
    }

    public List<Component> getLinesAsComponentList() {
        return Arrays.asList(getLinesAsComponentArray());
    }

    /**
     * Set this sign's lines of text.
     * 
     * @param value - Lines, must be 4 elements long
     */
    public void setLines(String[] value) {
        if (value == null)
            throw new IllegalArgumentException("value cannot be null!");
        if (value.length != 4)
            throw new IllegalArgumentException("value must have 4 elements!");

        handle.getStringArrays().write(0, value);
    }
}
