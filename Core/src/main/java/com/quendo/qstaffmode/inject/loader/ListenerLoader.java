package com.quendo.qstaffmode.inject.loader;

import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.listener.basic.JoinListener;
import com.quendo.qstaffmode.listener.basic.LeaveListener;
import com.quendo.qstaffmode.listener.basic.MoveListener;
import com.quendo.qstaffmode.listener.basic.StaffModeBasicListener;
import team.unnamed.inject.InjectAll;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@InjectAll
public class ListenerLoader implements Loader {

    private QStaffMode plugin;

    private StaffModeBasicListener staffModeBasicListener;
    private MoveListener moveListener;
    private JoinListener joinListener;
    private LeaveListener leaveListener;

    private void registerListeners (Listener...listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void load() {
        registerListeners(staffModeBasicListener, moveListener, joinListener, leaveListener);
    }
}
