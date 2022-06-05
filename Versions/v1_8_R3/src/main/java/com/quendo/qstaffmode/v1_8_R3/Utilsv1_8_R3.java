package com.quendo.qstaffmode.v1_8_R3;

import com.quendo.qstaffmode.api.Utils;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class Utilsv1_8_R3 implements Utils {

    @Override
    public ItemStack getItemInHand(PlayerEvent e) {
        return e.getPlayer().getItemInHand();
    }

    @Override
    public double getTPS() {
        return MinecraftServer.getServer().recentTps[0];
    }

    @Override
    public String getInventoryName(InventoryClickEvent inventoryClickEvent) {
        return inventoryClickEvent.getView().getTitle();
    }

    @Override
    public Material getMaterial(String id) {
        return Material.getMaterial(id) == null ? Material.STONE : Material.getMaterial(id);
    }
}
