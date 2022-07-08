package com.quendo.qstaffmode.listener.basic;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class LeaveListener implements Listener {

    @Named("config")
    private OldYMLFile config;

    @Named("messages")
    private OldYMLFile messages;

    private StaffModeManager staffModeManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(staffModeManager.isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("qstaffmode.frozendisconnect")) {
                    MessageUtil.sendMessage(p, messages.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            staffModeManager.unfreeze(e.getPlayer(), null, true);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), config.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
        if(e.getPlayer().hasPermission("qstaffmode.data.save")){
            staffModeManager.saveData(e.getPlayer());
        }
        if (config.getBoolean("scoreboard")) {
            staffModeManager.getScoreboardMap().remove(e.getPlayer().getUniqueId());
            e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        if(e.getPlayer().hasPermission("qstaffmode.data.save")){
            staffModeManager.saveData(e.getPlayer());
        }

        if(staffModeManager.isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("qstaffmode.frozendisconnect")) {
                    MessageUtil.sendMessage(p, messages.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            staffModeManager.unfreeze(e.getPlayer(), null, false);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), config.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
        if (config.getBoolean("scoreboard")) {
            staffModeManager.getScoreboardMap().remove(e.getPlayer().getUniqueId());
            e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}
