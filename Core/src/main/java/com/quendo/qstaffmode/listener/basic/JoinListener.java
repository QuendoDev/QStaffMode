package com.quendo.qstaffmode.listener.basic;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.UUID;

@InjectAll
public class JoinListener implements Listener {


    @Named("config")
    private OldYMLFile config;

    private StaffModeManager staffModeManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPermission("qstaffmode.bypass.vanish") && !staffModeManager.getVanished().isEmpty()) {
            for (UUID uuid : staffModeManager.getVanished()) {
                e.getPlayer().hidePlayer(Bukkit.getPlayer(uuid));
            }
        }
        if (config.getBoolean("autoStaffModeOnJoin") && e.getPlayer().hasPermission("qstaffmode.staffmode.autoenable")) {
            staffModeManager.enableStaffMode(e.getPlayer(), true);
            return;
        }
        if(config.getBoolean("autoEnableBySavedInfo") && e.getPlayer().hasPermission("qstaffmode.data.read")){
            staffModeManager.readData(e.getPlayer());
            return;
        }
        if (!config.getBoolean("autoStaffModeOnJoin") && !config.getBoolean("autoEnableBySavedInfo") && staffModeManager.getInStaffMode().find(e.getPlayer().getUniqueId()).isPresent()) {
            staffModeManager.returnPlayerItems(e.getPlayer());
            e.getPlayer().teleport(staffModeManager.getInStaffMode().find(e.getPlayer().getUniqueId()).get().getSavedLocation());
            staffModeManager.getInStaffMode().remove(e.getPlayer().getUniqueId());
        }
    }
}
