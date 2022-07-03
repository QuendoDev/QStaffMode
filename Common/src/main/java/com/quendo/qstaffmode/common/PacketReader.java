package com.quendo.qstaffmode.common;

import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Material;

public interface PacketReader {

    String getSoundEffect (PacketEvent event);

    Material getBlock (PacketEvent event);
}
