package com.quendo.qstaffmode.listener.basic;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.manager.StaffModeManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class MoveListener implements Listener {

    @Named("messages")
    private OldYMLFile messages;

    private StaffModeManager staffModeManager;

    @EventHandler
    public void move (PlayerMoveEvent e) {
        if(staffModeManager.isFrozen(e.getPlayer())) {
            if ((e.getFrom().getX() != e.getTo().getX())
            || (e.getFrom().getY() != e.getTo().getY())
            || (e.getFrom().getZ() != e.getTo().getZ())) {
                MessageUtil.sendMessage(e.getPlayer(), messages.getString("moveWhileFrozen"));
                e.setTo(new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY(),
                        e.getFrom().getZ()));
            }
        }
    }

}

