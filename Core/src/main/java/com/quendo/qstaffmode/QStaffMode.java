package com.quendo.qstaffmode;

import com.kino.kore.utils.PluginUtils;
import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.inject.modules.MainModule;
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

        getLogger().info(ChatColor.GREEN + "QStaffMode " + PluginUtils.getVersion(this) + " enabled");
    }

    @Override
    public void onDisable() {
        qsmService.stop();
    }

    /*
    qstaffmode.useitems
    qstaffmode.items.fly
    qstaffmode.items.randomtp
    qstaffmode.items.vanish
    qstaffmode.items.stafflist
    qstaffmode.items.inspect
    qstaffmode.items.freeze
    qstaffmode.items.navigator
    qstaffmode.items.kbFishingRod
    qstaffmode.staffmode
    qstaffmode.staffmode.keepflying
    qstaffmode.staffmode.autoenable
    qstaffmode.data.save
    qstaffmode.data.read
    qstaffmode.fly
    qstaffmode.vanish
    qstaffmode.randomtp
    qstaffmode.freeze
    qstaffmode.inspect
    qstaffmode.bypass.vanish
    qstaffmode.bypass.freeze
    qstaffmode.bypass.inspect
    qstaffmode.frozendisconnect
     */
}
