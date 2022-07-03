package com.quendo.qstaffmode.manager;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import com.quendo.qstaffmode.menus.InspectMenu;
import com.quendo.qstaffmode.menus.stafflist.StaffListMainMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.InStaffModeMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.WithoutStaffModeMenu;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;


@InjectAll
public class MenuManager {

    @Named ("messages")
    private YMLFile messages;

    private InspectMenu inspectMenu;
    private StaffListMainMenu staffListMainMenu;
    private InStaffModeMenu inStaffModeMenu;
    private WithoutStaffModeMenu withoutStaffModeMenu;

    public void openInspectMenu (Player p, Player interacted) {
        if (p.hasPermission("qstaffmode.inspect")) {
            inspectMenu.open(p, interacted);
            MessageUtils.sendMessage(p, messages.getString("openingInventory").replace("<player>", interacted.getDisplayName()));
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
