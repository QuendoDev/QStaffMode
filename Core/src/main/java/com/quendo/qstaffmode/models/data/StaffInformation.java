package com.quendo.qstaffmode.models.data;

import com.quendo.qore.utils.bukkit.BukkitUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@SuppressWarnings("unchecked")
public class StaffInformation implements ConfigurationSerializable {

    private Location savedLocation;
    private ItemStack[] inventoryItems;
    private ItemStack[] armor;

    public StaffInformation (Map<String, Object> map) {
        this.savedLocation = BukkitUtil.getOneLineLocFromString((String) map.get("savedLocation"));
        this.inventoryItems = ((ArrayList<ItemStack>) map.get("inventoryItems")).toArray(new ItemStack[0]);
        this.armor = ((ArrayList<ItemStack>) map.get("armor")).toArray(new ItemStack[0]);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("savedLocation", BukkitUtil.locToString(savedLocation));
        map.put("inventoryItems", inventoryItems);
        map.put("armor", armor);
        return map;
    }
}
