package com.quendo.qstaffmode.listener.inventory;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.common.Utils;
import com.quendo.qstaffmode.manager.StaffModeManager;
import com.quendo.qstaffmode.menus.pages.PageTracker;
import com.quendo.qstaffmode.menus.pages.PlayerPage;
import com.quendo.qstaffmode.manager.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitScheduler;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class InventoryListener implements Listener {

    @Named("menus")
    private OldYMLFile menus;

    @Named("config")
    private OldYMLFile config;

    private Utils utils;

    private QStaffMode qStaffMode;

    private StaffModeManager staffModeManager;
    private MenuManager menuManager;
    private PageTracker pageTracker;

    @EventHandler
    public void onClick (InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            String invName = ChatColor.stripColor(utils.getInventoryName(e));
            //////////////////MAIN MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("main.title")).equals(invName)) {
                mainMenu(e);
                return;
            }

            //////////////////AVAILABLE STAFF MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("availableStaff.title")).equals(invName)) {
                availableMenu(e);
                return;
            }

            //////////////////UNAVAILABLE STAFF MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("unavailableStaff.title")).equals(invName)) {
                unavailableMenu(e);
                return;
            }

            //////////////////TP MAIN MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("tpItemMenu.title")).equals(invName)) {
                tpMenu(e);
                return;
            }

            //////////////////TP MINING PLAYERS MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("inMiningLayers.title")).equals(invName)) {
                miningMenu(e);
                return;
            }

            //////////////////INVSEE MENU////////////////////////
            if (ChatColor.stripColor(menus.getString("inspect.title")).equals(invName)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose (InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        pageTracker.removePlayerPage(p);
    }

    private boolean checks (InventoryClickEvent e) {
        return e.getCurrentItem() != null && e.getSlotType() != null && !e.getCurrentItem().getType().equals(Material.AIR);
    }

    private boolean itemClickedEqualsItem (ItemStack item, String id) {
        return utils.getMaterial(item.getType().name()) == utils.getMaterial(id);
    }

    private void mainMenu(InventoryClickEvent e) {
        e.setCancelled(true);
        if (checks(e)) {
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {

                if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("main.inStaffMode.id"))
                        && p.hasPermission("qstaffmode.menu.availablestaff.open")) {
                    Bukkit.getScheduler().runTaskLater(qStaffMode,
                            () -> menuManager.openAvailableStaffMenu(p, 1),
                            1);

                }

                if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("main.withoutStaffMode.id"))
                        && p.hasPermission("qstaffmode.menu.unavailablestaff.open")) {
                    Bukkit.getScheduler().runTaskLater(qStaffMode,
                            () -> menuManager.openUnavailableStaffMenu(p, 1),
                            1);
                }
            }
        }
    }

    private void availableMenu (InventoryClickEvent e) {
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
                        int finalPage = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openAvailableStaffMenu(p, finalPage),
                                1);
                        pageTracker.modifyPlayerPage(p, page);
                    }

                    //CHECK IF ITEM IS PREVIOUS PAGE ITEM.
                    String ppos = menus.getString("availableStaff.previousPage.slot");
                    int prevPageSlot = ppos.equals("corner") ?
                            size - 9 : ppos.equals("middle") ?
                            size - 6 : -1;

                    if (slot == prevPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("availableStaff.previousPage.id"))) {
                        page--;
                        int finalPage = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openAvailableStaffMenu(p, finalPage),
                                1);
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

    private void unavailableMenu (InventoryClickEvent e) {
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
                        int finalPage = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openUnavailableStaffMenu(p, finalPage),
                                1);
                        pageTracker.modifyPlayerPage(p, page);
                    }

                    //CHECK IF ITEM IS PREVIOUS PAGE ITEM.
                    String ppos = menus.getString("unavailableStaff.previousPage.slot");
                    int prevPageSlot = ppos.equals("corner") ?
                            size - 9 : ppos.equals("middle") ?
                            size - 6 : -1;

                    if (slot == prevPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("unavailableStaff.previousPage.id"))) {
                        page--;
                        int finalPage = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openUnavailableStaffMenu(p, finalPage),
                                1);
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

    private void tpMenu (InventoryClickEvent e) {
        e.setCancelled(true);
        if (checks(e)) {
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("tpItemMenu.tpRandomPlayer.id"))
                        && p.hasPermission("qstaffmode.menu.tp.random")) {
                    staffModeManager.teleportToRandomplayer(p, config.getBoolean ("randomTpMultiworld"));
                }

                if (itemClickedEqualsItem(e.getCurrentItem(), menus.getString("tpItemMenu.miningLayersPlayers.id"))
                        && p.hasPermission("qstaffmode.menu.tp.mining")) {
                    Bukkit.getScheduler().runTaskLater(qStaffMode,
                            () -> menuManager.openMiningMenu(p, 1),
                            1);
                }
            }
        }
    }

    private void miningMenu (InventoryClickEvent e) {
        e.setCancelled(true);
        if (checks(e)) {
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory().equals(p.getOpenInventory().getTopInventory())) {
                PlayerPage playerInventory = pageTracker.getPlayerPage(p);
                if (playerInventory != null) {
                    int page = playerInventory.getPage();
                    int slot = e.getSlot();
                    int size = menus.getInt("inMiningLayers.size");

                    //CHECK IF ITEM IS NEXT PAGE ITEM.
                    String npos = menus.getString("inMiningLayers.nextPage.slot");
                    int nextPageSlot = npos.equals("corner") ?
                            size - 1 : npos.equals("middle") ?
                            size - 4 : -1;

                    if (slot == nextPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("inMiningLayers.nextPage.id"))) {
                        page++;
                        int finalPage = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openMiningMenu(p, finalPage),
                                1);
                        pageTracker.modifyPlayerPage(p, page);
                    }

                    //CHECK IF ITEM IS PREVIOUS PAGE ITEM.
                    String ppos = menus.getString("inMiningLayers.previousPage.slot");
                    int prevPageSlot = ppos.equals("corner") ?
                            size - 9 : ppos.equals("middle") ?
                            size - 6 : -1;

                    if (slot == prevPageSlot && itemClickedEqualsItem(e.getCurrentItem(), menus.getString("inMiningLayers.previousPage.id"))) {
                        page--;
                        int finalPage1 = page;
                        Bukkit.getScheduler().runTaskLater(qStaffMode,
                                () -> menuManager.openMiningMenu(p, finalPage1),
                                1);
                        pageTracker.modifyPlayerPage(p, page);
                    }

                    //CHECK IF ITEM IS PLAYER SKULL.
                    if (utils.isPlayerHead(e.getCurrentItem())) {
                        if (p.hasPermission("qstaffmode.menu.tp.teleport")) {
                            ItemStack i = e.getCurrentItem();
                            SkullMeta m = (SkullMeta) i.getItemMeta();
                            p.teleport(Bukkit.getPlayer(m.getOwner()));
                        }
                    }
                }
            }
        }
    }
}
