package com.quendo.qstaffmode.events;

import com.quendo.qstaffmode.utils.ActionType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NavigatorInteractEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player player;
    private final ActionType actionType;

    private final Location lookingAt;

    public NavigatorInteractEvent (Player player, ActionType actionType, Location lookingAt) {
        this.player = player;
        this.actionType = actionType;
        this.lookingAt = lookingAt;
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

    public ActionType getActionType() {
        return actionType;
    }

    public Location getLookingAt() {
        return lookingAt;
    }
}
