package com.quendo.qstaffmode.menus.stafflist;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.storage.Storage;
import com.quendo.qore.utils.bukkit.BukkitUtil;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.models.data.StaffInformation;
import com.quendo.qstaffmode.utils.SkullType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class StaffListMainMenu {

    @Getter
    private final Map<Integer, ItemStack> defaultItems = new HashMap<>();

    @Inject
    @Named("menus")
    private OldYMLFile menus;

    @Inject
    private ItemBuilder itemBuilder;

    @Inject
    private Storage<UUID, StaffInformation> inStaffMode;

    private Inventory main;

    public void create () {
        main = Bukkit.createInventory(null, menus.getInt("main.size"), menus.getString("main.title"));
        setupDecoration();
    }

    public void open (Player p) {

        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (Integer i : defaultItems.keySet()) {
            main.setItem(i, defaultItems.get(i));
        }

        //Item that opens the menu where all players in staff-mode (all staff available) is shown.
        if (menus.getBoolean("main.inStaffMode.enabled")) {
            main.setItem(menus.getInt("main.inStaffMode.slot"), setupAvailableStaffItem());
        }

        //Item that opens the menu where all players without staff-mode (all staff unavailable) is shown.
        if (menus.getBoolean("main.withoutStaffMode.enabled")) {
            main.setItem(menus.getInt("main.withoutStaffMode.slot"), setupNonAvailableStaffItem());
        }

        p.openInventory(main);
    }

    private ItemStack setupAvailableStaffItem () {
        List<String> lore = menus.getStringList("main.inStaffMode.lore");
        lore.replaceAll(line -> line
                .replace("<total>", inStaffMode.get().size() + ""));
        return buildItem("inStaffMode", lore);
    }

    private ItemStack setupNonAvailableStaffItem () {
        List<String> lore = menus.getStringList("main.withoutStaffMode.lore");
        lore.replaceAll(line -> line
                .replace("<total>", getWithoutStaffMode() + ""));
        return buildItem("withoutStaffMode", lore);
    }

    private void setupDecoration () {
        if(menus.getBoolean("main.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("main.decoration.lore"));
            if (menus.getString("main.decoration.type").equalsIgnoreCase("full")) {
                for (int i = 0; i < menus.getInt("main.size"); i++) {
                    defaultItems.put(i, item);
                }
            }
            if (menus.getString("main.decoration.type").equalsIgnoreCase("frame")) {
                for (int i : BukkitUtil.slotsOfBorderOfInventory(menus.getInt("main.size"))) {
                    defaultItems.put(i, item);
                }
            }
        }
    }

    private ItemStack buildItem (String item, List<String> lore) {
        return buildItem(item, lore, getName(item));
    }

    private ItemStack buildItem (String item, List<String> lore, String name) {
        ItemStack finalItem;
        itemBuilder.material(getId(item))
                .amount(getAmount(item))
                .data(getData(item))
                .name(name)
                .lore(lore);
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
        return finalItem;
    }


    private String getId (String item) {
        return menus.getString("main." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("main." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("main." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("main." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("main." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("main." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("main." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("main." + item + ".skull.id");
    }

    private int getWithoutStaffMode () {
        int withoutStaffMode = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("qstaffmode.staff") && !inStaffMode.find(p.getUniqueId()).isPresent()) {
                withoutStaffMode++;
            }
        }

        return withoutStaffMode;
    }
}
