package com.quendo.qstaffmode.listener.basic;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.manager.ChatManager;
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
    private OldYMLFile messages;

    @Named("config")
    private OldYMLFile config;

    private StaffModeManager staffModeManager;
    private ChatManager chatManager;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();
        if(p != null){
            if(config.getBoolean ("staffChatEnabled")) {
                String prefix = messages.getString("staffchat.prefix");
                String separator = messages.getString("staffchat.separator");
                String msg = e.getMessage();

                if (staffModeManager.isInStaffChat(p)) {
                    for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                        if (staff.hasPermission("qstaffmode.staffchat.read")) {
                            MessageUtil.sendMessage(staff, prefix + p.getDisplayName() + separator + msg);
                        }
                    }
                    e.setMessage(null);
                    e.setCancelled(true);
                }
            }
            if (config.getBoolean("chatLock")) {
                if (staffModeManager.isChatLock() && !p.hasPermission("qstaffmode.chat.lock.bypass")) {
                    e.setMessage(null);
                    e.setCancelled(true);
                    MessageUtil.sendMessage(p, messages.getString("cantTalk"));
                    return;
                }
            }
            if (config.getBoolean("slowmode")) {
                if (staffModeManager.isSlowmode() && !p.hasPermission("qstaffmode.chat.slowmode.bypass")) {
                    if (!chatManager.canChat(p)) {
                        e.setMessage(null);
                        e.setCancelled(true);
                        MessageUtil.sendMessage(p, messages.getString("inCooldownChat").replace("<time>", chatManager.getRemainingCooldown(p) + ""));
                    } else {
                        chatManager.add(p);
                    }
                }
            }
        }
    }

}
