package dev.alphacentauri.stafftools.listeners;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.modules.viewReports.ManageReportMenu;
import dev.alphacentauri.stafftools.modules.viewReports.ViewReportsGUI;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    private final StaffToolsPlugin plugin = StaffToolsPlugin.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        StaffUserManager staffUserManager = plugin.getStaffUserManager();
        Player player = event.getPlayer();

        if (PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            Utils.staffIncludeTrial.add(player);
        }

        if (PermissionUtil.isStaff(player.getUniqueId())) {
            Utils.staff.add(player);
        }

        if (PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            if (!staffUserManager.existsInStorage(player.getUniqueId())) {
                staffUserManager.createStaffFile(player.getUniqueId());
            }

            staffUserManager.loadPlayerToCache(player.getUniqueId());

            for (Player target : Utils.getOnlineStaffIncludeTrial_WithStaffNotify()) {
                 target.sendMessage(CC.translate(
                        "&b[STAFF] " + PermissionUtil.getUserPrefix(player.getUniqueId()) + " " + player.getName() + " &ejoined."
                 ));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        StaffUserManager staffUserManager = plugin.getStaffUserManager();
        Player player = event.getPlayer();

        if (PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            staffUserManager.offLoadPlayerFromCache(player.getUniqueId());

            for (Player target : Utils.getOnlineStaffIncludeTrial_WithStaffNotify()) {
                 target.sendMessage(CC.translate(
                        "&b[STAFF] " + PermissionUtil.getUserPrefix(player.getUniqueId()) + " " + player.getName() + " &edisconnected."
                 ));
            }
        }

        ViewReportsGUI.userToggleOption.remove(player.getUniqueId());
        ManageReportMenu.currentReportManage.remove(player.getUniqueId());
        Utils.staff.remove(player);
        Utils.staffIncludeTrial.remove(player);
    }

}











