package com.quendo.qstaffmode.commands;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = {"gamemode", "gm"}, permission = "qstaffmode.commands.gamemode.*", desc = "Gamemode command.")
public class GamemodeCommand implements CommandClass {

    @Inject
    @Named("messages")
    private OldYMLFile messages;

    @Command(names = {"creative", "1", "c"} , permission = "qstaffmode.commands.gamemode.creative", desc = "The player changes to creative mode.")
    @Usage("/gamemode creative [player]")
    public void creative (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.CREATIVE);
            MessageUtil.sendMessage(sender, messages.getString("changeYourGamemode"),
                    "<gamemode>", sender.getGameMode().name().toLowerCase());
            return;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.creative.others")) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        p.setGameMode(GameMode.CREATIVE);
        MessageUtil.sendMessage(sender, messages.getString("changeOthersGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<player>", p.getDisplayName());
        MessageUtil.sendMessage(p, messages.getString("someoneChangedYourGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<staff>", sender.getDisplayName());
    }

    @Command(names = {"survival", "0", "s"} , permission = "qstaffmode.commands.gamemode.survival", desc = "The player changes to survival mode.")
    @Usage("/gamemode survival [player]")
    public void survival (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.SURVIVAL);
            MessageUtil.sendMessage(sender, messages.getString("changeYourGamemode"), "<gamemode>", sender.getGameMode().name().toLowerCase());
            return;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.survival.others")) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        p.setGameMode(GameMode.SURVIVAL);
        MessageUtil.sendMessage(sender, messages.getString("changeOthersGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<player>", p.getDisplayName());
        MessageUtil.sendMessage(p, messages.getString("someoneChangedYourGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<staff>", sender.getDisplayName());
    }

    @Command(names = {"adventure", "2", "a"} , permission = "qstaffmode.commands.gamemode.adventure", desc = "The player changes to adventure mode.")
    @Usage("/gamemode adventure [player]")
    public void adventure (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.ADVENTURE);
            MessageUtil.sendMessage(sender, messages.getString("changeYourGamemode"), "<gamemode>", sender.getGameMode().name().toLowerCase());
            return;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.adventure.others")) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        p.setGameMode(GameMode.ADVENTURE);
        MessageUtil.sendMessage(sender, messages.getString("changeOthersGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<player>", p.getDisplayName());
        MessageUtil.sendMessage(p, messages.getString("someoneChangedYourGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<staff>", sender.getDisplayName());
    }

    @Command(names = {"spectator", "3", "sp", "spec"} , permission = "qstaffmode.commands.gamemode.spectator", desc = "The player changes to spectator mode.")
    @Usage("/gamemode spectator [player]")
    public void spectator (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.SPECTATOR);
            MessageUtil.sendMessage(sender, messages.getString("changeYourGamemode")
                    .replace("<gamemode>", sender.getGameMode().name().toLowerCase()));
            return;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtil.sendMessage(sender, messages.getString("playerNotOnline"));
            return;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.spectator.others")) {
            MessageUtil.sendMessage(sender, messages.getString("noPerms"));
            return;
        }
        p.setGameMode(GameMode.SPECTATOR);
        MessageUtil.sendMessage(sender, messages.getString("changeOthersGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<player>", p.getDisplayName());
        MessageUtil.sendMessage(p, messages.getString("someoneChangedYourGamemode"), "<gamemode>", p.getGameMode().name().toLowerCase(), "<staff>", sender.getDisplayName());
    }
}
