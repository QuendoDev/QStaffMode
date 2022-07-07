package com.quendo.qstaffmode.commands.staffitems;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.manager.MenuManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class InvseeCommand implements CommandClass {

    @Inject
    @javax.inject.Named ("messages")
    private OldYMLFile messages;

    @Inject
    private MenuManager menuManager;

    @Command(names = {"invsee", "inspect"}, permission = "qstaffmode.commands.invsee", desc = "Opens a menu with the inventory of the player that you chose.")
    @Usage("/invsee <player>")
    public void sCommand(@Sender Player sender, @Named("name") String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (p.hasPermission("qstaffmode.bypass.inspect")) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        menuManager.openInspectMenu(sender, p);
    }
}
