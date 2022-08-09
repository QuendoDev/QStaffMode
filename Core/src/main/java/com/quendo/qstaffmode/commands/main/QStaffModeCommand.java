package com.quendo.qstaffmode.commands.main;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qore.utils.bukkit.PluginUtil;
import com.quendo.qstaffmode.QStaffMode;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@Command(names = {"qstaffmode", "qsm"}, permission = "qstaffmode.commands.main.*", desc = "Information command of the plugin.")
public class QStaffModeCommand implements CommandClass {

    @Inject
    @Named ("messages")
    private OldYMLFile messages;

    @Inject
    @Named ("config")
    private OldYMLFile config;

    @Inject
    @Named ("scoreboard")
    private OldYMLFile scoreboard;

    @Inject
    @Named("menus")
    private OldYMLFile menus;

    @Inject
    private QStaffMode plugin;

    @Command(names = {"info", "author", "version"}, permission = "qstaffmode.commands.main.info", desc = "Get the info of this plugin.")
    @Usage("/qsm info")
    public void infoArgument(@Sender Player sender) {
        PluginUtil.sendInfo(plugin, sender);
    }

    @Command(names = {"reload", "rl"}, permission = "qstaffmode.commands.main.reload", desc = "Reload the config of the plugin.")
    @Usage("/qsm reload")
    public void reloadArgument(@Sender CommandSender sender) {
        MessageUtil.sendMessage(sender, PluginUtil.getPrefix(plugin) + "&aReloading plugin config.");
        config.reload();
        messages.reload();
        menus.reload();
        scoreboard.reload();
        MessageUtil.sendMessage(sender, PluginUtil.getPrefix(plugin) + "&aPlugin config reloaded.");
        MessageUtil.sendMessage(sender, PluginUtil.getPrefix(plugin) +
                "&bNote that some config should not have being loaded right now and needs the server to restart. " +
                "An example of this could be items or menus titles. Also some settings in the config as &e'autoStaffModeOnJoin' " +
                "&bor &e'autoEnableBySavedInfo' &bcan't be reloaded with the server running because it would cause errors in " +
                "the data saving system.");
    }
}
