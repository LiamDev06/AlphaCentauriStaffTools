package dev.alphacentauri.stafftools.utils;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

import java.util.Collection;
import java.util.UUID;

public class PermissionUtil {

    private static final LuckPerms luckPerms = StaffToolsPlugin.getInstance().getLuckPermsApi();

    public static boolean isStaffIncludeTrial(UUID who) {
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return false;
        }

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("trial"));
    }

    public static boolean isStaff(UUID who) {
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return false;
        }

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("helper"));
    }

    public static boolean isModerator(UUID who) {
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return false;
        }

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("moderator"));
    }

    public static boolean isManager(UUID who) {
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return false;
        }

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("manager"));
    }

    public static String getUserPrefix(UUID who) {
        User user = luckPerms.getUserManager().getUser(who);

        if (user == null) {
            return "";
        }

        return user.getCachedData().getMetaData().getPrefix();
    }

}
















