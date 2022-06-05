package com.quendo.qstaffmode.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InspectInteractEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final Player playerClicked;

    public InspectInteractEvent (Player player, Player clicked) {
        this.player = player;
        this.playerClicked = clicked;
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

    public Player getPlayerClicked() {
        return playerClicked;
    }

}
