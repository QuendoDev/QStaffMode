package com.quendo.qstaffmode.staffmode;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.LeaveInformation;
import com.quendo.qstaffmode.models.StaffInformation;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class StaffModeManager {

    @Inject
    @Named("config")
    private YMLFile config;

    @Inject
    @Named("messages")
    private YMLFile messages;

    @Inject
    private Storage<UUID, StaffInformation> inStaffMode;

    @Inject Storage<UUID, LeaveInformation> leaveInformationStorage;

    @Inject private ItemManager itemManager;

    @Getter private final List<UUID> frozen = new ArrayList<>();
    @Getter private final List<UUID> vanished = new ArrayList<>();
    @Getter private final List<UUID> flying = new ArrayList<>();

    public void toogleStaffMode (Player p, boolean serverConnection) {
        if (isInStaffMode(p)) {
            disableStaffMode(p, serverConnection);
        } else {
            enableStaffMode(p, serverConnection);
        }
    }

    public void disableStaffMode (Player p, boolean leaving) {
        if (p.hasPermission("qstaffmode.staffmode")) {
            unvanish(p);
            if ((isFlying(p) && !config.getBoolean("keepFlyingWhenDisableStaffMode"))
                    || (isFlying(p) && config.getBoolean("keepFlyingWhenDisableStaffMode") && !p.hasPermission("qstaffmode.staffmode.keepflying"))) {
                stopFlying(p);
            }
            returnPlayerItems(p);
            if (!leaving) {
                inStaffMode.remove(p.getUniqueId());
            }
            MessageUtils.sendMessage(p, messages.getString("disabledStaffMode"));
        }
    }

    public void enableStaffMode (Player p, boolean reconnecting) {
        if (p.hasPermission("qstaffmode.staffmode")) {
            if (!reconnecting && !inStaffMode.find(p.getUniqueId()).isPresent()) {
                inStaffMode.add(p.getUniqueId(), new StaffInformation(p.getLocation(), p.getInventory().getContents(), p.getInventory().getArmorContents()));
            }
            vanish(p);
            fly(p);
            giveStaffItems(p);
            p.setGameMode(GameMode.CREATIVE);
            MessageUtils.sendMessage(p, messages.getString("enabledStaffMode"));
        }
    }

    private void clearInventory (Player p){
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public void giveStaffItems (Player p) {
        clearInventory(p);
        itemManager.giveItems(p, vanished.contains(p.getUniqueId()));
    }

    public void returnPlayerItems (Player p) {
        clearInventory(p);
        Optional<StaffInformation> f = inStaffMode.find(p.getUniqueId());
        ItemStack[] armor = f.map(StaffInformation::getArmor).orElse(null);
        ItemStack[] items = f.map(StaffInformation::getInventoryItems).orElse(null);
        p.getInventory().setContents(items);
        p.getInventory().setArmorContents(armor);
    }

    public void toogleFly (Player p) {
        if (isFlying(p)) {
            stopFlying(p);
        } else {
            fly(p);
        }
    }

    private void fly (Player p) {
        if(p.hasPermission("qstaffmode.fly")) {
            p.setAllowFlight(true);
            p.setFlying(true);
            flying.add(p.getUniqueId());
            MessageUtils.sendMessage(p, messages.getString("enabledFly"));
        }
    }

    private void stopFlying (Player p) {
        if(p.hasPermission("qstaffmode.fly")) {
            flying.remove(p.getUniqueId());
            p.setAllowFlight(false);
            p.setFlying(false);
            MessageUtils.sendMessage(p, messages.getString("disabledFly"));
        }
    }

    private boolean isFlying (Player p) {
        return flying.contains(p.getUniqueId());
    }

    private void changeVanishItem (Player p, boolean vanish) {
        itemManager.giveVanishItem(p, vanish);
    }

    private void showPlayerToPlayer (Player toShow, Player player, boolean show) {
        if (!show) {
            if (!player.hasPermission("qstaffmode.bypass.vanish")) {
                player.hidePlayer(toShow);
                return;
            }
        }
        player.showPlayer(toShow);
    }

    public void toogleVanish (Player p) {
        if (isVanished(p)) {
            unvanish(p);
        } else {
            vanish(p);
        }
    }

    private void vanish (Player p) {
        if (p.hasPermission("qstaffmode.vanish")) {
            if (isInStaffMode(p)) {
                changeVanishItem(p, true);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                showPlayerToPlayer(p, player, false);
            }
            vanished.add(p.getUniqueId());
            MessageUtils.sendMessage(p, messages.getString("enabledVanish"));
        }
    }

    private void unvanish (Player p) {
        if (p.hasPermission("qstaffmode.vanish")) {
            if (isInStaffMode(p)) {
                changeVanishItem(p, false);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                showPlayerToPlayer(p, player, true);
            }
            vanished.remove(p.getUniqueId());
            MessageUtils.sendMessage(p, messages.getString("disabledVanish"));
        }
    }

    public boolean isInStaffMode (Player p) {
        return inStaffMode.find(p.getUniqueId()).isPresent();
    }

    private boolean isVanished(Player p){
        return vanished.contains(p.getUniqueId());
    }

    public void toogleFreeze (Player p, Player staff) {
        if (isFrozen(p)) {
            unfreeze(p, staff, false);
        } else {
            freeze(p, staff);
        }
    }

    private void freeze (Player p, Player staff) {
        if (staff.hasPermission("qstaffmode.freeze")) {
            if (!p.hasPermission("qstaffmode.bypass.freeze")) {
                frozen.add(p.getUniqueId());
                MessageUtils.sendMessage(p, messages.getString("frozenByStaff").replace("<player>", staff.getName()));
                MessageUtils.sendMessage(staff, messages.getString("frozeSomeone").replace("<player>", p.getName()));
            } else {
                MessageUtils.sendMessage(staff, messages.getString("noPerms"));
            }
        }
    }

    public void unfreeze (Player p, Player staff, boolean console) {
        if (console) {
            frozen.remove(p.getUniqueId());
            return;
        }
        if (staff.hasPermission("qstaffmode.freeze")) {
            frozen.remove(p.getUniqueId());
            MessageUtils.sendMessage(p, messages.getString("unfrozenByStaff").replace("<player>", staff.getName()));
            MessageUtils.sendMessage(staff, messages.getString("unfrozeSomeone").replace("<player>", p.getName()));
        }
    }

    public boolean isFrozen(Player p) {
        return frozen.contains(p.getUniqueId());
    }

    public void teleportToRandomplayer(Player p, boolean useMultiworld) {
        if (p.hasPermission("qstaffmode.randomtp")) {
            Random r = new Random();
            ArrayList<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
            players.remove(p);
            if (players.size() > 0) {
                int index = r.nextInt(players.size());
                Player player = players.get(index);
                if (useMultiworld) {
                    p.teleport(player.getLocation());
                    MessageUtils.sendMessage(p, messages.getString("teleportedTo").replace("<player>", player.getName()));
                } else {
                    if (player.getWorld().equals(p.getWorld())) {
                        p.teleport(player.getLocation());
                        MessageUtils.sendMessage(p, messages.getString("teleportedTo").replace("<player>", player.getName()));
                    } else {
                        teleportToRandomplayer(p, false);
                    }
                }
            } else {
                MessageUtils.sendMessage(p, messages.getString("errorRandomTp"));
            }
        }
    }

    public void saveData (Player p) {
        if (!config.getBoolean("autoStaffModeOnJoin")) {
            boolean fly = isFlying(p);
            boolean vanish = isVanished(p);
            boolean inStaffMode = isInStaffMode(p);
            leaveInformationStorage.remove(p.getUniqueId());
            leaveInformationStorage.add(p.getUniqueId(), new LeaveInformation(fly, vanish, inStaffMode));
        }
    }

    public void readData (Player p) {
        if (!config.getBoolean("autoStaffModeOnJoin")) {
            Optional<LeaveInformation> playerInfo = leaveInformationStorage.find(p.getUniqueId());
            if (playerInfo.isPresent()) {
                boolean fly = playerInfo.get().isFlying();
                setFlyingValue(p, fly);
                boolean vanish = playerInfo.get().isVanish();
                setVanishValue(p, vanish);
                boolean inStaffMode = playerInfo.get().isInStaffMode();
                setStaffModeValue(p, inStaffMode);
            }
        }
    }

    private void setFlyingValue (Player p, boolean fly) {
        if (fly) {
            fly(p);
        } else {
            stopFlying(p);
        }
    }

    private void setVanishValue (Player p, boolean vanish) {
        if (vanish) {
            vanish(p);
        } else {
            unvanish(p);
        }
    }

    private void setStaffModeValue (Player p, boolean staffmode) {
        if (staffmode) {
            enableStaffMode(p, true);
        } else {
            disableStaffMode(p, false);
        }
    }
}
