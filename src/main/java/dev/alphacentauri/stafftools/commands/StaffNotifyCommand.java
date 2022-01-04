package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import org.bukkit.entity.Player;

public class StaffNotifyCommand extends PlayerCommand {

    public StaffNotifyCommand() {
        super("staffnotify", "togglenotify");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (!PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou are not allowed to perform this!"));
            return;
        }

        StaffUserManager staffUserManager = StaffToolsPlugin.getInstance().getStaffUserManager();
        StaffUser staffUser = staffUserManager.getStaffUser(player.getUniqueId());

        if (staffUser == null) {
            staffUserManager.createStaffFile(player.getUniqueId());
            staffUser = staffUserManager.getStaffUser(player.getUniqueId());
        }

        if (staffUser.isStaffNotify()) {
            staffUser.setStaffNotify(false);
            player.sendMessage(CC.translate("&e&lOFF! &eYou toggled off the staff notification mode."));
        } else {
            staffUser.setStaffNotify(true);
            player.sendMessage(CC.translate("&a&lON! You toggled on the staff notification mode."));
        }

    }

}












