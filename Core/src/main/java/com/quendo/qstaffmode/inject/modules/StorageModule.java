package com.quendo.qstaffmode.inject.modules;

import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.StaffInformation;
import com.quendo.qstaffmode.staffmode.ItemManager;
import com.quendo.qstaffmode.staffmode.StaffModeManager;
import com.quendo.qstaffmode.storage.StaffModeStorageManager;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.UUID;


public class StorageModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(new TypeReference<Storage<UUID, StaffInformation>>() {}).to(StaffModeStorageManager.class).singleton();
        bind(ItemManager.class).singleton();
        bind(StaffModeManager.class).singleton();
        /*bind(new TypeReference<Storage<String, Game>>() {}).to(ArenaStorageManager.class).singleton();
        bind(ArenaUtils.class).singleton();*/
    }
}
