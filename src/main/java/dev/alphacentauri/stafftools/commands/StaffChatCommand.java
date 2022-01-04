package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.entity.Player;

public class StaffChatCommand extends PlayerCommand {

    public StaffChatCommand() {
        super("staffchat", "sc");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (!PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou are not allowed to do this!"));
            return;
        }

        StaffUserManager staffUserManager = StaffToolsPlugin.getInstance().getStaffUserManager();

        if (args.length == 0) {
            StaffUser staffUser = staffUserManager.getStaffUser(player.getUniqueId());
            if (staffUser == null) return;

            if (staffUser.isStaffChat()) {
                staffUser.setStaffChat(false);
                player.sendMessage(CC.translate("&c&lDISABLED! &cToggle staff chat disabled."));
            } else {
                staffUser.setStaffChat(true);
                player.sendMessage(CC.translate("&a&lENABLED! &aToggle staff chat enabled."));
            }

            return;
        }

        StringBuilder builder = new StringBuilder();
        for (String s : args) {
             builder.append(s).append(" ");
        }

        String message = builder.toString().trim();
        for (Player target : Utils.getOnlineStaffIncludeTrial()) {
            target.sendMessage(CC.translate(
                    "&b[STAFF] " + PermissionUtil.getUserPrefix(player.getUniqueId()) + " " + player.getName() + "&f: " + message
            ));
        }
    }
}













