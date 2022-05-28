package com.quendo.qstaffmode.inject.modules;

import com.quendo.qstaffmode.staffmode.ItemManager;
import team.unnamed.inject.AbstractModule;


public class StorageModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(ItemManager.class).singleton();
        /*bind(new TypeReference<Storage<UUID, StaffItem>>() {}).to(SWPlayerStorageManager.class).singleton();*/
        /*bind(new TypeReference<Storage<String, Game>>() {}).to(ArenaStorageManager.class).singleton();
        bind(ArenaUtils.class).singleton();*/
    }
}
