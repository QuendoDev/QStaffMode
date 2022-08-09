package com.quendo.qstaffmode.v1_8_R3;

import com.quendo.qstaffmode.common.Utils;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Utilsv1_8_R3 implements Utils {


    @Override
    public ItemStack getItemInHand(Player p) {
        return p.getItemInHand();
    }

    @Override
    public ItemStack getItemInHand (PlayerEvent e) {
        return e.getPlayer().getItemInHand();
    }

    @Override
    public double getTPS () {
        return MinecraftServer.getServer().recentTps[0];
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

    @Override
    public void sendActionBar(Player p, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
