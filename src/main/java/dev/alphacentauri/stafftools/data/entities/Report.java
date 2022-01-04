package dev.alphacentauri.stafftools.data.entities;

import java.util.UUID;

public class Report {

    private UUID submitted;
    private UUID against;
    private String reason;
    private String status;
    private String linkedPunishmentId;
    private long timeStamp;

    public Report(UUID submitted, UUID against, String reason, String status, String linkedPunishmentId, long timeStamp) {
        this.submitted = submitted;
        this.against = against;
        this.reason = reason;
        this.status = status;
        this.linkedPunishmentId = linkedPunishmentId;
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
