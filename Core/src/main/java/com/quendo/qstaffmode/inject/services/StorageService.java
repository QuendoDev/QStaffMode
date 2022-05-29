package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.service.Service;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.StaffInformation;

import javax.inject.Inject;
import java.util.UUID;

public class StorageService implements Service {

    @Inject
    private Storage<UUID, StaffInformation> staffInformationStorage;

    /*@Inject
    private Storage<String, Game> arenaStorage;*/

    @Override
    public void start() {
        staffInformationStorage.loadAll();
        /*arenaStorage.loadAll();*/
    }

    @Override
    public void stop() {
        staffInformationStorage.saveAll();
        /*arenaStorage.saveAll();*/
    }
}