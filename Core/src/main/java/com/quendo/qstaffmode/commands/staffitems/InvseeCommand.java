package com.quendo.qstaffmode.commands.staffitems;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
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
    private YMLFile messages;

    @Inject
    private MenuManager menuManager;

    @Command(names = {"invsee", "inspect"}, permission = "qstaffmode.commands.invsee", desc = "Opens a menu with the inventory of the player that you chose.")
    @Usage("/invsee <player>")
    public boolean sCommand(@Sender Player sender, @Named("name") String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (p.hasPermission("qstaffmode.bypass.inspect")) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        menuManager.openInspectMenu(sender, p);
        return true;
    }
}
