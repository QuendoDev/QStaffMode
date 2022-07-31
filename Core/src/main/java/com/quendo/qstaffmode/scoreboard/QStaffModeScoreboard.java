package com.quendo.qstaffmode.scoreboard;

import com.quendo.qore.files.config.OldYMLFile;
import com.quendo.qore.scoreboard.AssembleAdapter;
import com.quendo.qore.utils.JavaUtil;
import com.quendo.qstaffmode.common.Utils;
import com.quendo.qstaffmode.manager.StaffModeManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QStaffModeScoreboard implements AssembleAdapter {

    private OldYMLFile scoreboard;
    private Utils utils;
    private StaffModeManager staffModeManager;

    @Override
    public String getTitle(Player player) {
        try {
            return scoreboard.getString("title");
        } catch (NullPointerException ignored ) {}
        return null;
    }

    @Override
    public List<String> getLines(Player player) {
        try{
            List <String> lines = new ArrayList<>();
            for (String key : scoreboard.getConfigurationSection("lines").getKeys(false)) {
                if (scoreboard.getBoolean("lines." + key + ".enabled")) {
                    lines.add(replaceValues(scoreboard.getString("lines." + key + ".line"), player));
                }
            }
            return lines;
        } catch (NullPointerException ignored) {}
        return null;
    }

    private String replaceValues (String s, Player p) {
        return s.replace("<tps>", Math.max((Math.round(utils.getTPS() * 100) / 100.0), 20.0) + "")
                .replace("<online>", Bukkit.getOnlinePlayers().size() + "")
                .replace("<ccolor>", getColor("staffchat", staffModeManager.isInStaffChat(p)))
                .replace("<staffchat>", getValue("staffchat", staffModeManager.isInStaffChat(p)))
                .replace("<vcolor>", getColor("vanish", staffModeManager.isVanished(p)))
                .replace("<vanished>", getValue("vanish", staffModeManager.isVanished(p)));
    }

    private String getColor(String key, boolean enabled){
        String lettere = scoreboard.getString("lines." + key + ".enabled-color");
        String letterd = scoreboard.getString("lines." + key + ".disabled-color");

        return enabled ? "&" + lettere : "&" + letterd;
    }

    private String getValue (String key, boolean enabled) {
        String valuee = scoreboard.getString("lines." + key + ".enabled-" + key);
        String valued = scoreboard.getString("lines." + key + ".disabled-" + key);

        return enabled ? valuee : valued;
    }
}

