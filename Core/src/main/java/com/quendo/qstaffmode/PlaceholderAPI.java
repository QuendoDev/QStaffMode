package com.quendo.qstaffmode;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.utils.bukkit.BukkitUtil;
import com.quendo.qstaffmode.manager.StaffModeManager;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class PlaceholderAPI extends PlaceholderExpansion {

    private StaffModeManager staffModeManager;
    private OldYMLFile scoreboard;

    @Override
    public @NotNull String getIdentifier() {
        return "qstaffmode";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Quendo";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

        switch (params) {
            case "chatlock":
                return String.valueOf(staffModeManager.isChatLock());
            case "slowmode":
                return String.valueOf(staffModeManager.isSlowmode());

        }

        if (player == null) return "";

        switch (params) {
            case "vanish_boolean":
                return String.valueOf(staffModeManager.isVanished(player));
            case "vanish_value":
                return staffModeManager.isVanished(player) ? scoreboard.getString("lines.vanish.enabled-vanish") : scoreboard.getString("lines.vanish.disabled-vanish");
            case "staffchat_boolean":
                return String.valueOf(staffModeManager.isInStaffChat(player));
            case "staffchat_value":
                return staffModeManager.isInStaffChat(player) ? scoreboard.getString("lines.staffchat.enabled-staffchat") : scoreboard.getString("lines.staffchat.disabled-staffchat");
            case "fly":
                return String.valueOf(staffModeManager.isFlying(player));
            case "staffmode":
                return String.valueOf(staffModeManager.isInStaffMode(player));
            case "frozen":
                return String.valueOf(staffModeManager.isFrozen(player));
            case "savedlocation":
                return staffModeManager.getInStaffMode().find(player.getUniqueId()).isPresent() ?
                        BukkitUtil.formatLocation(staffModeManager.getInStaffMode().find(player.getUniqueId()).get().getSavedLocation(), false) : "";
        }

        return null;
    }
}
