package com.quendo.qstaffmode.v1_10_R1;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.quendo.qstaffmode.api.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class IBuilderv1_10_R1 implements ItemBuilder {

    protected Material material;
    private int amount;
    private byte data;

    private String name;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private List<ItemFlag> flags = new ArrayList<>();
    private boolean unbreakable = false;

    private String url;
    private String owner;

    @Override
    public ItemBuilder material(String material) {
        if (Material.getMaterial(material) == null) {
            this.material = Material.STONE;
        } else {
            this.material = Material.getMaterial(material);
        }
        return this;
    }

    @Override
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public ItemBuilder data(byte data) {
        this.data = data;
        return this;
    }

    @Override
    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public ItemBuilder addLoreLine(String line) {

        List<String> tempLore;
        if (lore == null) {
            tempLore = new ArrayList<>();
        } else {
            tempLore = lore;
        }
        tempLore.add(line);
        this.lore = tempLore;

        return this;
    }

    @Override
    public ItemBuilder addLoreLines(List<String> lines) {
        List<String> tempLore;
        if (lore == null) {
            tempLore = new ArrayList<>();
        } else {
            tempLore = lore;
        }
        tempLore.addAll(lines);
        this.lore = tempLore;

        return this;
    }

    @Override
    public ItemBuilder enchants(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;

        return this;
    }

    @Override
    public void addEnchant(Enchantment enchantment, Integer level) {
        enchantments.put(enchantment, level);
    }

    @Override
    public void flags(List<ItemFlag> flags) {
        this.flags = flags;
    }

    @Override
    public void addFlag(ItemFlag itemFlag) {
        flags.add(itemFlag);
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public void setURL(String url) {
        this.url = url;
    }

    public void glow() {
        addEnchant(material != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
        addFlag(ItemFlag.HIDE_ENCHANTS);
    }

    @Override
    public void unbreakable() {
        this.unbreakable = false;
    }

    @Override
    public ItemStack build() {

        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();

        enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        meta.setLore(lore);

        flags.forEach(meta::addItemFlags);

        if (unbreakable) {
            meta.spigot().setUnbreakable(true);
        }

        itemStack.setItemMeta(meta);


        if((material == Material.SKULL_ITEM) || (material == Material.SKULL)) {
            buidSkull(itemStack);
        }

        return itemStack;
    }

    @Override
    public ItemStack buildPlayerSkull(UUID uuid) {

        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        ItemMeta meta = itemStack.getItemMeta();

        enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        meta.setLore(lore);

        flags.forEach(meta::addItemFlags);

        itemStack.setItemMeta(meta);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(Bukkit.getPlayer(uuid).getName());

        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    private void buidSkull(ItemStack itemStack) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if (owner != null) {
            skullMeta.setOwner(owner);
        } else {
            if (url != null) {

                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                byte[] encondedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

                profile.getProperties().put("textures", new Property("textures", new String(encondedData)));

                Field profileField = null;

                try {
                    profileField = skullMeta.getClass().getDeclaredField("profile");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                if (profileField != null) {
                    profileField.setAccessible(true);

                    try {
                        profileField.set(skullMeta, profile);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        itemStack.setItemMeta(skullMeta);

    }
}