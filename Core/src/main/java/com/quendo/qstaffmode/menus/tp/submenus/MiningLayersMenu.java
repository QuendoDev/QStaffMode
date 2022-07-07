package com.quendo.qstaffmode.menus.tp.submenus;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.BukkitUtil;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.menus.pages.PageTracker;
import com.quendo.qstaffmode.menus.pages.PlayerPage;
import com.quendo.qstaffmode.utils.SkullType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class MiningLayersMenu {

    @Getter
    private final Map<Integer, ItemStack> defaultItems = new HashMap<>();

    @Inject
    @Named("menus")
    private OldYMLFile menus;

    @Inject
    @Named("config")
    private OldYMLFile config;

    @Inject
    private ItemBuilder itemBuilder;

    @Inject
    private PageTracker pageTracker;

    private Inventory inMiningLayers;

    public void create () {
        inMiningLayers = Bukkit.createInventory(null, menus.getInt("inMiningLayers.size"), menus.getString("inMiningLayers.title"));
        setupDecoration();
    }

    public void open (Player p, int page) {
        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (Integer i : defaultItems.keySet()) {
            inMiningLayers.setItem(i, defaultItems.get(i));
        }

        int pages = totalPages();

        //Set all heads for the page the player is.
        setHeads(pages, page);

        //Set the next page button (item).
        if (pages > page) {
            String where = menus.getString("inMiningLayers.nextPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                inMiningLayers.setItem(inMiningLayers.getSize() - 1, setupNextPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                inMiningLayers.setItem(inMiningLayers.getSize() - 4, setupNextPageItem());
            }
        }

        //Set the previous page button (item).
        if (page > 1) {
            String where = menus.getString("inMiningLayers.previousPage.slot");
            if (where.equalsIgnoreCase("corner")) {
                inMiningLayers.setItem(inMiningLayers.getSize() - 9, setupPrevPageItem());
            }
            if (where.equalsIgnoreCase("middle")) {
                inMiningLayers.setItem(inMiningLayers.getSize() - 6, setupPrevPageItem());
            }
        }

        //Set the item that tells you in which page are you.
        if (menus.getBoolean("inMiningLayers.pageItem.enabled")) {
            inMiningLayers.setItem(inMiningLayers.getSize() - 5, setupPagesItem(page, pages));
        }

        p.openInventory(inMiningLayers);
        pageTracker.addPlayerPage(new PlayerPage(p, page));
    }

    private void setupDecoration () {
        if(menus.getBoolean("inMiningLayers.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("inMiningLayers.decoration.lore"));
            if (menus.getString("inMiningLayers.decoration.type").equalsIgnoreCase("full")) {
                for (int i = 0; i < inMiningLayers.getSize(); i++) {
                    defaultItems.put(i, item);
                }
            }
            if (menus.getString("inMiningLayers.decoration.type").equalsIgnoreCase("frame")) {
                for (int i : BukkitUtil.slotsOfBorderOfInventory(inMiningLayers.getSize())) {
                    defaultItems.put(i, item);
                }
            }
        }
    }

    private ItemStack setupNextPageItem () {
        return buildItem("nextPage", menus.getStringList("inMiningLayers.nextPage.lore"));
    }

    private ItemStack setupPrevPageItem () {
        return buildItem("previousPage", menus.getStringList("inMiningLayers.previousPage.lore"));
    }

    private ItemStack setupPagesItem (int page, int totalPages) {
        String name = menus.getString("inMiningLayers.pageItem.name")
                .replace("<total>", totalPages + "")
                .replace("<page>", page + "");
        List<String> lore = menus.getStringList("inMiningLayers.pageItem.lore");
        lore.replaceAll(line -> line
                .replace("<total>", totalPages + "")
                .replace("<page>", page + ""));
        return buildItem("pageItem", lore, name);
    }

    private void setHeads (int pages, int page) {
        int space = getSpaceForHeads();
        int slot = 0;
        int maxSlot = inMiningLayers.getSize() - 10;
        List<Integer> borderSlots = BukkitUtil.slotsOfBorderOfInventory(inMiningLayers.getSize());

        for (int i = space * (page - 1); i < getInMiningLayers().size(); i++) {
            slot++;
            while (borderSlots.contains(slot)) {
                slot ++;
            }
            if (slot > maxSlot) {
                break;
            }
            ItemStack head = createHead(i);
            inMiningLayers.setItem(slot, head);
        }
    }

    private ItemStack createHead (int i) {
        List<UUID> mining = getInMiningLayers();
        UUID uuid = mining.get(i);
        Player player = Bukkit.getPlayer(uuid);
        String name = menus.getString("inMiningLayers.heads.name").replace("<player>", player.getName()).replace("<diff>", menus.getString("inMiningLayers.heads.difference"));
        List<String> lore = menus.getStringList("inMiningLayers.heads.lore");
        lore.replaceAll(
                line -> line.replace("<player>", player.getName())
                        .replace("<location>", BukkitUtil.formatLocation(player.getLocation(), false))
                        .replace("<diamonds>", getItemCount(player, "diamonds") + "")
                        .replace("<gold>", getItemCount(player, "gold") + "")
                        .replace("<iron>", getItemCount(player, "iron") + ""));
        itemBuilder.amount(menus.getInt("inMiningLayers.heads.amount")).name(name).lore(lore);
        if (menus.getBoolean("inMiningLayers.heads.glow")) {
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
        return menus.getString("inMiningLayers." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("inMiningLayers." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("inMiningLayers." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("inMiningLayers." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("inMiningLayers." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("inMiningLayers." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("inMiningLayers." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("inMiningLayers." + item + ".skull.id");
    }

    private int totalPages() {
        int i = getInMiningLayers().size();
        int s = getSpaceForHeads();
        return i % s == 0 ? (i / s) : (i / s) + 1;
    }

    private int getSpaceForHeads () {
        return inMiningLayers.getSize() - BukkitUtil.slotsOfBorderOfInventory(inMiningLayers.getSize()).size();
    }

    private List<UUID> getInMiningLayers () {
        List<UUID> miningLayers = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getLayers().contains(p.getLocation().getBlockY())) {
                miningLayers.add(p.getUniqueId());
            }
        }

        return miningLayers;
    }

    private List<Integer> getLayers () {
        List<Integer> layers = new ArrayList<>();
        for (String s : config.getString("miningLayers").split(",")) {
            layers.add(Integer.parseInt(s));
        }
        return layers;
    }

    private int getItemCount (Player p, String s) {
        int i = 0;
        switch (s) {
            case "diamonds":
                for (ItemStack item : p.getInventory().getContents()) {
                    if (item.getType() == Material.DIAMOND) {
                        i++;
                    }
                }
            case "gold":
                for (ItemStack item : p.getInventory().getContents()) {
                    if (item.getType() == Material.GOLD_INGOT) {
                        i++;
                    }
                }
            case "iron":
                for (ItemStack item : p.getInventory().getContents()) {
                    if (item.getType() == Material.IRON_INGOT) {
                        i++;
                    }
                }
        }
        return i;
    }
}
