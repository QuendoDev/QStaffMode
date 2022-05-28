package com.quendo.qstaffmode.inject.modules;

import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.inject.services.QStaffModeServices;
import com.quendo.qstaffmode.inject.services.StorageService;
import team.unnamed.inject.AbstractModule;

public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Service.class).named("qsm-service").to(QStaffModeServices.class).singleton();
        bind(Service.class).named("storage-service").to(StorageService.class).singleton();
    }
}
