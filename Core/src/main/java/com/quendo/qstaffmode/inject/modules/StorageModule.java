package com.quendo.qstaffmode.inject.modules;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.storage.Storage;
import com.quendo.qstaffmode.cooldown.ChatCooldown;
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

    @Override
    protected void configure() {
        bind(new TypeReference<Storage<UUID, StaffInformation>>() {}).to(StaffModeStorageManager.class).singleton();
        bind(new TypeReference<Storage<UUID, LeaveInformation>>() {}).to(LeaveStorageManager.class).singleton();
        bind(ItemManager.class).singleton();
        bind(ChatCooldown.class).singleton();
        bind(StaffModeManager.class).singleton();
        install(new MenuModule());
    }
}
