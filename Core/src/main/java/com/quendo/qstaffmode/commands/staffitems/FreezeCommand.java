package com.quendo.qstaffmode.commands.staffitems;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class FreezeCommand implements CommandClass {

    @Inject
    @javax.inject.Named ("messages")
    private OldYMLFile messages;

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = {"freeze", "ss"}, permission = "qstaffmode.commands.freeze", desc = "Freezes a player.")
    @Usage("/freeze <player>")
    public void freezeCommand (@Sender Player sender, @Named("name") String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (p.hasPermission("qstaffmode.bypass.freeze") || sender.getName().equals(name)) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        staffModeManager.toggleFreeze(p, sender);
    }
}
