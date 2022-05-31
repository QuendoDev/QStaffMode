package com.quendo.qstaffmode.listener.basic;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.staffmode.StaffModeManager;
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
    private YMLFile config;

    private StaffModeManager staffModeManager;

    //TODO: Data saving system for knowing if player is flying, vanished, frozen... and loading it when he's joining again (LeaveListener also)
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.data.read")){
            //Storage<UUID, LeaveInformation>
            //playerDataManager.readData(e.getPlayer());
        }
        if(!e.getPlayer().hasPermission("qstaffmode.bypass.vanish")) {
            for (UUID uuid : staffModeManager.getVanished()) {
                e.getPlayer().hidePlayer(Bukkit.getPlayer(uuid));
            }
        }
    }
}
