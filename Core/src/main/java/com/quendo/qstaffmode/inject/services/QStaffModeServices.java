package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.inject.loader.*;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class QStaffModeServices implements Service {

    private ListenerLoader listenerLoader;
    private CommandLoader commandsLoader;
    private CooldownLoader cooldownLoader;
    private FilesLoader filesLoader;
    private ItemsLoader itemsLoader;

    @Named("storage-service")
    private Service storageService;

    @Override
    public void start() {
        listenerLoader.load();
        commandsLoader.load();
        cooldownLoader.load();
        filesLoader.load();
        itemsLoader.load();

        storageService.start();
    }

    @Override
    public void stop() {
        storageService.stop();
    }
}
