package com.quendo.qstaffmode.commands.main;

import com.kino.kore.utils.PluginUtils;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
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
    private YMLFile messages;

    @Inject
    private QStaffMode plugin;

    @Command(names = {"info", "author", "version"}, permission = "qstaffmode.commands.main.info", desc = "Get the info of this plugin.")
    @Usage("/qsm info")
    public boolean infoArgument(@Sender Player sender) {
        MessageUtils.sendMessage(sender, "&e&m--------&e[QStaffMode]&e&m--------");
        MessageUtils.sendMessage(sender, "&aName: &f" + PluginUtils.getName(plugin));
        MessageUtils.sendMessage(sender, "&aVersion: &f" + PluginUtils.getVersion(plugin));
        MessageUtils.sendMessage(sender, "&aAuthor: &f" + Arrays.toString(PluginUtils.getAuthors(plugin).toArray()));
        MessageUtils.sendMessage(sender, "&e&m----------------------------");
        return true;
    }
}
