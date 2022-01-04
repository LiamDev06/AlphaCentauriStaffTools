package dev.alphacentauri.stafftools.data;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
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

        if (config.getConfigurationSection("reports") == null) {
            return reports;
        }

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            reports.add(new Report(
                    Integer.parseInt(key.replace("report-", "")),
                    UUID.fromString(config.getString(path + "submittedUuid")),
                    UUID.fromString(config.getString(path + "againstUuid")),
                    config.getString(path + "reason"),
                    config.getString(path + "status"),
                    config.getString(path + "linkedPunishmentId"),
                    config.getString(path + "staffHandling"),
                    config.getLong(path + "timestamp")
            ));
        }

        return reports;
    }

    public List<Report> getOpenNetworkReports() {
        List<Report> reports = new ArrayList<>();
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("reports") == null) {
            return reports;
        }

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            if (config.getString(path + "status").equalsIgnoreCase("open")) {
                reports.add(new Report(
                        Integer.parseInt(key.replace("report-", "")),
                        UUID.fromString(config.getString(path + "submittedUuid")),
                        UUID.fromString(config.getString(path + "againstUuid")),
                        config.getString(path + "reason"),
                        config.getString(path + "status"),
                        config.getString(path + "linkedPunishmentId"),
                        config.getString(path + "staffHandling"),
                        config.getLong(path + "timestamp")
                ));
            }
        }

        return reports;
    }

    public List<Report> getNetworkReportsAgainst(UUID uuid) {
        List<Report> reports = new ArrayList<>();
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("reports") == null) {
            return reports;
        }

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            if (config.getString(path + "againstUuid").equalsIgnoreCase(uuid.toString())) {
                reports.add(new Report(
                        Integer.parseInt(key.replace("report-", "")),
                        UUID.fromString(config.getString(path + "submittedUuid")),
                        UUID.fromString(config.getString(path + "againstUuid")),
                        config.getString(path + "reason"),
                        config.getString(path + "status"),
                        config.getString(path + "linkedPunishmentId"),
                        config.getString(path + "staffHandling"),
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

        if (config.getConfigurationSection("reports") == null) {
            return reports;
        }

        // key = report-[index]
        for (String key : config.getConfigurationSection("reports").getKeys(false)) {
            String path = "reports." + key + ".";

            if (config.getString(path + "submittedUuid").equalsIgnoreCase(uuid.toString())) {
                reports.add(new Report(
                        Integer.parseInt(key.replace("report-", "")),
                        UUID.fromString(config.getString(path + "submittedUuid")),
                        UUID.fromString(config.getString(path + "againstUuid")),
                        config.getString(path + "reason"),
                        config.getString(path + "status"),
                        config.getString(path + "linkedPunishmentId"),
                        config.getString(path + "staffHandling"),
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
                reportIndex,
                UUID.fromString(config.getString(path + "submittedUuid")),
                UUID.fromString(config.getString(path + "againstUuid")),
                config.getString(path + "reason"),
                config.getString(path + "status"),
                config.getString(path + "linkedPunishmentId"),
                config.getString(path + "staffHandling"),
                config.getLong(path + "timestamp")
        );
    }

    public void saveReportToStorage(Report report) {
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        final String path = "reports.report-" + report.getIndex() + ".";
        config.set(path + "submittedUuid", report.getSubmitted().toString());
        config.set(path + "againstUuid", report.getAgainst().toString());
        config.set(path + "reason", report.getReason());
        config.set(path + "status", report.getStatus()); // open
        config.set(path + "linkedPunishmentId", report.getLinkedPunishmentId()); // punishments can be linked with this report
        config.set(path + "staffHandling", report.getStaffHandling());
        config.set(path + "timestamp", report.getTimeStamp());

        try {
            config.save(file);
        } catch (IOException exception) {
            Bukkit.getLogger().warning("Something went wrong while trying to create a new report!");
        }
    }

}








