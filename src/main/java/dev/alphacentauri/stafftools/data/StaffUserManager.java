package dev.alphacentauri.stafftools.data;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class StaffUserManager {

    private final HashMap<UUID, StaffUser> staffUsers;
    private final StaffToolsPlugin plugin = StaffToolsPlugin.getInstance();

    public StaffUserManager() {
        staffUsers = new HashMap<>();
    }

    public HashMap<UUID, StaffUser> getStaffUsers() {
        return staffUsers;
    }

    public StaffUser getStaffUser(UUID uuid) {
        if (staffUsers.containsKey(uuid)) {
            return this.staffUsers.get(uuid);
        }

        if (existsInStorage(uuid)) {
            loadPlayerToCache(uuid);
            return this.staffUsers.get(uuid);
        }

        return null;
    }

    public void createStaffFile(UUID uuid) {
        File file = new File(plugin.getDataFolder() + "/staffdata", uuid.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        StaffUser staffUser = new StaffUser(uuid);

        config.set("uuid", uuid.toString());
        config.set("staffNotify", true);
        config.set("staffChat", false);
        config.set("spyMode", false);

        staffUsers.put(uuid, staffUser);

        try {
            config.save(file);
        } catch (IOException ignored) {
            Bukkit.getLogger().warning("Something went wrong while trying to create a new staff file!");
        }
    }

    public void loadPlayerToCache(UUID uuid) {
        File file = new File(plugin.getDataFolder() + "/staffdata", uuid.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        StaffUser staffUser = new StaffUser(uuid);
        staffUser.setStaffNotify(config.getBoolean("staffNotify"));
        staffUser.setStaffChat(config.getBoolean("staffChat"));
        staffUser.setSpyMode(config.getBoolean("spyMode"));
    }

    public void offLoadPlayerFromCache(UUID uuid) {
        StaffUser staffUser = getStaffUser(uuid);
        if (staffUser == null) return;

        File file = new File(plugin.getDataFolder() + "/staffdata", uuid.toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("uuid", staffUser.getUuid().toString());
        config.set("staffNotify", staffUser.isStaffNotify());
        config.set("staffChat", staffUser.isStaffChat());
        config.set("spyMode", staffUser.isSpyMode());

        try {
            config.save(file);
        } catch (IOException ignored) {
            Bukkit.getLogger().warning("Something went wrong while trying to offload a player from the cache");
        }
    }

    public void offLoadPlayersFromCache() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            offLoadPlayerFromCache(online.getUniqueId());
        }

        staffUsers.keySet().forEach(this::offLoadPlayerFromCache);
    }

    public boolean existsInStorage(UUID uuid) {
        return new File(plugin.getDataFolder() + "/staffdata", uuid.toString() + ".yml").exists();
    }

}












