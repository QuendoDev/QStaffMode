package com.quendo.qstaffmode.commands;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import com.quendo.qstaffmode.manager.StaffModeManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = "chat", permission = "qstaffmode.commands.chat.*", desc = "Chat control command.")
public class ChatCommand implements CommandClass {

    @Inject
    @Named("messages")
    private YMLFile messages;

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = {"lock", "disable"} , permission = "qstaffmode.commands.chat.lock", desc = "The player disables chat for everyone.")
    @Usage("/chat lock")
    public boolean lock (@Sender Player sender) {
        staffModeManager.lockChat();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessage(p, messages.getString("chatLock")
                    .replace("<value>", "disabled")
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString("disabledColor")));
        }
        return true;
    }

    @Command(names = {"unlock", "enable"} , permission = "qstaffmode.commands.chat.unlock", desc = "The player enables chat for everyone.")
    @Usage("/chat unlock")
    public boolean unlock (@Sender Player sender) {
        staffModeManager.unlockChat();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessage(p, messages.getString("chatLock")
                    .replace("<value>", "enabled")
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString("enabledColor")));
        }

        return true;
    }

    @Command(names = "toggle" , permission = "qstaffmode.commands.chat.toggle", desc = "The player toggles chat for everyone.")
    @Usage("/chat toggle")
    public boolean toggle (@Sender Player sender) {
        String action = staffModeManager.isChatLock() ? "enabled" : "disabled";
        String color = staffModeManager.isChatLock() ? "enabledColor" : "disabledColor";
        staffModeManager.toggleChat();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessage(p, messages.getString("chatLock")
                    .replace("<value>", action)
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString(color)));
        }
        return true;
    }

    @Command(names = "clear" , permission = "qstaffmode.commands.chat.clear", desc = "The player clear chat for everyone.")
    @Usage("/chat clear")
    public boolean clear (@Sender Player sender) {
        Bukkit.broadcastMessage(StringUtils.repeat(" \n", 100));
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessage(p, messages.getString("chatCleared")
                    .replace("<player>", sender.getDisplayName()));
        }
        return true;
    }

    @Command(names = {"slow", "slowmode", "cooldown"} , permission = "qstaffmode.commands.chat.slowmode", desc = "The player enables slow-mode chat for everyone.")
    @Usage("/chat slowmode")
    public boolean slowmode (@Sender Player sender) {
        String action = staffModeManager.isSlowmode() ? "disabled" : "enabled";
        String color = staffModeManager.isSlowmode() ? "disabledColor" : "enabledColor";
        staffModeManager.toggleSlowMode();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessage(p, messages.getString("chatSlowMode")
                    .replace("<value>", action)
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString(color)));
        }
        return true;
    }
}