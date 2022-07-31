package com.quendo.qstaffmode.inject.loader;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.setup.Loader;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.models.StaffItem;
import com.quendo.qstaffmode.manager.ItemManager;
import com.quendo.qstaffmode.utils.SkullType;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.List;

@InjectAll
public class ItemsLoader implements Loader {

    @Named("items")
    private OldYMLFile items;

    private ItemBuilder itemBuilder;
    private ItemManager itemManager;

    @Override
    public void load() {
        for (String item : items.getConfigurationSection("items").getKeys(false)) {
            ItemStack finalItem;
            itemBuilder.material(getId(item))
                    .amount(getAmount(item))
                    .data(getData(item))
                    .name(getName(item))
                    .lore(getLore(item)).unbreakable();
            if (getGlow(item)) {
                itemBuilder.glow();
            }
            if (getSkullType(item) == SkullType.OWNER) {
                itemBuilder.setOwner(getSkullId(item));
            }
            if (getSkullType(item) == SkullType.URL) {
                itemBuilder.setURL(getSkullId(item));
            }
            finalItem = itemBuilder.build();
            StaffItem staffItem = new StaffItem(
                    getEnabled(item),
                    finalItem,
                    getSlot(item));

            itemManager.getItems().put(item, staffItem);
        }
    }

    private boolean getEnabled (String item) {
        return items.getBoolean("items." + item + ".enabled");
    }
    private String getId (String item) {
        return items.getString("items." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) items.getInt("items." + item + ".data");
    }
    private int getAmount (String item) {
        return items.getInt("items." + item + ".amount");
    }
    private String getName (String item) {
        return items.getString("items." + item + ".name");
    }
    private List<String> getLore (String item) {
        return items.getStringList("items." + item + ".lore");
    }
    private boolean getGlow (String item) {
        return items.getBoolean("items." + item + ".gow");
    }
    private int getSlot (String item) {
        return items.getInt("items." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(items.getString("items." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return items.getString("items." + item + ".skull.id");
    }

}
