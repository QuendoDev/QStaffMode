package com.quendo.qstaffmode.inject.modules;

import com.quendo.qstaffmode.menus.InspectMenu;
import com.quendo.qstaffmode.menus.stafflist.StaffListMainMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.InStaffModeMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.WithoutStaffModeMenu;
import com.quendo.qstaffmode.menus.stafflist.submenus.pages.PageTracker;
import com.quendo.qstaffmode.staffmode.MenuManager;
import team.unnamed.inject.AbstractModule;


public class MenuModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PageTracker.class).singleton();
        bind(InspectMenu.class).singleton();
        bind(StaffListMainMenu.class).singleton();
        bind(WithoutStaffModeMenu.class).singleton();
        bind(InStaffModeMenu.class).singleton();
        bind(MenuManager.class).singleton();
    }
}
