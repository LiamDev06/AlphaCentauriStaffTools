package dev.alphacentauri.stafftools.listeners;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LuckPermsGroupChangeListener {

    public LuckPermsGroupChangeListener(StaffToolsPlugin plugin, LuckPerms luckPerms) {
        EventBus eventBus = luckPerms.getEventBus();
        eventBus.subscribe(plugin, UserDataRecalculateEvent.class, this::onGroupChange);
    }

    private void onGroupChange(UserDataRecalculateEvent event) {
        UUID uuid = event.getUser().getUniqueId();
        StaffUserManager manager = StaffToolsPlugin.getInstance().getStaffUserManager();

        if (PermissionUtil.isStaffIncludeTrial(uuid)) {

            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            if (!Utils.staffIncludeTrial.contains(player)) {
                Utils.staffIncludeTrial.add(player);
            }

            if (!manager.existsInStorage(player.getUniqueId())) {
                manager.createStaffFile(player.getUniqueId());
            }
            manager.loadPlayerToCache(uuid);
        }

        if (PermissionUtil.isStaff(uuid)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            if (!Utils.staff.contains(player)) {
                Utils.staff.add(player);
            }

            if (!manager.existsInStorage(player.getUniqueId())) {
                manager.createStaffFile(player.getUniqueId());
            }
            manager.loadPlayerToCache(uuid);
        }

        if (!PermissionUtil.isStaffIncludeTrial(uuid)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            Utils.staffIncludeTrial.remove(player);
            Utils.staff.remove(player);
            manager.offLoadPlayerFromCache(uuid);
            manager.getStaffUsers().remove(uuid);
        }

        if (!PermissionUtil.isStaff(uuid)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) return;

            Utils.staff.remove(player);
            manager.offLoadPlayerFromCache(uuid);
            manager.getStaffUsers().remove(uuid);
        }
    }

}









