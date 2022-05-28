package com.quendo.qstaffmode.inject.loader;

import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.QStaffMode;
import team.unnamed.inject.InjectAll;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@InjectAll
public class ListenerLoader implements Loader {

    private QStaffMode plugin;

    /*private JoinListener joinListener;
    private QuitListener quitListener;
    private PlayerTeleportListener teleportListener;*/

    private void registerListeners (Listener...listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void load() {
        registerListeners(/*joinListener, quitListener, teleportListener*/);
    }
}
