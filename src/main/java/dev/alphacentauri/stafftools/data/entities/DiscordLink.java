package dev.alphacentauri.stafftools.data.entities;

import java.util.UUID;

public class DiscordLink {

    private UUID minecraftUuid;
    private String discordId;
    private boolean isLinked;

    public DiscordLink(UUID minecraftUuid, String discordId, boolean isLinked) {
        this.minecraftUuid = minecraftUuid;
        this.discordId = discordId;
        this.isLinked = isLinked;
    }

    public UUID getMinecraftUuid() {
        return minecraftUuid;
    }

    public void setMinecraftUuid(UUID minecraftUuid) {
        this.minecraftUuid = minecraftUuid;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }
}
