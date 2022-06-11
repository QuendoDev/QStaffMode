package com.quendo.qstaffmode.listener.basic;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class ChatListener implements Listener {

    @Named("messages")
    private YMLFile messages;

    @Named("config")
    private YMLFile config;

    private StaffModeManager staffModeManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();
        if(p !=null){
            if(config.getBoolean ("staffChatEnabled")) {

                String prefix = messages.getString("staffchat.prefix");
                String separator = messages.getString("staffchat.separator");
                String msg = e.getMessage();

                if (staffModeManager.isInStaffChat(p)) {
                    for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                        if (staff.hasPermission("qstaffmode.staffchat.read")) {
                            staff.sendMessage(prefix + p.getDisplayName() + separator + msg);
                        }
                    }
                    e.setMessage(null);
                    e.setCancelled(true);
                }
            }
        }
    }

}
