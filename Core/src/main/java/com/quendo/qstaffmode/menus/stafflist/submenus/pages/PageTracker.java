package com.quendo.qstaffmode.menus.stafflist.submenus.pages;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PageTracker {

    private final List<PlayerPage> playerPages = new ArrayList<>();

    public PlayerPage getPlayerPage (Player p) {
        for(PlayerPage playerInventory : playerPages) {
            if(playerInventory.getPlayer().getName().equals(p.getName())) {
                return playerInventory;
            }
        }
        return null;
    }

    public void modifyPlayerPage (Player p, int newPage) {
        removePlayerPage(p);
        addPlayerPage(new PlayerPage(p, newPage));
    }

    public void addPlayerPage (PlayerPage playerInventory) {
        playerPages.add(playerInventory);
    }

    public void removePlayerPage (Player p) {
        playerPages.removeIf(playerPage -> playerPage.getPlayer().getName().equals(p.getName()));
    }

    public List<PlayerPage> getPlayerPages() {
        return playerPages;
    }
}
