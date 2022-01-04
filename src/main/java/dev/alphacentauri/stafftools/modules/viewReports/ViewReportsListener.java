package dev.alphacentauri.stafftools.modules.viewReports;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class ViewReportsListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        // All network reports
        if (event.getView().getTitle().equalsIgnoreCase("Reports")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) return;

            if (event.getSlot() == 49) {
                String option = ViewReportsGUI.userToggleOption.get(player.getUniqueId());

                if (option.equalsIgnoreCase("open")) {
                    ViewReportsGUI.userToggleOption.replace(player.getUniqueId(), "closed");
                    player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                }

                if (option.equalsIgnoreCase("closed")) {
                    ViewReportsGUI.userToggleOption.replace(player.getUniqueId(), "closedWithPunishment");
                    player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                }

                if (option.equalsIgnoreCase("closedWithPunishment")) {
                    ViewReportsGUI.userToggleOption.replace(player.getUniqueId(), "closedWithoutPunishment");
                    player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                }

                if (option.equalsIgnoreCase("closedWithoutPunishment")) {
                    ViewReportsGUI.userToggleOption.replace(player.getUniqueId(), "open");
                    player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                }
                return;
            }

            ItemMeta meta = event.getCurrentItem().getItemMeta();
            Report report = StaffToolsPlugin.getInstance().getReportManager()
                    .getReport(Integer.parseInt(CC.decolor(meta.getLore().get(0)
                            .replace("report-", ""))));

            if (event.getClick().isRightClick()) {
                player.openInventory(ManageReportMenu.getManageReport(report, player));
                return;
            }

            Player target = Bukkit.getPlayer(report.getAgainst());
            if (target == null) {
                player.sendMessage(CC.translate("&c&lNOT ONLINE! &cThis player is currently not online."));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, -1);
                return;
            }

            player.sendMessage(CC.translate("&7Teleporting..."));
            player.performCommand("spymode");
            player.teleport(target.getLocation());
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
        }
    }
}











