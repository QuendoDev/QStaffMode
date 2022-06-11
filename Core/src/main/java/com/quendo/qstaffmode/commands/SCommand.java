package com.quendo.qstaffmode.commands;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Named;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class SCommand implements CommandClass {

    @Inject
    @javax.inject.Named ("messages")
    private YMLFile messages;

    @Command(names = "s", permission = "qstaffmode.commands.teleport", desc = "The player used as an argument is teleported to your position.")
    @Usage("/s <player>")
    public boolean sCommand(@Sender Player sender, @Named("name") String name) {
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        p.teleport(sender);
        MessageUtils.sendMessage(sender,  messages.getString("teleported").replace("<player>", p.getName()));
        return true;
    }
}
