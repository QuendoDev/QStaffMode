package com.quendo.qstaffmode.commands.main;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qore.utils.bukkit.PluginUtil;
import com.quendo.qstaffmode.QStaffMode;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Usage;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Arrays;

@Command(names = {"qstaffmode", "qsm"}, permission = "qstaffmode.commands.main.*", desc = "Information command of the plugin.")
public class QStaffModeCommand implements CommandClass {

    @Inject
    @javax.inject.Named ("messages")
    private OldYMLFile messages;

    @Inject
    private QStaffMode plugin;

    @Command(names = {"info", "author", "version"}, permission = "qstaffmode.commands.main.info", desc = "Get the info of this plugin.")
    @Usage("/qsm info")
    public void infoArgument(@Sender Player sender) {
        PluginUtil.sendInfo(plugin, sender);
    }
}
