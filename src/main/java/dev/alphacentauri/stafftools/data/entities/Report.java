package dev.alphacentauri.stafftools.data.entities;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Report {

    private int index;
    private UUID submitted;
    private UUID against;
    private String reason;
    private String status;
    private String linkedPunishmentId;
    private String staffHandling;
    private long timeStamp;

    public Report(int index, UUID submitted, UUID against, String reason, String status, String linkedPunishmentId, String staffHandling, long timeStamp) {
        this.index = index;
        this.submitted = submitted;
        this.against = against;
        this.reason = reason;
        this.status = status;
        this.linkedPunishmentId = linkedPunishmentId;
        this.staffHandling = staffHandling;
        this.timeStamp = timeStamp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public UUID getSubmitted() {
        return submitted;
    }

    public void setSubmitted(UUID submitted) {
        this.submitted = submitted;
    }

    public UUID getAgainst() {
        return against;
    }

    public void setAgainst(UUID against) {
        this.against = against;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinkedPunishmentId() {
        return linkedPunishmentId;
    }

    public void setLinkedPunishmentId(String linkedPunishmentId) {
        this.linkedPunishmentId = linkedPunishmentId;
    }

    public String getStaffHandling() {
        return staffHandling;
    }

    public void setStaffHandling(String staffHandling) {
        this.staffHandling = staffHandling;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void save() {
        File file = new File(StaffToolsPlugin.getInstance().getDataFolder(), "reports.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String path = "reports.report-" + index + ".";

        config.set(path + "submittedUuid", submitted.toString());
        config.set(path + "againstUuid", against.toString());
        config.set(path + "reason", reason);
        config.set(path + "status", status);
        config.set(path + "linkedPunishmentId", linkedPunishmentId);
        config.set(path + "staffHandling", staffHandling);
        config.set(path + "timestamp", timeStamp);

        try {
            config.save(file);
        } catch (IOException exception) {
            Bukkit.getLogger().warning("Something went wrong while trying to save a report!");
        }
    }
}













