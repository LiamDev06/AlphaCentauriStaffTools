package dev.alphacentauri.stafftools.utils;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Utils {

    public static ArrayList<Player> staffIncludeTrial;
    public static ArrayList<Player> staff;

    public static void setup() {
        staffIncludeTrial = new ArrayList<>();
        staff = new ArrayList<>();
    }

    public static ArrayList<Player> getOnlineStaffIncludeTrial() {
        return staffIncludeTrial;
    }

    public static ArrayList<Player> getOnlineStaff() {
        return staff;
    }

    public static ArrayList<Player> getOnlineStaffIncludeTrial_WithStaffNotify() {
        final ArrayList<Player> players = new ArrayList<>();

        for (Player target : staffIncludeTrial) {
            StaffUser staffUser = StaffToolsPlugin.getInstance().getStaffUserManager().getStaffUser(target.getUniqueId());
            if (staffUser.isStaffNotify()) {
                players.add(target);
            }
        }

        return players;
    }

    public static ArrayList<Player> getOnlineStaff_WithStaffNotify() {
        final ArrayList<Player> players = new ArrayList<>();

        for (Player target : staff) {
            StaffUser staffUser = StaffToolsPlugin.getInstance().getStaffUserManager().getStaffUser(target.getUniqueId());
            if (staffUser.isStaffNotify()) {
                players.add(target);
            }
        }

        return players;
    }

    public static FileConfiguration loadConfig(String fileName) {
        return YamlConfiguration.loadConfiguration(new File(StaffToolsPlugin.getInstance().getDataFolder(), fileName));
    }

    public static String prefixWithName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        String name;

        if (player == null) {
            name = Bukkit.getOfflinePlayer(uuid).getName();
        } else {
            name = player.getName();
        }

        return CC.translate(getUserPrefix(uuid) + " " + name);
    }

    public static String getUserPrefix(UUID who) {
        LuckPerms luckPerms = StaffToolsPlugin.getInstance().getLuckPermsApi();
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return "";
        }

        return CC.translate(user.getCachedData().getMetaData().getPrefix());
    }

    public static String friendlyDateFromTimestamp(long input) {
        Timestamp timestamp = new Timestamp(input);
        Date date = new Date(timestamp.getTime());
        return date.toString();
    }

    public static void noPerm(Player player) {
        player.sendMessage(CC.translate("&cYou do not have access to perform this!"));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, 1);
    }

}








