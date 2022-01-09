package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.modules.viewreports.ViewReportsGUI;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ViewReportsCommand extends PlayerCommand {

    public ViewReportsCommand() {
        super("viewreports");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (!PermissionUtil.isStaff(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou are not allowed to do this!"));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(CC.translate("&7Fetching all network reports..."));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 2);

            ViewReportsGUI.userToggleOption.remove(player.getUniqueId());
            ViewReportsGUI.userToggleOption.put(player.getUniqueId(), "open");
            ViewReportsGUI.userPageCounter.remove(player.getUniqueId());
            ViewReportsGUI.userPageCounter.put(player.getUniqueId(), 1D);
            player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            player.sendMessage(CC.translate("&c&lNEVER PLAYED! &cThis player has never played on the server before, are you sure you typed their name right?"));
            return;
        }

        player.sendMessage(CC.translate("&7Fetching all network reports made by " + target.getName() + "..."));
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 2);

        ViewReportsGUI.userToggleOption.remove(player.getUniqueId());
        ViewReportsGUI.userToggleOption.put(player.getUniqueId(), "open");
        player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId(), target));
    }
}























