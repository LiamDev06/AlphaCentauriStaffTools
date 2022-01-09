package dev.alphacentauri.stafftools.commands;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.modules.spymode.SpyModeProfile;
import dev.alphacentauri.stafftools.modules.spymode.SpyModeSystem;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.PermissionUtil;
import dev.alphacentauri.stafftools.utils.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpyModeCommand extends PlayerCommand {

    public SpyModeCommand() {
        super("spymode");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args) {
        if (!PermissionUtil.isModerator(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cYou must be a moderator or above to perform this!"));
            return;
        }

        SpyModeSystem system = StaffToolsPlugin.getInstance().getSpyModeSystem();

        if (args.length == 0) {
            if (system.isInSpyMode(player)) {
                player.sendMessage(CC.translate("&c&lALREADY IN MODE! &cYou are already in &bSpy Mode&c! Use '&6/spymode leave&c' to leave it first!"));
                return;
            }

            new SpyModeProfile(player).saveToProfile();
            player.sendMessage(CC.translate("&a&lSPY MODE ENTERED! &aYou have entered spy mode, meaning your actions are now invisible to non-staff members."));
            return;
        }

        if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("quit")) {
            if (!system.isInSpyMode(player)) {
                player.sendMessage(CC.translate("&cYou are currently not in spy mode!"));
                return;
            }

            system.resetPlayer(player);
            player.sendMessage(CC.translate("&e&lSPY MODE LEFT! &eYou left spy mode and are being reset to your old self..."));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(CC.translate("&c&lNOT ONLINE! &cThis player is not online!"));
            return;
        }

        //TODO Enter spy mode with the 'target' as the target following player

    }
}







