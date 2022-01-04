package dev.alphacentauri.stafftools.listeners;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        StaffUserManager manager = StaffToolsPlugin.getInstance().getStaffUserManager();
        StaffUser staffUser = manager.getStaffUser(player.getUniqueId());
        if (staffUser == null) return;

        if (staffUser.isStaffChat() && PermissionUtil.isStaffIncludeTrial(player.getUniqueId())) {
            event.setCancelled(true);

            for (Player target : Utils.getOnlineStaffIncludeTrial()) {
                target.sendMessage(CC.translate(
                        "&b[STAFF] " + PermissionUtil.getUserPrefix(player.getUniqueId()) + " " + player.getName() + "&f: " +
                                event.getMessage().trim()
                ));
            }
        }
    }

}














