package com.quendo.qstaffmode.inject.modules;

import com.quendo.qstaffmode.api.ItemBuilder;
import com.quendo.qstaffmode.api.Utils;
import org.bukkit.Bukkit;
import team.unnamed.inject.AbstractModule;

@SuppressWarnings("unchecked")
public class APIModule extends AbstractModule {

    private static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    @Override
    protected void configure() {
        try {
            bind(ItemBuilder.class).to((Class<? extends ItemBuilder>) Class.forName("com.quendo.qstaffmode.v" + SERVER_VERSION + ".IBuilderv" + SERVER_VERSION));
            bind(Utils.class).to((Class<? extends Utils>) Class.forName("com.quendo.qstaffmode.v" + SERVER_VERSION + ".Utilsv" + SERVER_VERSION));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
