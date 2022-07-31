package com.quendo.qstaffmode;


import com.quendo.qore.setup.Service;
import com.quendo.qore.utils.bukkit.PluginUtil;
import com.quendo.qstaffmode.inject.modules.MainModule;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;

public class QStaffMode extends JavaPlugin {

    @Inject
    @Named("qsm-service")
    private Service qsmService;

    @Inject
    private StaffModeManager staffModeManager;

    @Override
    public void onEnable() {
        Injector injector = Injector.create(new MainModule(this));
        injector.injectMembers(this);

        qsmService.start();

        staffModeManager.setup(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "QStaffMode " + PluginUtil.getVersion(this) + " enabled");
    }

    @Override
    public void onDisable() {
        qsmService.stop();
    }

    /*
    qstaffmode.commands.main.*
    qstaffmode.commands.fly.*
    qstaffmode.commands.vanish.*
    qstaffmode.commands.gamemode.*
    qstaffmode.commands.staffchat.*
    qstaffmode.commands.chat.*

    qstaffmode.commands.fly.toggle
    qstaffmode.commands.fly.on
    qstaffmode.commands.fly.off

    qstaffmode.commands.chat.lock
    qstaffmode.commands.chat.unlock
    qstaffmode.commands.chat.toggle
    qstaffmode.commands.chat.slowmode
    qstaffmode.commands.chat.clear

    qstaffmode.commands.gamemode.creative
    qstaffmode.commands.gamemode.spectator
    qstaffmode.commands.gamemode.survival
    qstaffmode.commands.gamemode.adventure

    qstaffmode.commands.gamemode.creative.others
    qstaffmode.commands.gamemode.spectator.others
    qstaffmode.commands.gamemode.survival.others
    qstaffmode.commands.gamemode.adventure.others

    qstaffmode.commands.main.info

    qstaffmode.commands.freeze

    qstaffmode.commands.invsee

    qstaffmode.commands.teleport

    qstaffmode.commands.staffchat.toggle
    qstaffmode.commands.staffchat.on
    qstaffmode.commands.staffchat.off

    qstaffmode.commands.vanish.toggle
    qstaffmode.commands.vanish.on
    qstaffmode.commands.vanish.off

    qstaffmode.commands.staffmode

    qstaffmode.bypass.freeze
    qstaffmode.bypass.inspect
    qstaffmode.bypass.vanish

    qstaffmode.items.fly
    qstaffmode.items.randomtp
    qstaffmode.items.vanish
    qstaffmode.items.stafflist
    qstaffmode.items.inspect
    qstaffmode.items.freeze
    qstaffmode.items.navigator
    qstaffmode.items.kbfishingrod

    qstaffmode.menu.availablestaff.open
    qstaffmode.menu.unavailablestaff.open
    qstaffmode.menu.availablestaff.teleport
    qstaffmode.menu.unavailableStaff.teleport
    qstaffmode.menu.tp.random
    qstaffmode.menu.tp.mining
    qstaffmode.menu.tp.teleport

    qstaffmode.data.save
    qstaffmode.data.read

    qstaffmode.staffmode.keepflying
    qstaffmode.staffmode.autoenable

    qstaffmode.chat.lock.bypass
    qstaffmode.chat.slowmode.bypass

    qstaffmode.frozendisconnect
    qstaffmode.useitems
    qstaffmode.staff
    qstaffmode.vanish
    qstaffmode.randomtp
    qstaffmode.freeze
    qstaffmode.inspect
    qstaffmode.staffchat
    qstaffmode.staffchat.read
    qstaffmode.staffmode
    qstaffmode.fly
    qstaffmode.stafflist
    qstaffmode.stafflist.availablestaff
    qstaffmode.stafflist.unavailablestaff
    qstaffmode.tpmenu
    qstaffmode.tpmenu.mining
    qstaffmode.message.enabledstaffmode
    qstaffmode.silentchest

     */
}
