package com.quendo.qstaffmode.common;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public interface Utils {

    Material getMaterial (String id);

    ItemStack getItemInHand (PlayerEvent e);

    double getTPS();

    String getInventoryName(InventoryClickEvent inventoryClickEvent);

    boolean isPlayerHead (ItemStack itemStack);

    boolean isSkull (ItemStack itemStack);

    void sendActionBar (Player p, String message);

}
