package dev.alphacentauri.stafftools.modules.spymode;


import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.utils.CC;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;

public class SpyModeSystem {

    private final HashMap<Player, SpyModeProfile> spyModeProfiles;

    public SpyModeSystem() {
        spyModeProfiles = new HashMap<>();
    }

    public SpyModeProfile getProfile(Player player) {
        if (spyModeProfiles.containsKey(player)) {
            return spyModeProfiles.get(player);
        }

        return null;
    }

    public void resetPlayer(Player player) {
        SpyModeProfile profile = spyModeProfiles.get(player);
        if (profile == null) return;

        player.getInventory().clear();
        player.getInventory().setContents(profile.getInventory().values().toArray(new ItemStack[0]));
        player.getInventory().setArmorContents(profile.getArmorSet());
        player.updateInventory();

        player.setGameMode(profile.getGameMode());
        player.setFoodLevel(profile.getFoodLevel());
        player.setHealthScale(profile.getHealthScale());
        player.setHealth(profile.getHealth());
        player.setExp(profile.getExp());
        player.setLevel(profile.getLevel());
        player.setWalkSpeed(profile.getWalkSpeed());
        player.setFlySpeed(profile.getFlySpeed());

        player.setAllowFlight(profile.getAllowFlight());
        if (profile.getAllowFlight()) {
            player.setFlying(profile.isFlying());
        }

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.getInventory().setItemInMainHand(profile.getItemInMainHand());
        player.getInventory().setItemInOffHand(profile.getItemInOffHand());
        player.teleport(profile.getLocation());

        User user = StaffToolsPlugin.getInstance().getLuckPermsApi().getUserManager().getUser(player.getUniqueId());
        if (user == null) return;
        user.data().remove(Node.builder("suffix.500. &5[S]").build());

        spyModeProfiles.remove(player);
    }

    public void savePlayer(Player player) {
        new SpyModeProfile(player).saveToProfile();
    }

    public boolean isInSpyMode(Player player) {
        return spyModeProfiles.containsKey(player);
    }

    public void resetAllPlayers() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            resetPlayer(online);
        }

        spyModeProfiles.keySet().forEach(this::resetPlayer);
    }

    public HashMap<Player, SpyModeProfile> getSpyModeProfiles() {
        return spyModeProfiles;
    }
}
