package com.quendo.qstaffmode.models.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class StaffInformation implements ConfigurationSerializable {

    private Location savedLocation;
    private ItemStack[] inventoryItems;
    private ItemStack[] armor;

    public StaffInformation (Map<String, Object> map) {
        this.savedLocation = (Location) map.get("savedLocation");
        this.inventoryItems = (ItemStack[]) map.get("inventoryItems");
        this.armor = (ItemStack[]) map.get("armor");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("savedLocation", savedLocation);
        map.put("inventoryItems", inventoryItems);
        map.put("armor", armor);
        return map;
    }
}
