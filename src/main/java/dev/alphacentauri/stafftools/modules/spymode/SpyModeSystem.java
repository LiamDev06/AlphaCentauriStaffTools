package dev.alphacentauri.stafftools.modules.spymode;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        //TODO Inventory thingy DOES NOT work at all so ye redo that stupid :=)
        profile.getInventory().setContents(profile.getInventory().getContents());
        profile.setArmorSet(profile.getArmorSet());

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
            player.setFlying(profile.getIsFlying());
        }

        player.setItemInHand(profile.getItemInMainHand());
        player.teleport(profile.getLocation());

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
