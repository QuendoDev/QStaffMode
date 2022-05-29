package com.quendo.qstaffmode.staffmode;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.StaffInformation;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class StaffModeManager {

    @Named("messages")
    private YMLFile messages;

    @Inject
    private Storage<UUID, StaffInformation> inStaffMode;

    @Inject private ItemManager itemManager;

    @Getter private final List<UUID> frozen = new ArrayList<>();
    @Getter private final List<UUID> vanished = new ArrayList<>();
    @Getter private final List<UUID> flying = new ArrayList<>();

    private void clearInventory (Player p){
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public void giveStaffItems (Player p) {
        itemManager.giveItems(p, vanished.contains(p.getUniqueId()));
    }

    public void returnPlayerItems (Player p) {
        Optional<StaffInformation> f = inStaffMode.find(p.getUniqueId());
        ItemStack[] armor = f.map(StaffInformation::getArmor).orElse(null);
        ItemStack[] items = f.map(StaffInformation::getInventoryItems).orElse(null);
        p.getInventory().setContents(items);
        p.getInventory().setArmorContents(armor);
    }

    public void toogleFly (Player p) {
        if(p.hasPermission("qstaffmode.fly")){
            if (flying.contains(p.getUniqueId())) {
                flying.add(p.getUniqueId());
                p.setAllowFlight(true);
                p.setFlying(true);
                MessageUtils.sendMessage(p, messages.getString("enabledFly"));
            } else {
                flying.remove(p.getUniqueId());
                p.setAllowFlight(false);
                p.setFlying(false);
                MessageUtils.sendMessage(p, messages.getString("disabledFly"));
            }
        }
    }
}
