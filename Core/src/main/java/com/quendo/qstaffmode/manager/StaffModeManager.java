package com.quendo.qstaffmode.manager;

import com.quendo.qore.files.OldYMLFile;
import com.quendo.qore.scoreboard.Assemble;
import com.quendo.qore.scoreboard.AssembleBoard;
import com.quendo.qore.storage.Storage;
import com.quendo.qore.utils.bukkit.MessageUtil;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.common.Utils;
import com.quendo.qstaffmode.models.data.LeaveInformation;
import com.quendo.qstaffmode.models.data.StaffInformation;
import com.quendo.qstaffmode.scoreboard.QStaffModeScoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicesManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class StaffModeManager {

    @Inject
    private Utils utils;

    @Inject
    @Named("config")
    private OldYMLFile config;

    @Inject
    @Named("messages")
    private OldYMLFile messages;

    @Inject
    @Named("scoreboard")
    private OldYMLFile scoreboard;

    @Inject
    @Getter
    private Storage<UUID, StaffInformation> inStaffMode;

    @Inject Storage<UUID, LeaveInformation> leaveInformationStorage;

    @Inject private ItemManager itemManager;

    @Getter private final List<UUID> frozen = new ArrayList<>();
    @Getter private final List<UUID> vanished = new ArrayList<>();
    @Getter private final List<UUID> flying = new ArrayList<>();
    @Getter private final List<UUID> inStaffChat = new ArrayList<>();

    @Getter private Assemble staffmodeScoreboard;
    @Getter private Map<UUID, AssembleBoard> scoreboardMap;

    @Getter private boolean chatLock = false;
    @Getter private boolean slowmode = false;

    public void setup (QStaffMode qStaffMode) {
        staffmodeScoreboard = new Assemble(qStaffMode, new QStaffModeScoreboard(scoreboard, utils, this), false);
        scoreboardMap = staffmodeScoreboard.getBoards();
    }

    public void toggleStaffMode (Player p, boolean serverConnection) {
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
            if (config.getBoolean("teleportToInitialPosWhenDisabling")) {
                inStaffMode.find(p.getUniqueId()).ifPresent(staffInformation -> p.teleport(staffInformation.getSavedLocation()));
                MessageUtil.sendMessage(p, messages.getString("teleportedToInitialPos"));
            }
            if (!leaving) {
                inStaffMode.remove(p.getUniqueId());
            }
            if (config.getBoolean("scoreboard")) {
                scoreboardMap.remove(p.getUniqueId());
                p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
            MessageUtil.sendMessage(p, messages.getString("disabledStaffMode"));
            if (config.getBoolean("bcStaffMode")) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("qstaffmode.message.enabledstaffmode")) {
                        MessageUtil.sendMessage(online, messages.getString("bcDisabledStaffMode").replace("<player>", p.getName()));
                    }
                }
            }
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
            p.setHealth(20.0D);
            p.setFoodLevel(20);
            if (config.getBoolean("scoreboard")) {
                scoreboardMap.put(p.getUniqueId(), new AssembleBoard(p, staffmodeScoreboard));
            }
            if (config.getBoolean("creativeOnStaffmode")) {
                p.setGameMode(GameMode.CREATIVE);
            }
            MessageUtil.sendMessage(p, messages.getString("enabledStaffMode"));
            if (config.getBoolean("bcStaffMode")) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("qstaffmode.message.enabledstaffmode")) {
                        MessageUtil.sendMessage(online, messages.getString("bcEnabledStaffMode").replace("<player>", p.getName()));
                    }
                }
            }
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
        ItemStack[] armor = f.map(StaffInformation::getArmor).orElse(new ItemStack[0]);
        ItemStack[] items = f.map(StaffInformation::getInventoryItems).orElse(new ItemStack[0]);
        p.getInventory().setContents(items);
        p.getInventory().setArmorContents(armor);
    }

    public void toggleFly (Player p) {
        if (isFlying(p)) {
            stopFlying(p);
        } else {
            fly(p);
        }
    }

    public void fly (Player p) {
        if(p.hasPermission("qstaffmode.fly")) {
            p.setAllowFlight(true);
            p.setFlying(true);
            flying.add(p.getUniqueId());
            MessageUtil.sendMessage(p, messages.getString("enabledFly"));
        }
    }

    public void stopFlying (Player p) {
        if(p.hasPermission("qstaffmode.fly")) {
            flying.remove(p.getUniqueId());
            p.setAllowFlight(false);
            p.setFlying(false);
            MessageUtil.sendMessage(p, messages.getString("disabledFly"));
        }
    }

    public boolean isFlying (Player p) {
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

    public void toggleVanish (Player p) {
        if (isVanished(p)) {
            unvanish(p);
        } else {
            vanish(p);
        }
    }

    public void vanish (Player p) {
        if (p.hasPermission("qstaffmode.vanish")) {
            if (isInStaffMode(p)) {
                changeVanishItem(p, true);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                showPlayerToPlayer(p, player, false);
            }
            vanished.add(p.getUniqueId());
            if (config.getBoolean("actionbarWhenVanish")) {
                utils.sendActionBar(p, messages.getString("vanishActionBar"));
            }
            MessageUtil.sendMessage(p, messages.getString("enabledVanish"));
        }
    }

    public void unvanish (Player p) {
        if (p.hasPermission("qstaffmode.vanish")) {
            if (isInStaffMode(p)) {
                changeVanishItem(p, false);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                showPlayerToPlayer(p, player, true);
            }
            vanished.remove(p.getUniqueId());
            if (config.getBoolean("actionbarWhenVanish")) {
                utils.sendActionBar(p, messages.getString("unvanishActionBar"));
            }
            MessageUtil.sendMessage(p, messages.getString("disabledVanish"));
        }
    }

    public boolean isInStaffMode (Player p) {
        return inStaffMode.find(p.getUniqueId()).isPresent();
    }

    public boolean isVanished(Player p){
        return vanished.contains(p.getUniqueId());
    }

    public void toggleFreeze (Player p, Player staff) {
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
                MessageUtil.sendMessage(p, messages.getString("frozenByStaff").replace("<player>", staff.getName()));
                MessageUtil.sendMessage(staff, messages.getString("frozeSomeone").replace("<player>", p.getName()));
            } else {
                MessageUtil.sendMessage(staff, messages.getString("noPerms"));
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
            MessageUtil.sendMessage(p, messages.getString("unfrozenByStaff").replace("<player>", staff.getName()));
            MessageUtil.sendMessage(staff, messages.getString("unfrozeSomeone").replace("<player>", p.getName()));
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
                    MessageUtil.sendMessage(p, messages.getString("teleportedTo").replace("<player>", player.getName()));
                } else {
                    if (player.getWorld().equals(p.getWorld())) {
                        p.teleport(player.getLocation());
                        MessageUtil.sendMessage(p, messages.getString("teleportedTo").replace("<player>", player.getName()));
                    } else {
                        teleportToRandomplayer(p, false);
                    }
                }
            } else {
                MessageUtil.sendMessage(p, messages.getString("errorRandomTp"));
            }
        }
    }

    public boolean isInStaffChat (Player p) {
        return inStaffChat.contains(p.getUniqueId());
    }

    public void toggleStaffChat (Player p) {
        if (isInStaffChat(p)) {
            disableStaffChat(p);
        } else {
            enableStaffChat(p);
        }
    }

    public void enableStaffChat (Player p) {
        if (config.getBoolean("staffChatEnabled")) {
            if (p.hasPermission("qstaffmode.staffchat")) {
                inStaffChat.add(p.getUniqueId());
                MessageUtil.sendMessage(p, messages.getString("enabledStaffChat"));
            }
        } else {
            MessageUtil.sendMessage(p, messages.getString("staffChatDisabledByDefault"));
        }
    }

    public void disableStaffChat (Player p) {
        if (p.hasPermission("qstaffmode.staffchat")) {
            inStaffChat.remove(p.getUniqueId());
            MessageUtil.sendMessage(p, messages.getString("disabledStaffChat"));
        }
    }

    public void toggleChat () {
        if (isChatLock()) {
            unlockChat ();
        } else {
            lockChat();
        }
    }

    public void lockChat () {
        if (config.getBoolean("chatLock")) {
            chatLock = true;
        }
    }

    public void unlockChat () {
        if (config.getBoolean("chatLock")) {
            chatLock = false;
        }
    }

    public void toggleSlowMode () {
        if (isSlowmode()) {
            noSlowMode ();
        } else {
            slowmode();
        }
    }

    private void slowmode () {
        if (config.getBoolean("slowmode")) {
            slowmode = true;
        }
    }

    private void noSlowMode () {
        if (config.getBoolean("slowmode")) {
            slowmode = false;
        }
    }

    public void saveData (Player p) {
        if (!config.getBoolean("autoStaffModeOnJoin")) {
            boolean fly = isFlying(p);
            boolean vanish = isVanished(p);
            boolean isInStaffChat = isInStaffChat (p);
            boolean inStaffMode = isInStaffMode(p);
            leaveInformationStorage.remove(p.getUniqueId());
            leaveInformationStorage.add(p.getUniqueId(), new LeaveInformation(fly, vanish, inStaffMode, isInStaffChat));
        }
    }

    public void readData (Player p) {
        if (!config.getBoolean("autoStaffModeOnJoin")) {
            Optional<LeaveInformation> playerInfo = leaveInformationStorage.find(p.getUniqueId());
            if (playerInfo.isPresent()) {
                boolean inStaffMode = playerInfo.get().isInStaffMode();
                setStaffModeValue(p, inStaffMode);
                boolean fly = playerInfo.get().isFlying();
                setFlyingValue(p, fly);
                boolean vanish = playerInfo.get().isVanish();
                setVanishValue(p, vanish);
                boolean isInStaffChat = playerInfo.get().isInStaffChat();
                setStaffChatValue (p, isInStaffChat);
            }
        }
    }

    private void setStaffChatValue (Player p, boolean isInStaffChat) {
        if (isInStaffChat) {
            enableStaffChat(p);
        } else {
            disableStaffChat(p);
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
            returnPlayerItems(p);
            inStaffMode.remove(p.getUniqueId());
        }
    }
}
