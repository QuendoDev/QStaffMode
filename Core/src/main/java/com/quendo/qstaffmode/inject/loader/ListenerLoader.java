package com.quendo.qstaffmode.inject.loader;

import com.quendo.qore.setup.Loader;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.listener.basic.*;
import com.quendo.qstaffmode.listener.inventory.InventoryListener;
import com.quendo.qstaffmode.listener.items.ItemsInteractGeneralListener;
import com.quendo.qstaffmode.listener.items.ItemsInteractImplListener;
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
    private ItemsInteractImplListener itemsInteractImplListener;
    private ItemsInteractGeneralListener itemsInteractGeneralListener;
    private InventoryListener inventoryListener;
    private ChatListener chatListener;

    private void registerListeners (Listener...listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void load() {
        registerListeners(staffModeBasicListener, moveListener, joinListener, leaveListener,
                itemsInteractGeneralListener, itemsInteractImplListener, inventoryListener,
                chatListener);
    }
}
