package com.quendo.qstaffmode.api;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public interface Utils {

    Material getMaterial (String id);

    ItemStack getItemInHand (PlayerEvent e);

    double getTPS();

    String getInventoryName(InventoryClickEvent inventoryClickEvent);

}
