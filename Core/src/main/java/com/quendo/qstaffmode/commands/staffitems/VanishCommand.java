package com.quendo.qstaffmode.commands.staffitems;

import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"vanish", "v", "ocultar", "hide"}, permission = "qstaffmode.commands.vanish.*", desc = "Vanish command.")
public class VanishCommand implements CommandClass {

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = "", permission = "qstaffmode.commands.vanish.toggle", desc = "The player enables vanish if it's disabled, and disables it if enabled.")
    @Usage("/vanish")
    public boolean toggleVanishCommand(@Sender Player sender) {
        staffModeManager.toggleVanish(sender);
        return true;
    }

    @Command(names = "on", permission = "qstaffmode.commands.vanish.on", desc = "The player enables vanish.")
    @Usage("/vanish on")
    public boolean onVanish(@Sender Player sender) {
        staffModeManager.vanish(sender);
        return true;
    }

    @Command(names = "off", permission = "qstaffmode.commands.vanish.off", desc = "The player disables vanish.")
    @Usage("/vanish off")
    public boolean offVanish(@Sender Player sender) {
        staffModeManager.unvanish(sender);
        return true;
    }
}
