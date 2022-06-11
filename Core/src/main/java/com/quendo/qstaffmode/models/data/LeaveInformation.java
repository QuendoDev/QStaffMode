package com.quendo.qstaffmode.models.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class LeaveInformation implements ConfigurationSerializable {

    private boolean flying;
    private boolean vanish;
    private boolean inStaffChat;
    private boolean inStaffMode;

    public LeaveInformation (Map<String, Object> map) {
        this.flying = (boolean) map.get("flying");
        this.vanish = (boolean) map.get("vanish");
        this.vanish = (boolean) map.get("inStaffChat");
        this.inStaffMode = (boolean) map.get("inStaffMode");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("flying", flying);
        map.put("vanish", vanish);
        map.put("inStaffChat", inStaffChat);
        map.put("inStaffMode", inStaffMode);
        return map;
    }
}
