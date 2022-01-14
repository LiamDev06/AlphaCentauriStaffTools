package dev.alphacentauri.stafftools.utils;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

import java.util.Collection;
import java.util.UUID;

public class PermissionUtil {

    private static final LuckPerms luckPerms = StaffToolsPlugin.getInstance().getLuckPermsApi();

    public static boolean isStaffIncludeTrial(UUID uuid) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null) return false;

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("trial"));
    }

    public static boolean isStaff(UUID uuid) {
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null) return false;

        Collection<Group> inheritedGroups = user.getInheritedGroups(user.getQueryOptions());
        return inheritedGroups.stream().anyMatch(g -> g.getName().equals("staff"));
    }

}
