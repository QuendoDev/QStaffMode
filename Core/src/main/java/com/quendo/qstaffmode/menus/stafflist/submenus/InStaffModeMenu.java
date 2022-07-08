package com.quendo.qstaffmode.menus.stafflist.submenus;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.storage.Storage;
import com.quendo.qore.utils.bukkit.BukkitUtil;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.menus.pages.PageTracker;
import com.quendo.qstaffmode.menus.pages.PlayerPage;
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

public class InStaffModeMenu {

    @Getter
    private final Map<Integer, ItemStack> defaultItems = new HashMap<>();

    @Inject
    @Named("menus")
    private OldYMLFile menus;

    @Inject
    private ItemBuilder itemBuilder;

    @Inject
    private PageTracker pageTracker;

    @Inject
    private Storage<UUID, StaffInformation> inStaffMode;

    private Inventory available;

    public void create () {
        available = Bukkit.createInventory(null, menus.getInt("availableStaff.size"), menus.getString("availableStaff.title"));
        setupDecoration();
    }

    public void open (Player p, int page) {
        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (Integer i : defaultItems.keySet()) {
            available.setItem(i, defaultItems.get(i));
        }

        int pages = totalPages();

        //Set all heads for the page the player is.
        setHeads(pages, page);

        //Set the next page button (item).
        if (pages > page) {
            String where = menus.getString("availableStaff.nextPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                available.setItem(available.getSize() - 1, setupNextPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                available.setItem(available.getSize() - 4, setupNextPageItem());
            }
        }

        //Set the previous page button (item).
        if (page > 1) {
            String where = menus.getString("availableStaff.previousPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                available.setItem(available.getSize() - 9, setupPrevPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                available.setItem(available.getSize() - 6, setupPrevPageItem());
            }
        }

        //Set the item that tells you in which page are you.
        if (menus.getBoolean("availableStaff.pageItem.enabled")) {
            available.setItem(available.getSize() - 5, setupPagesItem(page, pages));
        }

        p.openInventory(available);
        pageTracker.addPlayerPage(new PlayerPage(p, page));
    }

    private void setupDecoration () {
        if(menus.getBoolean("availableStaff.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("availableStaff.decoration.lore"));
            if (menus.getString("availableStaff.decoration.type").equalsIgnoreCase("full")) {
                for (int i = 0; i < available.getSize(); i++) {
                    defaultItems.put(i, item);
                }
            }
            if (menus.getString("availableStaff.decoration.type").equalsIgnoreCase("frame")) {
                for (int i : BukkitUtil.slotsOfBorderOfInventory(available.getSize())) {
                    defaultItems.put(i, item);
                }
            }
        }
    }

    private ItemStack setupNextPageItem () {
        return buildItem("nextPage", menus.getStringList("availableStaff.nextPage.lore"));
    }

    private ItemStack setupPrevPageItem () {
        return buildItem("previousPage", menus.getStringList("availableStaff.previousPage.lore"));
    }

    private ItemStack setupPagesItem (int page, int totalPages) {
        String name = menus.getString("availableStaff.pageItem.name")
                .replace("<total>", totalPages + "")
                .replace("<page>", page + "");
        List<String> lore = menus.getStringList("availableStaff.pageItem.lore");
        lore.replaceAll(line -> line
                .replace("<total>", totalPages + "")
                .replace("<page>", page + ""));
        return buildItem("pageItem", lore, name);
    }

    private void setHeads (int pages, int page) {
        int space = getSpaceForHeads();
        int slot = 0;
        int maxSlot = available.getSize() - 10;
        List<Integer> borderSlots = BukkitUtil.slotsOfBorderOfInventory(available.getSize());

        for (int i = space * (page - 1); i < inStaffMode.get().size(); i++) {
            slot++;
            while (borderSlots.contains(slot)) {
                slot ++;
            }
            if (slot > maxSlot) {
                break;
            }
            ItemStack head = createHead(i);
            available.setItem(slot, head);
        }
    }

    private ItemStack createHead (int i) {
        List<UUID> staff = new ArrayList<>(inStaffMode.get().keySet());
        UUID uuid = staff.get(i);
        Player player = Bukkit.getPlayer(uuid);
        String name = menus.getString("availableStaff.heads.name").replace("<player>", player.getName());
        List<String> lore = menus.getStringList("availableStaff.heads.lore");
        lore.replaceAll(
                line -> line.replace("<player>", player.getName()));
        itemBuilder.amount(menus.getInt("availableStaff.heads.amount")).name(name).lore(lore);
        if (menus.getBoolean("availableStaff.heads.glow")) {
            itemBuilder.glow();
        }
        return itemBuilder.buildPlayerSkull(uuid);
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
        return menus.getString("availableStaff." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("availableStaff." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("availableStaff." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("availableStaff." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("availableStaff." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("availableStaff." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("availableStaff." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("availableStaff." + item + ".skull.id");
    }

    private int totalPages() {
        int i = inStaffMode.get().size();
        int s = getSpaceForHeads();
        return i % s == 0 ? (i / s) : (i / s) + 1;
    }

    private int getSpaceForHeads () {
        return available.getSize() - BukkitUtil.slotsOfBorderOfInventory(available.getSize()).size();
    }
}
