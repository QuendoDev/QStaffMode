package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.service.Service;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.LeaveInformation;
import com.quendo.qstaffmode.models.StaffInformation;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

public class StorageService implements Service {

    @Inject
    @Named("config")
    private YMLFile config;

    @Inject
    private Storage<UUID, StaffInformation> staffInformationStorage;

    @Inject
    private Storage<UUID, LeaveInformation> leaveInformationStorage;

    @Override
    public void start() {
        staffInformationStorage.loadAll();
        if (config.getBoolean("autoEnableBySavedInfo")) {
            leaveInformationStorage.loadAll();
        }
    }

    @Override
    public void stop() {
        staffInformationStorage.saveAll();
        if (config.getBoolean("autoEnableBySavedInfo")) {
            leaveInformationStorage.saveAll();
        }
    }
}