package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.modules.spymode.SpyModeSystem;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import dev.alphacentauri.stafftools.utils.Utils;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpyModeCommand extends PlayerCommand {

    public SpyModeCommand() {
        super("spymode");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (!player.hasPermission("stafftools.tools.spymode") && !player.isOp()) {
            Utils.noPerm(player);
            return;
        }

        SpyModeSystem system = StaffToolsPlugin.getInstance().getSpyModeSystem();

        if (args.length == 0) {
            if (system.isInSpyMode(player)) {
                player.sendMessage(CC.translate("&c&lALREADY IN MODE! &cYou are already in &bSpy Mode&c! Use '&6/spymode leave&c' to leave it first!"));
                return;
            }

            system.savePlayer(player);
            applyEffects(player);

            player.sendMessage(CC.translate("&a&lSPY MODE ENTERED! &aYou have entered spy mode, meaning your actions are now invisible to non-staff members."));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
            return;
        }

        if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("quit")) {
            if (!system.isInSpyMode(player)) {
                player.sendMessage(CC.translate("&cThis can only be performed while in spy mode!"));
                return;
            }

            system.resetPlayer(player);
            player.sendMessage(CC.translate("&e&lSPY MODE LEFT! &eYou left spy mode and are being reset to your old self..."));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
            return;
        }

        if (args[0].equalsIgnoreCase("togglespec")) {
            if (!system.isInSpyMode(player)) {
                player.sendMessage(CC.translate("&cThis can only be performed while in spy mode!"));
                return;
            }

            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.setGameMode(GameMode.ADVENTURE);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, 2);
                player.sendMessage(CC.translate("&7Toggled &coff &7spectator option."));

                player.setAllowFlight(true);
                player.setFlying(true);
            } else {
                player.setGameMode(GameMode.SPECTATOR);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                player.sendMessage(CC.translate("&7Toggled &aon &7spectator option."));
            }

            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&c&lNOT ONLINE! &cThis player is not online!"));
            return;
        }


        //TODO Enter spy mode with the 'target' as the target following player

    }

    private void applyEffects(Player player) {
        User user = StaffToolsPlugin.getInstance().getLuckPermsApi().getUserManager().getUser(player.getUniqueId());
        if (user == null) return;

        SuffixNode node = SuffixNode.builder(CC.translate(" &5[S]"), 500).build();
        user.data().add(node);

        PotionEffect effect = new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false, true);

        for (PotionEffect active : player.getActivePotionEffects()) {
            player.removePotionEffect(active.getType());
        }

        player.addPotionEffect(effect);
        player.closeInventory();
        player.getInventory().clear();
        player.updateInventory();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setHealthScale(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}







