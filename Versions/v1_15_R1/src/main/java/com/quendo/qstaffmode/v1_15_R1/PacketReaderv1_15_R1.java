package com.quendo.qstaffmode.v1_15_R1;

import com.comphenix.protocol.events.PacketEvent;
import com.quendo.qstaffmode.common.PacketReader;
import org.bukkit.Material;

public class PacketReaderv1_15_R1 implements PacketReader {

    @Override
    public String getSoundEffect(PacketEvent event) {
        return event.getPacket().getSoundEffects().read(0).name();
    }

    @Override
    public Material getBlock(PacketEvent event) {
        return event.getPacket().getBlocks().read(0);
    }
}
