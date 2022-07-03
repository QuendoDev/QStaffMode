package com.quendo.qstaffmode;

import com.kino.kore.utils.PluginUtils;
import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.inject.modules.MainModule;
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


    @Override
    public void onEnable() {
        Injector injector = Injector.create(new MainModule(this));
        injector.injectMembers(this);

        qsmService.start();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "QStaffMode " + PluginUtils.getVersion(this) + " enabled");
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

    qstaffmode.commands.fly.toogle
    qstaffmode.commands.fly.on
    qstaffmode.commands.fly.off

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

    qstaffmode.commands.staffchat.toogle
    qstaffmode.commands.staffchat.on
    qstaffmode.commands.staffchat.off

    qstaffmode.commands.vanish.toogle
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

    qstaffmode.data.save
    qstaffmode.data.read

    qstaffmode.staffmode.keepflying
    qstaffmode.staffmode.autoenable

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
    qstaffmode.message.enabledstaffmode
    qstaffmode.silentchest
     */
}
