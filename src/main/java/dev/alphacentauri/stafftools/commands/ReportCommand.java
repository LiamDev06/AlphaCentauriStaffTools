package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ReportCommand extends PlayerCommand {

    private final StaffToolsPlugin plugin = StaffToolsPlugin.getInstance();

    public ReportCommand() {
        super("report", "reportplayer");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&c&lMISSING ARGUMENTS! &cValid usage: /report <player> <reason>"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            player.sendMessage(CC.translate("&c&lNEVER PLAYED! &cThis player has never played on the server before, are you sure you typed their name right?"));
            return;
        }

        if (target.getUniqueId() == player.getUniqueId()) {
            player.sendMessage(CC.translate("&cYou cannot report yourself!"));
            return;
        }

        if (PermissionUtil.isStaff(target.getUniqueId())) {
            player.sendMessage(CC.translate("&c&lBLOCKED! &cThis player cannot be reported!"));
            return;
        }

        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (String s : args) {
            if (count > 0) {
                builder.append(s).append(" ");
            }

            count++;
        }

        final String reason = builder.toString().trim();
        player.sendMessage(CC.translate("&a&lREPORT COMPLETE! &aThanks a lot for reporting " + PermissionUtil.getUserPrefix(target.getUniqueId()) + " " +
                target.getName() + "&a! Every report helps making this server a better place for everyone :)"));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);

        Report report = new Report(player.getUniqueId(), target.getUniqueId(), reason, "open", "", System.currentTimeMillis());
        plugin.getReportManager().saveReportToStorage(report);

        for (Player staff : Utils.getOnlineStaff_WithStaffNotify()) {
            staff.sendMessage(CC.translate(
                    "&b[STAFF] " + Utils.prefixWithName(player.getUniqueId()) + " &ecreated a new reported against " +
                            Utils.prefixWithName(target.getUniqueId()) + "&e, with the reason: &f" + reason
            ));
        }
    }

}













