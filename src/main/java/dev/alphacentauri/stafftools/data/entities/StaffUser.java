package dev.alphacentauri.stafftools.data.entities;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.StaffUserManager;

import java.util.UUID;

public class StaffUser {

    private UUID uuid;
    private boolean isStaffNotify;
    private boolean isStaffChat;
    private boolean isSpyMode;

    public StaffUser(UUID uuid) {
        this.uuid = uuid;
        this.isStaffNotify = true;
        this.isStaffChat = false;
        this.isSpyMode = false;
        save();
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isStaffNotify() {
        return isStaffNotify;
    }

    public boolean isStaffChat() {
        return isStaffChat;
    }

    public boolean isSpyMode() {
        return isSpyMode;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        save();
    }

    public void setStaffNotify(boolean staffNotify) {
        this.isStaffNotify = staffNotify;
        save();
    }

    public void setStaffChat(boolean staffChat) {
        this.isStaffChat = staffChat;
        save();
    }

    public void setSpyMode(boolean spyMode) {
        this.isSpyMode = spyMode;
        save();
    }

    private void save() {
        StaffUserManager manager = StaffToolsPlugin.getInstance().getStaffUserManager();

        if (manager.getStaffUsers().containsKey(uuid)) {
            manager.getStaffUsers().replace(uuid, this);
        } else {
            manager.getStaffUsers().put(uuid, this);
        }
    }

}
