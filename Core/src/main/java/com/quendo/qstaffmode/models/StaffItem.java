package com.quendo.qstaffmode.models;

import com.quendo.qstaffmode.utils.SkullType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;


@AllArgsConstructor
@Getter
public class StaffItem {

    private boolean enabled;
    private ItemStack item;
    private int slot;

}
