package com.quendo.qstaffmode.listener.basic;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;

@InjectAll
public class StaffModeBasicListener implements Listener {

    @Named("config")
    private YMLFile config;

    private StaffModeManager staffModeManager;

    private QStaffMode qStaffMode;

    @Named("messages")
    private YMLFile messages;

    @EventHandler
    public void onAction(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && staffModeManager.isVanished(p)
                && config.getBoolean("chestSoundCancel")
                && p.hasPermission("qstaffmode.silentchest")
                && (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {

            e.setCancelled(true);
            Block chest = e.getClickedBlock();
            Inventory inventory = ((Chest) chest.getState()).getInventory();
            Inventory clone = Bukkit.createInventory(null, inventory.getSize(), ChatColor.translateAlternateColorCodes('&', "&aSilent Chest"));
            clone.setContents(inventory.getContents());
            MessageUtils.sendMessage(p, messages.getString("openChestSilent"));
            p.openInventory(clone);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(e.getEntity() != null && e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            //If damager is frozen or is on staffmode, damage will be cancelled.
            if (staffModeManager.isInStaffMode((Player) e.getDamager()) || staffModeManager.isFrozen((Player) e.getDamager())) {
                e.setCancelled(true);
            }
            //If damaged entity is frozen or is on staffmode, damage will be cancelled.
            if (staffModeManager.isInStaffMode((Player) e.getEntity()) || staffModeManager.isFrozen((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        //If player is frozen or is on staffmode, placing blocks will be cancelled.
        if (staffModeManager.isInStaffMode(e.getPlayer()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();
            //If player is frozen or is on staffmode, mobs targeting will be cancelled.
            if (staffModeManager.isInStaffMode(p) || staffModeManager.isFrozen(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e) {
        //If player is frozen or is on staffmode, removing blocks will be cancelled.
        if (staffModeManager.isInStaffMode(e.getPlayer()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        //If player is frozen or is on staffmode, picking up items will be cancelled.
        if (staffModeManager.isInStaffMode(e.getPlayer()) || staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        //If player is frozen (IF "dropItemsWhileFrozen" IS FALSE) or is on staffmode, dropping items will be cancelled.
        if (staffModeManager.isInStaffMode(e.getPlayer())) {
            e.setCancelled(true);
        }
        if (!config.getBoolean("dropItemsWhileFrozen") && staffModeManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            //If player is frozen or is on staffmode, damage will be cancelled.
            if (staffModeManager.isInStaffMode(p) || staffModeManager.isFrozen(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            if (staffModeManager.isInStaffMode(p)) {
                if(e.getClickedInventory() != null) {
                    e.setCancelled(true);
                }
            }
            if (!config.getBoolean("moveInventoryWhileFrozen") && staffModeManager.isFrozen(p)) {
                if(e.getClickedInventory() != null) {
                    if (e.getClickedInventory().equals(p.getInventory())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}

