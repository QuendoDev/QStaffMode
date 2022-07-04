package com.quendo.qstaffmode.commands;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class StaffModeCommand implements CommandClass {

    @Inject
    @javax.inject.Named ("messages")
    private OldYMLFile messages;

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = {"staff", "staffmode", "sm", "mod", "admin", "h"}, permission = "qstaffmode.commands.staffmode", desc = "Enables the staffmode for the executor.")
    @Usage("/staff")
    public boolean staffModeCommand (@Sender Player sender) {
        staffModeManager.toggleStaffMode(sender, false);
        return true;
    }
}
