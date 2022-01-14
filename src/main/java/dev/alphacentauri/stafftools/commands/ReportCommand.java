package dev.alphacentauri.stafftools.commands;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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

        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (String s : args) {
            if (count > 0) {
                builder.append(s).append(" ");
            }

            count++;
        }

        final String reason = builder.toString().trim();
        player.sendMessage(CC.translate("&a&lREPORT COMPLETE! &aThanks a lot for reporting " +
                target.getName() + "&a! Every report helps making this server a better place for everyone :)"));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);

        int reportIndex = plugin.getConfig().getInt("reportIndex");
        plugin.getConfig().set("reportIndex", reportIndex + 1);
        plugin.saveConfig();

        Report report = new Report(reportIndex, player.getUniqueId(), target.getUniqueId(), reason, "open", "", CC.translate("&7N/A"),System.currentTimeMillis());
        plugin.getReportManager().saveReportToStorage(report);

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xF1C916)
                .setTitle(new WebhookEmbed.EmbedTitle("Player Report", null))
                .setDescription(
                        "**Reporter:** " + player.getName() + " `" + player.getUniqueId().toString() + "`\n"+
                        "**Rule Breaker:** " + target.getName() + " `" + target.getUniqueId().toString() + "`"+
                        "\n\n"+
                        "**Reason:**" + "\n"+
                        reason+
                        "\n\n"+
                        "**Date Issued:** " + Utils.friendlyDateFromTimestamp(report.getTimeStamp())
                )
                .setFooter(new WebhookEmbed.EmbedFooter("report-" + reportIndex,
                "https://media.discordapp.net/attachments/927655491151224892/927999720121581659/logo_big.jpg?width=638&height=638")).build();

        WebhookMessageBuilder messageBuilder = new WebhookMessageBuilder().addEmbeds(embed);
        plugin.getDiscordWebhook().getClient().send(messageBuilder.build());

        for (Player staff : Utils.getOnlineStaff_WithStaffNotify()) {
            staff.sendMessage(CC.translate(
                    "&b[STAFF] " + Utils.prefixWithName(player.getUniqueId()) + " &ecreated a new reported against " +
                            Utils.prefixWithName(target.getUniqueId()) + "&e, with the reason: &f" + reason
            ));
        }
    }

}













