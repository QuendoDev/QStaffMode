package com.quendo.qstaffmode.cooldown;

import com.kino.kore.utils.files.YMLFile;
import com.quendo.qstaffmode.QStaffMode;
import lombok.Getter;
import org.bukkit.Bukkit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatCooldown {

    @Getter
    int taskid;

    @Getter private final Map<UUID, Integer> cooldowns = new HashMap<>();

    @Inject
    private QStaffMode plugin;

    public void launchCooldown () {
        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
            for (UUID uuid : cooldowns.keySet()) {

                int remaining = cooldowns.get(uuid);

                if(remaining <= 0){
                    removeCooldown(uuid);
                    return;
                }

                remaining--;
                cooldowns.put(uuid, remaining);
            }

        }, 0L, 20L);
    }

    public void checkIfCooldown (UUID uuid, int cooldown) {
        if (cooldown <= 0) {
            return;
        }

        if(!cooldowns.containsKey(uuid)){
            cooldowns.put(uuid, cooldown);
        }
    }

    public void removeCooldown (UUID uuid){
        cooldowns.remove(uuid);
    }

    public boolean hasCooldown (UUID uuid) {
        return cooldowns.containsKey(uuid);
    }

    public int getRemainingCooldown (UUID uuid) {
        return cooldowns.get(uuid);
    }
}
