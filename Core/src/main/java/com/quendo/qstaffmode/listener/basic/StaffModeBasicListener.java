package com.quendo.qstaffmode.listener.basic;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class StaffModeBasicListener implements Listener {

    @Named("config")
    private YMLFile config;

    private StaffModeManager staffModeManager;

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
                    if (e.getClickedInventory().equals(p.getInventory())) {
                        e.setCancelled(true);
                    }
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

