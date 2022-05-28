package com.quendo.qstaffmode.inject.modules;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.files.FileBinder;
import lombok.AllArgsConstructor;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.AbstractModule;

@AllArgsConstructor
public class MainModule extends AbstractModule {

    private final QStaffMode qStaffMode;

    @Override
    protected void configure() {
        YMLFile config;
        FileBinder fileBinder = new FileBinder()
                .bind("config", new YMLFile(qStaffMode, "config"))
                .bind("messages", new YMLFile(qStaffMode, "messages"))
                .bind("items", new YMLFile(qStaffMode, "items"));

        install(fileBinder.build());
        install(new APIModule());
        install(new StorageModule());
        install(new ServicesModule());


        bind(QStaffMode.class).toInstance(qStaffMode);
        bind(Plugin.class).to(QStaffMode.class);
    }
}
