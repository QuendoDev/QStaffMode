package com.quendo.qstaffmode.inject.modules;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.manager.PacketManager;
import com.quendo.qstaffmode.models.data.LeaveInformation;
import com.quendo.qstaffmode.models.data.StaffInformation;
import com.quendo.qstaffmode.manager.ItemManager;
import com.quendo.qstaffmode.manager.StaffModeManager;
import com.quendo.qstaffmode.storage.LeaveStorageManager;
import com.quendo.qstaffmode.storage.StaffModeStorageManager;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;


public class StorageModule extends AbstractModule {

    @Inject
    @Named("config")
    private YMLFile config;

    @Override
    protected void configure() {
        bind(new TypeReference<Storage<UUID, StaffInformation>>() {}).to(StaffModeStorageManager.class).singleton();
        bind(new TypeReference<Storage<UUID, LeaveInformation>>() {}).to(LeaveStorageManager.class).singleton();
        bind(ItemManager.class).singleton();
        bind(StaffModeManager.class).singleton();
        //TODO bind(PacketManager.class).singleton();
        install(new MenuModule());
    }
}
