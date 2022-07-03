package com.quendo.qstaffmode.inject.loader;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.common.PacketReader;
import com.quendo.qstaffmode.manager.PacketManager;
import org.bukkit.Material;

import javax.inject.Inject;

public class ProtocolLoader implements Loader {

    @Inject
    private QStaffMode qStaffMode;

    @Inject
    private PacketReader packetReader;

    @Inject
    private PacketManager packetManager;

    @Override
    public void load() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(qStaffMode, ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT, PacketType.Play.Server.BLOCK_ACTION) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (packetManager.isChestOpening()) {
                            System.out.println("asd");
                            if (event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                                System.out.println("asda");
                                if (packetReader.getSoundEffect(event).contains("chest")) {
                                    System.out.println("asdd");
                                    event.setCancelled(true);
                                }
                            }
                            if (event.getPacketType() == PacketType.Play.Server.BLOCK_ACTION) {
                                System.out.println("asdas");
                                if (packetReader.getBlock(event) == Material.CHEST || packetReader.getBlock(event) == Material.TRAPPED_CHEST) {
                                    System.out.println("asdasda");
                                    event.setCancelled(true);
                                }
                            }
                            packetManager.setChestOpening(false);
                        }
                    }
                });
    }
}
