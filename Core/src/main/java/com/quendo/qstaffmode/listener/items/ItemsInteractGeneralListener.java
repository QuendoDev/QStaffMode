package com.quendo.qstaffmode.listener.items;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.common.Utils;
import com.quendo.qstaffmode.events.*;
import com.quendo.qstaffmode.manager.StaffModeManager;
import com.quendo.qstaffmode.utils.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Set;

@InjectAll
public class ItemsInteractGeneralListener implements Listener {

    private StaffModeManager staffModeManager;
    private Utils utils;

    @Named("items")
    private OldYMLFile items;

    @Named("messages")
    private OldYMLFile messages;

    @EventHandler
    public void interactEntity (PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(e.getRightClicked() instanceof Player) {
            Player clicked = (Player) e.getRightClicked();
            if (staffModeManager.isInStaffMode(p)) {
                if (p.hasPermission("qstaffmode.useitems")) {

                    ////////////******INSPECT******/////////////
                    if (itemInHandequalsItem(e, "inspect") && p.hasPermission("qstaffmode.items.inspect")) {
                        if (!clicked.hasPermission("qstaffmode.bypass.inspect")) {
                            Bukkit.getServer().getPluginManager().callEvent(new InspectInteractEvent(p, clicked));
                        } else {
                            MessageUtil.sendMessage(p, messages.getString("noPerms"));
                        }
                    }

                    ////////////******FREEZE******/////////////
                    if (itemInHandequalsItem(e, "freeze") && p.hasPermission("qstaffmode.items.freeze")) {
                        if (!clicked.hasPermission("qstaffmode.bypass.freeze")) {
                            Bukkit.getServer().getPluginManager().callEvent(new FreezeInteractEvent(p, clicked));
                        } else {
                            MessageUtil.sendMessage(p, messages.getString("noPerms"));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAction(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getClickedBlock() != null &&
                (e.getClickedBlock().getType() == Material.CHEST ||
                        e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {
            return;
        }

        if (staffModeManager.isInStaffMode(p)) {
            if (p.hasPermission("qstaffmode.useitems") && e.getItem() != null && utils.getItemInHand(e) != null) {

                ////////////******NAVIGATOR******/////////////
                if (itemInHandequalsItem(e, "navigator") && p.hasPermission("qstaffmode.items.navigator") && e.getAction() != null) {
                    if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        Bukkit.getServer().getPluginManager().callEvent(new NavigatorInteractEvent(p, ActionType.LEFT_CLICK, p.getLocation()));
                    }
                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        try {
                            if (p.getTargetBlock((Set<Material>) null, 25) != null) {
                                Block block = p.getTargetBlock((Set<Material>) null, 25);
                                if (block != null) {
                                    Bukkit.getServer().getPluginManager().callEvent(new NavigatorInteractEvent(p, ActionType.RIGHT_CLICK, new Location(block.getWorld(),
                                            block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch())));
                                }
                            }
                        } catch (IllegalStateException ignored) {

                        }
                    }
                    e.setCancelled(true);
                    return;
                }

                ////////////******STAFFLIST******/////////////
                if (itemInHandequalsItem(e, "staffList") && p.hasPermission("qstaffmode.items.stafflist")) {
                    if(e.getAction() != null ) {
                        Bukkit.getServer().getPluginManager().callEvent(new StaffListInteractEvent(p));
                    }
                    e.setCancelled(true);
                    return;
                }

                ////////////******VANISH******/////////////
                if ((itemInHandequalsItem(e, "vanish") || itemInHandequalsItem(e, "visible")) && p.hasPermission("qstaffmode.items.vanish")) {
                    if(e.getAction() != null ) {
                        Bukkit.getServer().getPluginManager().callEvent(new VanishInteractEvent(p));
                    }
                    e.setCancelled(true);
                    return;
                }

                ////////////******FLY******/////////////
                if (itemInHandequalsItem(e, "fly") && p.hasPermission("qstaffmode.items.fly")) {
                    if(e.getAction() != null ) {
                        Bukkit.getServer().getPluginManager().callEvent(new FlyInteractEvent(p));
                    }
                    e.setCancelled(true);
                    return;
                }

                ////////////******RANDOMTP******/////////////
                if(itemInHandequalsItem(e, "randomtp") && p.hasPermission("qstaffmode.items.randomtp")) {
                    if(e.getAction() != null ) {
                        Bukkit.getServer().getPluginManager().callEvent(new RandomTpInteractEvent(p));
                    }
                    e.setCancelled(true);
                    return;
                }

                ////////////******KBFISHINGROD******/////////////
                if (itemInHandequalsItem(e, "kbFishingRod") && p.hasPermission("qstaffmode.items.kbfishingrod")) {
                    MessageUtil.sendMessage(p, messages.getString("usedFishingRod"));
                    return;
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void attack (EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player target = (Player) event.getEntity();
            if (staffModeManager.isInStaffMode(attacker) && !staffModeManager.isInStaffMode(target)) {
                if (itemInHandequalsItem(utils.getItemInHand(attacker), "kbFishingRod") &&
                        attacker.hasPermission("qstaffmode.items.kbfishingrod")) {
                    target.setVelocity(attacker.getLocation().getDirection().multiply(3));
                }
            }
        }
    }

    private boolean itemInHandequalsItem (ItemStack itemStack, String key) {
        boolean metaAndName = itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName();
        boolean sameName = itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().equals(items.getString("items." + key + ".name"));
        boolean sameId = utils.getMaterial(itemStack.getType().name()) == utils.getMaterial(items.getString("items." + key + ".id"));
        return metaAndName && sameName && sameId;
    }

    private boolean itemInHandequalsItem (PlayerEvent e, String key) {
        boolean metaAndName = utils.getItemInHand(e).hasItemMeta() && utils.getItemInHand(e).getItemMeta().hasDisplayName();
        boolean sameName = utils.getItemInHand(e).hasItemMeta() && utils.getItemInHand(e).getItemMeta().getDisplayName().equals(items.getString("items." + key + ".name"));
        boolean sameId = utils.getMaterial(utils.getItemInHand(e).getType().name()) == utils.getMaterial(items.getString("items." + key + ".id"));
        return metaAndName && sameName && sameId;
    }

}
