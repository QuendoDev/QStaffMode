package com.quendo.qstaffmode.menus.stafflist.submenus.pages;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PlayerPage {

    private Player player;
    private int page;

    public Player getPlayer() {
        return player;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
