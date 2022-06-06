package com.quendo.qstaffmode.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ItemBuilder {

    ItemBuilder material (String material);

    ItemBuilder data (byte data);

    ItemBuilder amount (int amount);

    /**
     * Sets the name for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param name The new name for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder name(String name);

    /**
     * Sets the lore for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param lore The new lore for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder lore(List<String> lore);

    /**
     * Adds a line to the lore of the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param line The line that you are adding to the lore for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder addLoreLine(String line);

    /**
     * Adds some lines to the lore of the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param lines The lines that you are adding to the lore for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder addLoreLines(List<String> lines);

    /**
     * Sets a set of enchantments for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param enchantments The new enchantments map for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder enchants(Map<Enchantment, Integer> enchantments);

    /**
     * Adds a enchantment for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method with the same specified {@link Enchantment} will lead to overriding the last level for the enchantment
     *
     * @param enchantment The new {@link Enchantment} added to the {@linkplain ItemStack} being build
     * @param level The level of the {@link Enchantment} being added
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder addEnchant(Enchantment enchantment, Integer level);

    /**
     * Sets a set of flags for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method will override the last value set
     *
     * @param flags The new flags list for the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder flags(List<ItemFlag> flags);

    /**
     * Adds a flag for the {@linkplain ItemStack}
     * <p>
     * Multiple calls to this method with the same specified {@link ItemFlag} will lead to the Flag being added multiple times
     *
     * @param itemFlag The new {@link ItemFlag} added to the {@linkplain ItemStack} being build
     * @return The same mutable instance of ItemBuilder
     */
    ItemBuilder addFlag(ItemFlag itemFlag);

    ItemBuilder setURL (String url);

    ItemBuilder setOwner (String owner);

    ItemBuilder glow();

    /**
     * Builds the final {@link ItemStack} instance with the specified fields
     *
     * @return A non null {@link ItemStack} instance
     */
    ItemStack build();

    ItemStack buildPlayerSkull(UUID uuid);
}
