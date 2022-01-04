package dev.alphacentauri.stafftools.data;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportManager {

    public List<Report> getNetworkReports() {
        List<Report> reports = new ArrayList<>();
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            reports.add(new Report(
                    UUID.fromString(config.getString(path + "submittedUuid")),
                    UUID.fromString(config.getString(path + "againstUuid")),
                    config.getString(path + "reason"),
                    config.getString(path + "status"),
                    config.getString(path + "linkedPunishmentId"),
                    config.getLong(path + "timestamp")
            ));
        }

        return reports;
    }

    public List<Report> getNetworkReportsAgainst(UUID uuid) {
        List<Report> reports = new ArrayList<>();
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            if (config.getString(path + "againstUuid").equalsIgnoreCase(uuid.toString())) {
                reports.add(new Report(
                        UUID.fromString(config.getString(path + "submittedUuid")),
                        UUID.fromString(config.getString(path + "againstUuid")),
                        config.getString(path + "reason"),
                        config.getString(path + "status"),
                        config.getString(path + "linkedPunishmentId"),
                        config.getLong(path + "timestamp")
                ));
            }
        }

        return reports;
    }

    public List<Report> getNetworkReportsSubmitted(UUID uuid) {
        List<Report> reports = new ArrayList<>();
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            if (config.getString(path + "submittedUuid").equalsIgnoreCase(uuid.toString())) {
                reports.add(new Report(
                        UUID.fromString(config.getString(path + "submittedUuid")),
                        UUID.fromString(config.getString(path + "againstUuid")),
                        config.getString(path + "reason"),
                        config.getString(path + "status"),
                        config.getString(path + "linkedPunishmentId"),
                        config.getLong(path + "timestamp")
                ));
            }
        }

        return reports;
    }

    public Report getReport(int reportIndex) {
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        final String path = "reports.report-" + reportIndex + ".";
        return new Report(
                UUID.fromString(config.getString("submittedUuid")),
                UUID.fromString(config.getString("againstUuid")),
                config.getString("reason"),
                config.getString("status"),
                config.getString("linkedPunishmentId"),
                config.getLong("timestamp")
        );
    }

    public void saveReportToStorage(Report report) {
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        StaffToolsPlugin plugin = StaffToolsPlugin.getInstance();

        int reportIndex = plugin.getConfig().getInt("reportIndex");
        plugin.getConfig().set("reportIndex", reportIndex + 1);
        plugin.saveConfig();

        final String path = "reports.report-" + reportIndex + ".";
        FileConfiguration reportsConfig = Utils.loadConfig("reports.yml");
        reportsConfig.set(path + "submittedUuid", report.getSubmitted().toString());
        reportsConfig.set(path + "againstUuid", report.getAgainst().toString());
        reportsConfig.set(path + "reason", report.getReason());
        reportsConfig.set(path + "status", report.getStatus()); // open
        reportsConfig.set(path + "linkedPunishmentId", report.getLinkedPunishmentId()); // punishments can be linked with this report
        reportsConfig.set(path + "timestamp", report.getTimeStamp());

        try {
            config.save(file);
        } catch (IOException exception) {
            Bukkit.getLogger().warning("Something went wrong while trying to create a new report!");
        }
    }

}








