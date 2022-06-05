package com.quendo.qstaffmode.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FreezeInteractEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final Player playerFrozen;

    public FreezeInteractEvent (Player player, Player clicked) {
        this.player = player;
        this.playerFrozen = clicked;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayerFrozen() {
        return playerFrozen;
    }
}
