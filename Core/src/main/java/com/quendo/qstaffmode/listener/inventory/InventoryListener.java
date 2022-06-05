package com.quendo.qstaffmode.listener.inventory;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.api.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class InventoryListener implements Listener {

    @Named("menus")
    private YMLFile menus;

    private Utils utils;

    @EventHandler
    public void onClick (InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            String invName = utils.getInventoryName(e);
            //TODO: STAFFLIST MENU
            //////////////////INVSEE MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("inspect.title")).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
            }
        }
    }

    private boolean checks (InventoryClickEvent e) {
        return e.getCurrentItem() == null || e.getSlotType() == null || e.getCurrentItem().getType().equals(Material.AIR);
    }
}
