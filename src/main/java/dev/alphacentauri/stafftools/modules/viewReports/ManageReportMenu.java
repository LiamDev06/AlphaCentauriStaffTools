package dev.alphacentauri.stafftools.modules.viewReports;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.data.entities.StaffUser;
import dev.alphacentauri.stafftools.modules.ModuleUtils;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ManageReportMenu implements Listener {

    public static HashMap<UUID, Report> currentReportManage;

    public static Inventory getManageReport(Report report, Player opener) {
        Inventory inv = Bukkit.createInventory(null, 54, "Reports ➤ Manage Report");
        ModuleUtils.fillGlassAround(inv);
        StaffUser staffUser = StaffToolsPlugin.getInstance().getStaffUserManager().getStaffUser(opener.getUniqueId());
        currentReportManage.remove(opener.getUniqueId());
        currentReportManage.put(opener.getUniqueId(), report);

        inv.setItem(49, new ItemBuilder(Material.ARROW)
                .setDisplayName("&aGo Back")
                .setLore(CC.translate("&7To network reports")).build());

        inv.setItem(19, new ItemBuilder(Material.ENDER_EYE)
        .setDisplayName("&bClaim Report")
        .setLore(
                CC.translate("&7Claim the current report as yours"),
                CC.translate("&7so other staff members know"),
                CC.translate("&7that you are currently handling it."),
                "",
                CC.translate("&e➤ Click to claim"))
        .build());

        inv.setItem(21, new ItemBuilder(Material.PAPER)
                .setDisplayName("&bDownload Logs")
                .setLore(
                        CC.translate("&7Download a log file of the reported rule breaker."),
                        CC.translate("&7The log file will be sent via discord and contains"),
                        CC.translate("&7all the previous events, actions and messages the"),
                        CC.translate("&7reported player have performed."),
                        "",
                        CC.translate("&e➤ Click to download via discord"))
                .build());

        inv.setItem(23, new ItemBuilder(Material.DIAMOND_AXE)
                .setDisplayName("&bExecute Punishment")
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore(
                        CC.translate("&7If you found the reported rule breaker"),
                        CC.translate("&7to be breaking rules, execute a punishment"),
                        CC.translate("&7right from here and have it link to the report."),
                        "",
                        CC.translate("&e➤ Click to execute"))
                .build());

        inv.setItem(25, new ItemBuilder(Material.ORANGE_DYE)
                .setDisplayName("&bClose Report")
                .setLore(
                        CC.translate("&7Close the report after you are done"),
                        CC.translate("&7reviewing and handling it. The report"),
                        CC.translate("&7will be sent to the archive and cannot be"),
                        CC.translate("&7opened again."),
                        "",
                        CC.translate("&e➤ Click to close"))
                .build());


        return inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)  {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Report report = currentReportManage.get(player.getUniqueId());

        if (event.getView().getTitle().equalsIgnoreCase("Reports ➤ Manage Report")) {
            event.setCancelled(true);

            if (event.getSlot() == 19) {
                if (report.getStaffHandling().equalsIgnoreCase(player.getUniqueId().toString())) {
                    player.sendMessage(CC.translate("&c&lALREADY HANDLING! &cYou are already marked as the handler of this report."));
                    return;
                }

                report.setStaffHandling(player.getUniqueId().toString());
                report.save();

                player.sendMessage(CC.translate("&3&lCLAIMED! &3You claimed this report as yours."));
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 2);
                return;
            }

            // Download logs (will be sent to a channel in the discord)
            if (event.getSlot() == 21) {
                return;
            }

            // Execute punishment
            if (event.getSlot() == 23) {
                return;
            }

            if (event.getSlot() == 25) {
                report.setStatus("closed");
                report.save();

                player.sendMessage(CC.translate("&2&lCLOSED! &2You closed the report."));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 10, -1);
                ViewReportsGUI.userPageCounter.replace(player.getUniqueId(), 1D);
                player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                return;
            }

            if (event.getSlot() == 49) {
                player.openInventory(ViewReportsGUI.getNetworkReports(player.getUniqueId()));
                player.updateInventory();
            }
        }
    }
}





















