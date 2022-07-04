package com.quendo.qstaffmode.inject.services;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.setup.Service;
import com.quendo.qstaffmode.PlaceholderAPI;
import com.quendo.qstaffmode.inject.loader.*;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;

@InjectAll
public class QStaffModeServices implements Service {

    private ListenerLoader listenerLoader;
    private CommandLoader commandsLoader;
    private CooldownLoader cooldownLoader;
    private FilesLoader filesLoader;
    private ItemsLoader itemsLoader;
    private MenuLoader menuLoader;

    @Named("storage-service")
    private Service storageService;

    @Named("registration-service")
    private Service servicesRegistrationService;

    @Named("scoreboard")
    private OldYMLFile scoreboard;

    private StaffModeManager staffModeManager;

    @Override
    public void start() {
        listenerLoader.load();
        commandsLoader.load();
        cooldownLoader.load();
        filesLoader.load();
        itemsLoader.load();
        menuLoader.load();

        storageService.start();
        servicesRegistrationService.start();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI(staffModeManager, scoreboard).register();
        }
    }

    @Override
    public void stop() {
        storageService.stop();
        servicesRegistrationService.stop();

        for (Player p : Bukkit.getOnlinePlayers()) {
            staffModeManager.getScoreboardMap().remove(p.getUniqueId());
        }
    }
}
