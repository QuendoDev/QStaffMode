package com.quendo.qstaffmode.commands;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
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
    private OldYMLFile messages;

    @Inject
    private StaffModeManager staffModeManager;

    @Command(names = {"lock", "disable"} , permission = "qstaffmode.commands.chat.lock", desc = "The player disables chat for everyone.")
    @Usage("/chat lock")
    public void lock (@Sender Player sender) {
        if(!staffModeManager.isChatLock()) {
            staffModeManager.lockChat();
            for (Player p : Bukkit.getOnlinePlayers()) {
                MessageUtil.sendMessage(p, messages.getString("chatLock")
                        .replace("<value>", messages.getString("disabledMessage"))
                        .replace("<name>", sender.getDisplayName())
                        .replace("<color>", messages.getString("disabledColor")));
            }
        } else {
            MessageUtil.sendMessage(sender, messages.getString("alreadyLocked"));
        }
    }

    @Command(names = {"unlock", "enable"} , permission = "qstaffmode.commands.chat.unlock", desc = "The player enables chat for everyone.")
    @Usage("/chat unlock")
    public void unlock (@Sender Player sender) {
        if (staffModeManager.isChatLock()) {
            staffModeManager.unlockChat();
            for (Player p : Bukkit.getOnlinePlayers()) {
                MessageUtil.sendMessage(p, messages.getString("chatLock")
                        .replace("<value>", messages.getString("enabledMessage"))
                        .replace("<name>", sender.getDisplayName())
                        .replace("<color>", messages.getString("enabledColor")));
            }
        } else {
            MessageUtil.sendMessage(sender, messages.getString("alreadyUnlocked"));
        }
    }

    @Command(names = "toggle" , permission = "qstaffmode.commands.chat.toggle", desc = "The player toggles chat for everyone.")
    @Usage("/chat toggle")
    public void toggle (@Sender Player sender) {
        String action = staffModeManager.isChatLock() ? "enabledMessage" : "disabledMessage";
        String color = staffModeManager.isChatLock() ? "enabledColor" : "disabledColor";
        staffModeManager.toggleChat();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtil.sendMessage(p, messages.getString("chatLock")
                    .replace("<value>", messages.getString(action))
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString(color)));
        }
    }

    @Command(names = "clear" , permission = "qstaffmode.commands.chat.clear", desc = "The player clear chat for everyone.")
    @Usage("/chat clear")
    public void clear (@Sender Player sender) {
        Bukkit.broadcastMessage(StringUtils.repeat(" \n", 100));
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtil.sendMessage(p, messages.getString("chatCleared")
                    .replace("<player>", sender.getDisplayName()));
        }
    }

    @Command(names = {"slow", "slowmode", "cooldown"} , permission = "qstaffmode.commands.chat.slowmode", desc = "The player enables slow-mode chat for everyone.")
    @Usage("/chat slowmode")
    public boolean slowmode (@Sender Player sender) {
        String action = staffModeManager.isSlowmode() ? "enabledMessage" : "disabledMessage";
        String color = staffModeManager.isSlowmode() ? "disabledColor" : "enabledColor";
        staffModeManager.toggleSlowMode();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MessageUtil.sendMessage(p, messages.getString("chatSlowMode")
                    .replace("<value>", messages.getString(action))
                    .replace("<name>", sender.getDisplayName())
                    .replace("<color>", messages.getString(color)));
        }
        return true;
    }
}
