package com.quendo.qstaffmode.commands;

import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = {"staffchat", "sc", "chatprivado"}, permission = "qstaffmode.commands.staffchat.*", desc = "StaffChat command.")
public class StaffChatCommand implements CommandClass {

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = "", permission = "qstaffmode.commands.staffchat.toggle", desc = "The player enables staffchat if it's disabled, and disables it if enabled.")
    @Usage("/staffchat")
    public boolean toggleStaffChatCommand(@Sender Player sender) {
        staffModeManager.toggleStaffChat(sender);
        return true;
    }

    @Command(names = "on", permission = "qstaffmode.commands.staffchat.on", desc = "The player enables staffchat.")
    @Usage("/staffchat on")
    public boolean onStaffChat(@Sender Player sender) {
        staffModeManager.enableStaffChat(sender);
        return true;
    }

    @Command(names = "off", permission = "qstaffmode.commands.staffchat.off", desc = "The player disables staffchat.")
    @Usage("/staffchat off")
    public boolean offStaffChat(@Sender Player sender) {
        staffModeManager.disableStaffChat(sender);
        return true;
    }
}
