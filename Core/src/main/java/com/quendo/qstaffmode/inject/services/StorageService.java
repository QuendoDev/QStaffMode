package com.quendo.qstaffmode.inject.services;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.setup.Service;
import com.quendo.qore.storage.Storage;
import com.quendo.qstaffmode.models.data.LeaveInformation;
import com.quendo.qstaffmode.models.data.StaffInformation;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

public class StorageService implements Service {

    @Inject
    @Named("config")
    private OldYMLFile config;

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