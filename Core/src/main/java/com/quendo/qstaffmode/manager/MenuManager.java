package com.quendo.qstaffmode.manager;

import com.quendo.qstaffmode.menus.InspectMenu;
import com.quendo.qstaffmode.menus.stafflist.StaffListMainMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.InStaffModeMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.WithoutStaffModeMenu;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;


@InjectAll
public class MenuManager {

    private InspectMenu inspectMenu;
    private StaffListMainMenu staffListMainMenu;
    private InStaffModeMenu inStaffModeMenu;
    private WithoutStaffModeMenu withoutStaffModeMenu;

    public void openInspectMenu (Player p, Player interacted) {
        if (p.hasPermission("qstaffmode.inspect")) {
            inspectMenu.open(p, interacted);
        }
    }

    public void openStaffListMainMenu (Player p) {
        if (p.hasPermission("qstaffmode.stafflist")) {
            staffListMainMenu.open(p);
        }
    }

    public void openAvailableStaffMenu (Player p, int page) {
        if (p.hasPermission("qstaffmode.stafflist.availablestaff")) {
            inStaffModeMenu.open(p, page);
        }
    }

    public void openUnavailableStaffMenu (Player p, int page) {
        if (p.hasPermission("qstaffmode.stafflist.unavailablestaff")) {
            withoutStaffModeMenu.open(p, page);
        }
    }
}
