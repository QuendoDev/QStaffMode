package com.quendo.qstaffmode.menus.tp;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.storage.Storage;
import com.quendo.qore.utils.bukkit.BukkitUtil;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.utils.SkullType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TpMainMenu {

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

    private Inventory tpiteminv;

    public void create () {
        tpiteminv = Bukkit.createInventory(null, menus.getInt("tpItemMenu.size"), menus.getString("tpItemMenu.title"));
        setupDecoration();
    }

    public void open (Player p) {
        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (Integer i : defaultItems.keySet()) {
            tpiteminv.setItem(i, defaultItems.get(i));
        }

        //Item that opens the menu where all players in staff-mode (all staff available) is shown.
        if (menus.getBoolean("tpItemMenu.tpRandomPlayer.enabled")) {
            tpiteminv.setItem(menus.getInt("tpItemMenu.tpRandomPlayer.slot"), setupTpRandomPlayer());
        }

        //Item that opens the menu where all players without staff-mode (all staff unavailable) is shown.
        if (menus.getBoolean("tpItemMenu.miningLayersPlayers.enabled")) {
            tpiteminv.setItem(menus.getInt("tpItemMenu.miningLayersPlayers.slot"), setupMiningLayersPlayers());
        }

        p.openInventory(tpiteminv);
    }

    private ItemStack setupTpRandomPlayer () {
        List<String> lore = menus.getStringList("tpItemMenu.tpRandomPlayer.lore");
        lore.replaceAll(line -> line
                .replace("<total>", Bukkit.getOnlinePlayers().size() + ""));
        return buildItem("tpRandomPlayer", lore);
    }

    private ItemStack setupMiningLayersPlayers () {
        List<String> lore = menus.getStringList("tpItemMenu.miningLayersPlayers.lore");
        lore.replaceAll(line -> line
                .replace("<total>", getInMiningLayers() + ""));
        return buildItem("miningLayersPlayers", lore);
    }

    private void setupDecoration () {
        if(menus.getBoolean("tpItemMenu.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("tpItemMenu.decoration.lore"));
            if (menus.getString("tpItemMenu.decoration.type").equalsIgnoreCase("full")) {
                for (int i = 0; i < menus.getInt("tpItemMenu.size"); i++) {
                    defaultItems.put(i, item);
                }
            }
            if (menus.getString("tpItemMenu.decoration.type").equalsIgnoreCase("frame")) {
                for (int i : BukkitUtil.slotsOfBorderOfInventory(menus.getInt("tpItemMenu.size"))) {
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
        return menus.getString("tpItemMenu." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("tpItemMenu." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("tpItemMenu." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("tpItemMenu." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("tpItemMenu." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("tpItemMenu." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("tpItemMenu." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("tpItemMenu." + item + ".skull.id");
    }

    private int getInMiningLayers () {
        int miningLayers = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getLayers().contains(p.getLocation().getBlockY())) {
                miningLayers++;
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
}
