package com.quendo.qstaffmode.inject.services;

import com.kino.kore.utils.service.Service;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

import javax.inject.Inject;

public class ServicesRegistrationService implements Service {

    @Inject
    private QStaffMode qStaffMode;

    @Inject
    private StaffModeManager staffModeManager;

    @Override
    public void start() {
        Bukkit.getServicesManager().register(StaffModeManager.class, staffModeManager, qStaffMode, ServicePriority.Normal);
    }

    @Override
    public void stop() {
        Bukkit.getServicesManager().unregisterAll(qStaffMode);
    }
}
