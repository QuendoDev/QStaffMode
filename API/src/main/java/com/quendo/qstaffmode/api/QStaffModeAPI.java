package com.quendo.qstaffmode.api;

import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;

public class QStaffModeAPI {

    private final StaffModeManager staffModeManager;

    public QStaffModeAPI (Plugin plugin) {
        ServicesManager servicesManager = plugin.getServer().getServicesManager();
        this.staffModeManager = servicesManager.getRegistration(StaffModeManager.class).getProvider();
    }

    public boolean isInStaffMode (Player p) {
        return staffModeManager.isInStaffMode(p);
    }

    public boolean isFrozen (Player p) {
        return staffModeManager.isFrozen(p);
    }

    public boolean isVanished (Player p) {
        return staffModeManager.isVanished(p);
    }

    public boolean isFlying (Player p) {
        return staffModeManager.isFlying(p);
    }

    public boolean isInStaffChat (Player p){
        return staffModeManager.isInStaffChat(p);
    }

    public Location getSavedLocation (Player p) {
        if (staffModeManager.getInStaffMode().find(p.getUniqueId()).isPresent()) {
            return staffModeManager.getInStaffMode().find(p.getUniqueId()).get().getSavedLocation();
        }
        return null;
    }
}
