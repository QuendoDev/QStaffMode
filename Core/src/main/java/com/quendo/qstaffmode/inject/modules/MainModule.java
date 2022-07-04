package com.quendo.qstaffmode.inject.modules;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.setup.Loader;
import com.quendo.qstaffmode.PlaceholderAPI;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.files.FileBinder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;

@AllArgsConstructor
public class MainModule extends AbstractModule {

    private final QStaffMode qStaffMode;

    @Override
    protected void configure() {
        FileBinder fileBinder = new FileBinder()
                .bind("config", new OldYMLFile(qStaffMode, "config"))
                .bind("messages", new OldYMLFile(qStaffMode, "messages"))
                .bind("items", new OldYMLFile(qStaffMode, "items"))
                .bind("menus", new OldYMLFile(qStaffMode, "menus"))
                .bind("scoreboard", new OldYMLFile(qStaffMode, "scoreboard"))
                .bind("staffInformation", new OldYMLFile(qStaffMode, "staffInformation"))
                .bind("leaveInformation", new OldYMLFile(qStaffMode, "leaveInformation"));

        install(fileBinder.build());
        install(new APIModule());
        install(new StorageModule());
        install(new ServicesModule());

        bind(QStaffMode.class).toInstance(qStaffMode);
        bind(Plugin.class).to(QStaffMode.class);
    }
}
