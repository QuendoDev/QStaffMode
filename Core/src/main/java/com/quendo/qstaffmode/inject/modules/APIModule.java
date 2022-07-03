package com.quendo.qstaffmode.inject.modules;

import com.quendo.qstaffmode.common.ItemBuilder;
import com.quendo.qstaffmode.common.Utils;
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
            Bukkit.getLogger().severe("VERSION " + SERVER_VERSION + " IS NOT SUPPORTED YET!");
            Bukkit.getLogger().severe("IF YOU DON'T USE A SUPPORTED VERSION, YOU WILL BE GETTING " + e.getClass().getName());
        }
    }
}
