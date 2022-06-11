package com.quendo.qstaffmode.listener.items;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.events.*;
import com.quendo.qstaffmode.manager.MenuManager;
import com.quendo.qstaffmode.manager.StaffModeManager;
import com.quendo.qstaffmode.utils.ActionType;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@InjectAll
public class ItemsInteractImplListener implements Listener {

    @Named("config")
    private YMLFile config;

    private StaffModeManager staffModeManager;
    private MenuManager menuManager;

    @EventHandler
    public void navigatorInteract (NavigatorInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.navigator")) {
            if (e.getActionType() == ActionType.LEFT_CLICK) {
                if (config.getDouble("navigatorImpulse") > 0.0D && config.getDouble("navigatorImpulse") <= 8.0D) {
                    Vector v = e.getLookingAt().getDirection().normalize().multiply(config.getDouble("navigatorImpulse"));
                    e.getPlayer().setVelocity(v);
                }
            }

            if (e.getActionType() == ActionType.RIGHT_CLICK) {
                Location loc = e.getLookingAt();
                e.getPlayer().teleport(loc);
            }
        }
    }

    @EventHandler
    public void flyInteract (FlyInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.fly")) {
            staffModeManager.toogleFly(e.getPlayer());
        }
    }

    @EventHandler
    public void randomTpInteract (RandomTpInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.randomtp")) {
            staffModeManager.teleportToRandomplayer(e.getPlayer(), config.getBoolean("randomTpMultiworld"));
        }
    }

    @EventHandler
    public void vanishInteract (VanishInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.vanish")) {
            staffModeManager.toogleVanish(e.getPlayer());
        }
    }

    @EventHandler
    public void stafflistInteract (StaffListInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.stafflist")) {
            menuManager.openStaffListMainMenu(e.getPlayer());
        }
    }

    @EventHandler
    public void inspectInteract (InspectInteractEvent e) {
        if(e.getPlayer().hasPermission("qstaffmode.items.inspect") && !e.getPlayerClicked().hasPermission("qstaffmode.bypass.inspect")) {
            menuManager.openInspectMenu(e.getPlayer(), e.getPlayerClicked());
        }
    }

    @EventHandler
    public void freezeInteract (FreezeInteractEvent e) {
        System.out.println(e.getPlayer().getName() + e.getPlayerFrozen().getName());
        if(e.getPlayer().hasPermission("qstaffmode.items.freeze") && !e.getPlayerFrozen().hasPermission("qstaffmode.bypass.freeze")) {
            staffModeManager.toogleFreeze(e.getPlayerFrozen(), e.getPlayer());
        }
    }
}
