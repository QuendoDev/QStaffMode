package com.quendo.qstaffmode.inject.loader;

import com.quendo.qore.setup.Loader;
import com.quendo.qstaffmode.menus.InspectMenu;
import com.quendo.qstaffmode.menus.stafflist.StaffListMainMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.InStaffModeMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.WithoutStaffModeMenu;
import team.unnamed.inject.InjectAll;


@InjectAll
public class MenuLoader implements Loader {

    private InspectMenu inspectMenu;
    private StaffListMainMenu staffListMainMenu;
    private WithoutStaffModeMenu withoutStaffModeMenu;
    private InStaffModeMenu inStaffModeMenu;

    @Override
    public void load() {
        inspectMenu.create();
        staffListMainMenu.create();
        withoutStaffModeMenu.create();
        inStaffModeMenu.create();
    }
}
