package com.quendo.qstaffmode.staffmode;

import com.quendo.qstaffmode.models.StaffItem;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    @Getter
    private final Map<String, StaffItem> items = new HashMap<>();

    public void giveItems (Player player, boolean vanished) {
        Map<String, StaffItem> tempItems = items;
        if (vanished) {
            tempItems.remove("visible");
        } else {
            tempItems.remove("vanish");
        }
        for (String s : tempItems.keySet()) {
            if (tempItems.get(s).isEnabled()) {
                player.getInventory().setItem(tempItems.get(s).getSlot(), tempItems.get(s).getItem());
            }
        }
    }

    public void giveVanishItem (Player player, boolean vanished) {
        if (vanished) {
            if (items.get("vanish").isEnabled()) {
                player.getInventory().setItem(items.get("vanish").getSlot(), items.get("vanish").getItem());
            }
        } else {
            if (items.get("visible").isEnabled()) {
                player.getInventory().setItem(items.get("visible").getSlot(), items.get("visible").getItem());
            }
        }
    }

}
