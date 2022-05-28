package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.service.Service;

public class StorageService implements Service {

    /*@Inject
    private Storage<UUID, StaffItem> itemStorage;*/

    /*@Inject
    private Storage<String, Game> arenaStorage;*/

    @Override
    public void start() {
        /*itemStorage.loadAll();*/
        /*arenaStorage.loadAll();*/
    }

    @Override
    public void stop() {
        /*itemStorage.saveAll();*/
        /*arenaStorage.saveAll();*/
    }
}