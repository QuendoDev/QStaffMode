package com.quendo.qstaffmode.listener.inventory;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.api.Utils;
import com.quendo.qstaffmode.menus.stafflist.submenus.pages.PageTracker;
import com.quendo.qstaffmode.menus.stafflist.submenus.pages.PlayerPage;
import com.quendo.qstaffmode.staffmode.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class InventoryListener implements Listener {

    @Named("menus")
    private YMLFile menus;

    private Utils utils;

    private MenuManager menuManager;
    private PageTracker pageTracker;

    @EventHandler
    public void onClick (InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            String invName = utils.getInventoryName(e);
            //////////////////MAIN MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("main.title")).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
                if (checks(e)) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {

                        if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("main.inStaffMode.id"))
                        && p.hasPermission("qstaffmode.menu.availablestaff.open")) {
                            menuManager.openAvailableStaffMenu(p, 1);
                        }

                        if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("main.withoutStaffMode.id"))
                                && p.hasPermission("qstaffmode.menu.unavailablestaff.open")) {
                            menuManager.openUnavailableStaffMenu(p, 1);
                        }
                    }
                }
            }

            //////////////////AVAILABLE STAFF MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("availableStaff.title")).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
                if (checks(e)) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                        PlayerPage playerInventory = pageTracker.getPlayerPage(p);
                        if (playerInventory != null) {
                            int page = playerInventory.getPage();
                            int slot = e.getSlot();
                            int size = menus.getInt("availableStaff.size");

                            //CHECK IF ITEM IS NEXT PAGE ITEM.
                            String npos = menus.getString("availableStaff.nextPage.slot");
                            int nextPageSlot = npos.equals("corner") ?
                                    size - 1 : npos.equals("middle") ?
                                    size - 4 : -1;

                            if (slot == nextPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("availableStaff.nextPage.id"))) {
                                page++;
                                menuManager.openAvailableStaffMenu(p, page);
                                pageTracker.modifyPlayerPage(p, page);
                            }

                            //CHECK IF ITEM IS PREVIOUS PAGE ITEM.
                            String ppos = menus.getString("availableStaff.previousPage.slot");
                            int prevPageSlot = ppos.equals("corner") ?
                                    size - 9 : ppos.equals("middle") ?
                                    size - 6 : -1;

                            if (slot == prevPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("availableStaff.previousPage.id"))) {
                                page--;
                                menuManager.openAvailableStaffMenu(p, page);
                                pageTracker.modifyPlayerPage(p, page);
                            }

                            //CHECK IF ITEM IS PLAYER SKULL.
                            if (utils.isPlayerHead(e.getCurrentItem())) {
                                if (p.hasPermission("qstaffmode.menu.availablestaff.teleport")) {
                                    ItemStack i = e.getCurrentItem();
                                    SkullMeta m = (SkullMeta) i.getItemMeta();
                                    p.teleport(Bukkit.getPlayer(m.getOwner()));
                                }
                            }
                        }
                    }
                }
            }

            //////////////////unAVAILABLE STAFF MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("unavailableStaff.title")).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
                if (checks(e)) {
                    Player p = (Player) e.getWhoClicked();
                    if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                        PlayerPage playerInventory = pageTracker.getPlayerPage(p);
                        if (playerInventory != null) {
                            int page = playerInventory.getPage();
                            int slot = e.getSlot();
                            int size = menus.getInt("unavailableStaff.size");

                            //CHECK IF ITEM IS NEXT PAGE ITEM.
                            String npos = menus.getString("unavailableStaff.nextPage.slot");
                            int nextPageSlot = npos.equals("corner") ?
                                    size - 1 : npos.equals("middle") ?
                                    size - 4 : -1;

                            if (slot == nextPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("unavailableStaff.nextPage.id"))) {
                                page++;
                                menuManager.openUnavailableStaffMenu(p, page);
                                pageTracker.modifyPlayerPage(p, page);
                            }

                            //CHECK IF ITEM IS PREVIOUS PAGE ITEM.
                            String ppos = menus.getString("unavailableStaff.previousPage.slot");
                            int prevPageSlot = ppos.equals("corner") ?
                                    size - 9 : ppos.equals("middle") ?
                                    size - 6 : -1;

                            if (slot == prevPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("unavailableStaff.previousPage.id"))) {
                                page--;
                                menuManager.openUnavailableStaffMenu(p, page);
                                pageTracker.modifyPlayerPage(p, page);
                            }

                            //CHECK IF ITEM IS PLAYER SKULL.
                            if (utils.isPlayerHead(e.getCurrentItem())) {
                                if (p.hasPermission("qstaffmode.menu.unavailableStaff.teleport")) {
                                    ItemStack i = e.getCurrentItem();
                                    SkullMeta m = (SkullMeta) i.getItemMeta();
                                    p.teleport(Bukkit.getPlayer(m.getOwner()));
                                }
                            }
                        }
                    }
                }
            }


            //////////////////INVSEE MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("inspect.title")).equals(ChatColor.stripColor(invName))) {
                e.setCancelled(true);
            }
        }
    }

    private boolean checks (InventoryClickEvent e) {
        return e.getCurrentItem() != null && e.getSlotType() != null && !e.getCurrentItem().getType().equals(Material.AIR);
    }

    private boolean itemClickedEqualsItem (ItemStack item, String id) {
        return utils.getMaterial(item.getType().name()) == utils.getMaterial(id);
    }
}
