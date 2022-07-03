package com.quendo.qstaffmode.v1_8_R3;

import com.comphenix.protocol.events.PacketEvent;
import com.quendo.qstaffmode.common.PacketReader;
import org.bukkit.Material;

public class PacketReaderv1_8_R3 implements PacketReader {

    @Override
    public String getSoundEffect(PacketEvent event) {
        return event.getPacket().getStrings().read(0);
    }

    @Override
    public Material getBlock(PacketEvent event) {
        return event.getPacket().getBlocks().read(0);
    }
}
