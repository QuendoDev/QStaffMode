package com.quendo.qstaffmode.menus.stafflist.submenus;

import com.kino.kore.utils.BukkitUtils;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.api.ItemBuilder;
import com.quendo.qstaffmode.menus.stafflist.submenus.pages.PageTracker;
import com.quendo.qstaffmode.menus.stafflist.submenus.pages.PlayerPage;
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

public class WithoutStaffModeMenu {

    @Getter
    private final Map<ItemStack, Integer> defaultItems = new HashMap<>();

    @Inject
    @Named("menus")
    private YMLFile menus;

    @Inject
    private ItemBuilder itemBuilder;

    @Inject
    private PageTracker pageTracker;

    @Inject
    private Storage<UUID, StaffInformation> inStaffMode;

    private Inventory without;

    public void create () {
        without = Bukkit.createInventory(null, menus.getInt("unavailableStaff.size"), menus.getString("unavailableStaff.title"));
        setupDecoration();
    }

    public void open (Player p, int page) {
        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (ItemStack item : defaultItems.keySet()) {
            without.setItem(defaultItems.get(item), item);
        }

        int pages = totalPages();

        //Set all heads for the page the player is.
        setHeads(pages, page);

        //Set the next page button (item).
        if (pages > page) {
            String where = menus.getString("unavailableStaff.nextPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                without.setItem(without.getSize() - 1, setupNextPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                without.setItem(without.getSize() - 4, setupNextPageItem());
            }
        }

        //Set the previous page button (item).
        if (page > 1) {
            String where = menus.getString("unavailableStaff.previousPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                without.setItem(without.getSize() - 9, setupPrevPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                without.setItem(without.getSize() - 6, setupPrevPageItem());
            }
        }

        //Set the item that tells you in which page are you.
        if (menus.getBoolean("unavailableStaff.pageItem.enabled")) {
            without.setItem(without.getSize() - 5, setupPagesItem(page, pages));
        }

        p.openInventory(without);
        pageTracker.addPlayerPage(new PlayerPage(p, page));
    }

    private void setupDecoration () {
        if(menus.getBoolean("unavailableStaff.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("unavailableStaff.decoration.lore"));
            if (menus.getString("unavailableStaff.decoration.type").equalsIgnoreCase("full")) {
                for (int i = 0; i < without.getSize(); i++) {
                    defaultItems.put(item, i);
                }
            }
            if (menus.getString("unavailableStaff.decoration.type").equalsIgnoreCase("frame")) {
                for (int i : BukkitUtils.slotsOfBorderOfInventory(without.getSize())) {
                    defaultItems.put(item, i);
                }
            }
        }
    }

    private ItemStack setupNextPageItem () {
        return buildItem("nextPage", menus.getStringList("unavailableStaff.nextPage.lore"));
    }

    private ItemStack setupPrevPageItem () {
        return buildItem("previousPage", menus.getStringList("unavailableStaff.previousPage.lore"));
    }

    private ItemStack setupPagesItem (int page, int totalPages) {
        String name = menus.getString("unavailableStaff.pageItem.name")
                .replace("<total>", totalPages + "")
                .replace("<page>", page + "");
        List<String> lore = menus.getStringList("unavailableStaff.pageItem.lore");
        lore.replaceAll(line -> line
                .replace("<total>", totalPages + "")
                .replace("<page>", page + ""));
        return buildItem("pageItem", lore, name);
    }

    private void setHeads (int pages, int page) {
        int space = getSpaceForHeads();
        int slot = 0;
        int maxSlot = without.getSize() - 10;
        List<Integer> borderSlots = BukkitUtils.slotsOfBorderOfInventory(without.getSize());

        for (int i = space * (page - 1); i < getWithoutStaffMode().size(); i++) {
            slot++;
            while (borderSlots.contains(slot)) {
                slot ++;
            }
            if (slot > maxSlot) {
                break;
            }
            ItemStack head = createHead(i);
            without.setItem(slot, head);
        }
    }

    private ItemStack createHead (int i) {
        List<UUID> staff = getWithoutStaffMode();
        UUID uuid = staff.get(i);
        Player player = Bukkit.getPlayer(uuid);
        String name = menus.getString("unavailableStaff.heads.name").replace("<player>", player.getName());
        List<String> lore = menus.getStringList("unavailableStaff.heads.lore");
        lore.replaceAll(
                line -> line.replace("<player>", player.getName()));
        itemBuilder.amount(menus.getInt("unavailableStaff.heads.amount")).name(name).lore(lore);
        if (menus.getBoolean("unavailableStaff.heads.glow")) {
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
        if (getSkullType(item) == SkullType.NAME) {
            itemBuilder.setOwner(getSkullId(item));
        }
        if (getSkullType(item) == SkullType.URL) {
            itemBuilder.setURL(getSkullId(item));
        }
        finalItem = itemBuilder.build();
        return finalItem;
    }


    private String getId (String item) {
        return menus.getString("unavailableStaff." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("unavailableStaff." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("unavailableStaff." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("unavailableStaff." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("unavailableStaff." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("unavailableStaff." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("unavailableStaff." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("unavailableStaff." + item + ".skull.id");
    }

    private int totalPages() {
        int i = getWithoutStaffMode().size();
        int s = getSpaceForHeads();
        return i % s == 0 ? (i / s) : (i / s) + 1;
    }

    private List<UUID> getWithoutStaffMode () {
        List<UUID> withoutStaffMode = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("qstaffmode.staff") && !inStaffMode.find(p.getUniqueId()).isPresent()) {
                withoutStaffMode.add(p.getUniqueId());
            }
        }

        return withoutStaffMode;
    }

    private int getSpaceForHeads () {
        return without.getSize() - BukkitUtils.slotsOfBorderOfInventory(without.getSize()).size();
    }
}
