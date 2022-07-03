package com.quendo.qstaffmode.commands.staffitems;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
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
    private YMLFile messages;

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = {"freeze", "ss"}, permission = "qstaffmode.commands.freeze", desc = "Freezes a player.")
    @Usage("/freeze <player>")
    public boolean freezeCommand (@Sender Player sender, @Named("name") String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (p.hasPermission("qstaffmode.bypass.freeze") || sender.getName().equals(name)) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        staffModeManager.toggleFreeze(p, sender);
        return true;
    }
}
