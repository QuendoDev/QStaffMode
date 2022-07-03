package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.inject.loader.*;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import team.unnamed.inject.InjectAll;

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

    }

    @Override
    public void stop() {
        storageService.stop();
        servicesRegistrationService.stop();
    }
}
