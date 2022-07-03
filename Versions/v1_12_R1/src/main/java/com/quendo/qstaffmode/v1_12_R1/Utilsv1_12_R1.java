package com.quendo.qstaffmode.v1_12_R1;

import com.quendo.qstaffmode.api.Utils;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Utilsv1_12_R1 implements Utils {

    @Override
    public ItemStack getItemInHand(PlayerEvent e) {
        if(e instanceof PlayerInteractEntityEvent) {
            if (((PlayerInteractEntityEvent) e).getHand() == EquipmentSlot.HAND) {
                return e.getPlayer().getInventory().getItemInMainHand();
            }
        }
        if(e instanceof PlayerInteractEvent) {
            if(((PlayerInteractEvent) e).getHand() == EquipmentSlot.HAND) {
                return e.getPlayer().getInventory().getItemInMainHand();
            }
        }

        return new ItemStack(Material.AIR);
    }

    @Override
    public double getTPS () {
        if (Bukkit.getServer() instanceof CraftServer) {
            MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            return server.recentTps[0];
        }

        return 0.0D;
    }

    @Override
    public String getInventoryName (InventoryClickEvent inventoryClickEvent) {
        return inventoryClickEvent.getView().getTitle();
    }

    @Override
    public Material getMaterial (String id) {
        return Material.getMaterial(id) == null ? Material.STONE : Material.getMaterial(id);
    }

    @Override
    public boolean isSkull (ItemStack itemStack) {
        return itemStack.getType() == Material.SKULL_ITEM;
    }

    @Override
    public boolean isPlayerHead (ItemStack itemStack) {
        return isSkull(itemStack) && itemStack.getDurability() == 3 && itemStack.hasItemMeta() && ((SkullMeta)itemStack.getItemMeta()).hasOwner();
    }
}
