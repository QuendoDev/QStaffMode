package com.quendo.qstaffmode.menus;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.utils.SkullType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InspectMenu {

    @Getter
    private final Map<Integer, ItemStack> defaultItems = new HashMap<>();

    @Inject
    @Named("menus")
    private OldYMLFile menus;

    @Inject
    private ItemBuilder itemBuilder;

    private Inventory inspect;

    public void create () {
         inspect = Bukkit.createInventory(null, 54, menus.getString("inspect.title"));
         setupDecoration();
    }

    public void open (Player p, Player interacted) {

        //Decoration (if it is not enabled, then "defaultItems" will be empty).
        for (Integer i : defaultItems.keySet()) {
            inspect.setItem(i, defaultItems.get(i));
        }

        //Inventory items from the interacted player.
        if (menus.getBoolean("inspect.contents")) {
            for (int i = 0; i < 36; i++) {
                inspect.setItem(i, interacted.getInventory().getContents()[i]);
            }
        }

        //Armor items from the interacted player.
        if (menus.getBoolean("inspect.armor")) {
            for (int i = 0; i < interacted.getInventory().getArmorContents().length; i++) {
                inspect.setItem(45 + i, interacted.getInventory().getArmorContents()[i]);
            }
        }

        //Info item (xp, food and health of the interacted player).
        if (menus.getBoolean("inspect.info.enabled")) {
            inspect.setItem(50, setupInfoItem(interacted));
        }

        //Gamemode of the player item.
        if (menus.getBoolean("inspect.gamemode.enabled")) {
            inspect.setItem(51, setupGamemodeItem(interacted));
        }

        //Player flying or not item.
        if (menus.getBoolean("inspect.fly.enabled")) {
            inspect.setItem(52, setupFlyItem(interacted));
        }

        //Effects on the player item.
        if (menus.getBoolean("inspect.effects.enabled")) {
            inspect.setItem(53, setupEffectsItem(interacted));
        }

        p.openInventory(inspect);
    }
    private ItemStack setupInfoItem (Player p) {
        List<String> lore = menus.getStringList("inspect.info.lore");
        lore.replaceAll(line -> line
                .replace("<name>", p.getDisplayName())
                .replace("<food>", p.getFoodLevel() + "")
                .replace("<xp>", p.getExpToLevel() + "")
                .replace("<health>", Math.round(p.getHealth() * 100) / 100.0 + ""));
        return buildItem("info", lore);
    }

    private ItemStack setupGamemodeItem (Player p) {
        List<String> lore = menus.getStringList("inspect.gamemode.lore");
        lore.replaceAll(line -> line
                .replace("<gm>", p.getGameMode() + ""));
        String name = menus.getString("inspect.gamemode.name").replace("<gm>", p.getGameMode() + "");
        return buildItem("gamemode", lore, name);
    }

    private ItemStack setupFlyItem (Player p) {
        List<String> lore = menus.getStringList("inspect.fly.lore");
        lore.replaceAll(line -> line.replace(
                "<fly>", p.isFlying() + ""));
        String name = menus.getString("inspect.fly.name").replace("<fly>", p.isFlying() + "");
        return buildItem("fly", lore, name);
    }

    private ItemStack setupEffectsItem (Player p) {
        List<String> lore = menus.getStringList("inspect.effects.lore");
        String format = menus.getString("inspect.effects.format");
        for(PotionEffect e : p.getActivePotionEffects()) {
            lore.add(format
                    .replace("<name>", e.getType().getName())
                    .replace("<level>", e.getAmplifier() + 1 + "")
                    .replace("<duration>", duration(e.getDuration())));
        }
        return buildItem("effects", lore);
    }

    private void setupDecoration () {
        if(menus.getBoolean("inspect.decoration.enabled")) {
            ItemStack item = buildItem("decoration", menus.getStringList("inspect.decoration.lore"));
            for(int i = 0; i < 54; i++) {
                defaultItems.put(i, item);
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
        return menus.getString("inspect." + item + ".id");
    }
    private byte getData (String item) {
        return (byte) menus.getInt("inspect." + item + ".data");
    }
    private int getAmount (String item) {
        return menus.getInt("inspect." + item + ".amount");
    }
    private String getName (String item) {
        return menus.getString("inspect." + item + ".name");
    }

    private boolean getGlow (String item) {
        return menus.getBoolean("inspect." + item + ".gow");
    }
    private int getSlot (String item) {
        return menus.getInt("inspect." + item + ".slot");
    }
    private SkullType getSkullType (String item) {
        return SkullType.valueOf(menus.getString("inspect." + item + ".skull.type"));
    }
    private String getSkullId (String item) {
        return menus.getString("inspect." + item + ".skull.id");
    }

    private String duration (int i) {
        int seconds = i / 20;

        int minutes = seconds / 60;

        int secondsLeft = seconds % 60;

        return minutes + ":" + secondsLeft;
    }
}
