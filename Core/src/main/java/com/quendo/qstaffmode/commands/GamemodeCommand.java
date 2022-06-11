package com.quendo.qstaffmode.commands;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = {"gamemode", "gm"}, permission = "qstaffmode.commands.gamemode.*", desc = "Gamemode command.")
public class GamemodeCommand implements CommandClass {

    @Inject
    @Named("messages")
    private YMLFile messages;

    @Command(names = {"creative", "1", "c"} , permission = "qstaffmode.commands.gamemode.creative", desc = "The player changes to creative mode.")
    @Usage("/gamemode creative [player]")
    public boolean creative (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.CREATIVE);
            MessageUtils.sendMessage(sender, messages.getString("changeYourGamemode")
                    .replace("<gamemode>", sender.getGameMode().name().toLowerCase()));
            return true;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.creative.others")) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        p.setGameMode(GameMode.CREATIVE);
        MessageUtils.sendMessage(sender, messages.getString("changeOthersGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<player>", p.getDisplayName()));
        MessageUtils.sendMessage(p, messages.getString("someoneChangedYourGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<staff>", sender.getDisplayName()));
        return true;
    }

    @Command(names = {"survival", "0", "s"} , permission = "qstaffmode.commands.gamemode.survival", desc = "The player changes to survival mode.")
    @Usage("/gamemode survival [player]")
    public boolean survival (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.SURVIVAL);
            MessageUtils.sendMessage(sender, messages.getString("changeYourGamemode")
                    .replace("<gamemode>", sender.getGameMode().name().toLowerCase()));
            return true;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.survival.others")) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        p.setGameMode(GameMode.SURVIVAL);
        MessageUtils.sendMessage(sender, messages.getString("changeOthersGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<player>", p.getDisplayName()));
        MessageUtils.sendMessage(p, messages.getString("someoneChangedYourGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<staff>", sender.getDisplayName()));
        return true;
    }

    @Command(names = {"adventure", "2", "a"} , permission = "qstaffmode.commands.gamemode.adventure", desc = "The player changes to adventure mode.")
    @Usage("/gamemode adventure [player]")
    public boolean adventure (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.ADVENTURE);
            MessageUtils.sendMessage(sender, messages.getString("changeYourGamemode")
                    .replace("<gamemode>", sender.getGameMode().name().toLowerCase()));
            return true;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.adventure.others")) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        p.setGameMode(GameMode.ADVENTURE);
        MessageUtils.sendMessage(sender, messages.getString("changeOthersGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<player>", p.getDisplayName()));
        MessageUtils.sendMessage(p, messages.getString("someoneChangedYourGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<staff>", sender.getDisplayName()));
        return true;
    }

    @Command(names = {"spectator", "3", "sp", "spec"} , permission = "qstaffmode.commands.gamemode.spectator", desc = "The player changes to spectator mode.")
    @Usage("/gamemode spectator [player]")
    public boolean spectator (@Sender Player sender, @OptArg @me.fixeddev.commandflow.annotated.annotation.Named("name") String name) {
        if (name == null) {
            sender.setGameMode(GameMode.SPECTATOR);
            MessageUtils.sendMessage(sender, messages.getString("changeYourGamemode")
                    .replace("<gamemode>", sender.getGameMode().name().toLowerCase()));
            return true;
        }
        Player p = Bukkit.getPlayer(name);
        if (p == null || !p.isOnline()) {
            MessageUtils.sendMessage(sender, messages.getString("playerNotOnline"));
            return true;
        }
        if (!sender.hasPermission("qstaffmode.commands.gamemode.spectator.others")) {
            MessageUtils.sendMessage(sender, messages.getString("noPerms"));
            return true;
        }
        p.setGameMode(GameMode.SPECTATOR);
        MessageUtils.sendMessage(sender, messages.getString("changeOthersGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<player>", p.getDisplayName()));
        MessageUtils.sendMessage(p, messages.getString("someoneChangedYourGamemode")
                .replace("<gamemode>", p.getGameMode().name().toLowerCase())
                .replace("<staff>", sender.getDisplayName()));
        return true;
    }
}
