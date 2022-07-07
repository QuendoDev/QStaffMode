package com.quendo.qstaffmode.manager;

import com.quendo.qore.files.OldYMLFile;
import lombok.Getter;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatManager {

    @Getter private final Map<UUID, Long> lastMessage = new HashMap<>();

    @Inject
    @Named ("config")
    private OldYMLFile config;

    public boolean canChat (Player p) {
        if (!lastMessage.containsKey(p.getUniqueId())) return true;
        if (!(lastMessage.get(p.getUniqueId()) + (config.getInt("slowmodeCooldown") * 1000L) < System.currentTimeMillis())) return false;
        lastMessage.remove(p.getUniqueId());
        return true;
    }

    public void add (Player p) {
        lastMessage.put(p.getUniqueId(), System.currentTimeMillis());
    }

    public int getRemainingCooldown (Player p) {
        return (int) (config.getInt("slowmodeCooldown") - ((System.currentTimeMillis() - lastMessage.get(p.getUniqueId())) / 1000D));
    }
}
