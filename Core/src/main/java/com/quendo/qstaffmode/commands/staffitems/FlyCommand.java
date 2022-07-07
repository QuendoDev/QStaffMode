package com.quendo.qstaffmode.commands.staffitems;

import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "fly", permission = "qstaffmode.commands.fly.*", desc = "Fly command.")
public class FlyCommand implements CommandClass {

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = "", permission = "qstaffmode.commands.fly.toggle", desc = "The player enables fly if it's disabled, and disables it if enabled.")
    @Usage("/fly")
    public void toggleFlyCommand(@Sender Player sender) {
        staffModeManager.toggleFly(sender);
    }

    @Command(names = "on", permission = "qstaffmode.commands.fly.on", desc = "The player enables fly.")
    @Usage("/fly on")
    public void onFly(@Sender Player sender) {
        staffModeManager.fly(sender);
    }

    @Command(names = "off", permission = "qstaffmode.commands.fly.off", desc = "The player disables fly.")
    @Usage("/fly off")
    public void offFly(@Sender Player sender) {
        staffModeManager.stopFlying(sender);
    }
}
