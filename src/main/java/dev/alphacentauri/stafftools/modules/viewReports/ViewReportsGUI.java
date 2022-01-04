package dev.alphacentauri.stafftools.modules.viewReports;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.data.ReportManager;
import dev.alphacentauri.stafftools.data.entities.Report;
import dev.alphacentauri.stafftools.modules.ModuleUtils;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.ItemBuilder;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ViewReportsGUI {

    public static HashMap<UUID, String> userToggleOption;

    public static Inventory getNetworkReports(UUID menuOpener) {
        Inventory inv = Bukkit.createInventory(null, 54, "Reports");
        String option = userToggleOption.get(menuOpener);
        ReportManager manager = StaffToolsPlugin.getInstance().getReportManager();

        ModuleUtils.fillGlassAround(inv);
        inv.setItem(49, getToggleItem(option));

        if (option.equalsIgnoreCase("open")) {
            if (manager.getOpenNetworkReports().isEmpty()) {
                inv.setItem(22, new ItemBuilder(Material.BEDROCK)
                        .setDisplayName("&cNo Reports")
                        .setLore(CC.translate("&7There are currently no open"),
                                CC.translate("&7reports available.")).build());
                return inv;
            }

            for (Report report : manager.getOpenNetworkReports()) {
                String staffHandling = report.getStaffHandling();
                if (!staffHandling.contains("N/A")) {
                    staffHandling = Bukkit.getOfflinePlayer(UUID.fromString(staffHandling)).getName();
                }

                ArrayList<String> lore = new ArrayList<>();
                lore.add(CC.translate("&8report-" + report.getIndex()));
                lore.add("");
                lore.add(CC.translate("&bReporter: &f" + Bukkit.getOfflinePlayer(report.getSubmitted()).getName()));
                lore.add(CC.translate("&bRule Breaker: &f" + Bukkit.getOfflinePlayer(report.getAgainst()).getName()));
                lore.add("");
                lore.add(CC.translate(CC.translate("&bReason:")));

                for (String s : ChatPaginator.wordWrap(report.getReason(), 36)) {
                    lore.add(CC.translate("&f" + s));
                }

                lore.add("");
                lore.add(CC.translate("&bStatus: &fOpen"));
                lore.add(CC.translate("&bLinked Punishment: &8N/A"));
                lore.add(CC.translate("&bStaff Handling: &f" + staffHandling));
                lore.add(CC.translate("&bDate Issued: &f" + Utils.friendlyDateFromTimestamp(report.getTimeStamp())));
                lore.add("");
                lore.add(CC.translate("&e➤ Left click to teleport with spy mode"));
                lore.add(CC.translate("&e➤ Right click to manage"));

                ItemStack item = new ItemBuilder(Material.PAPER)
                        .setDisplayName("&aPlayer Report")
                        .setLore(lore).build();

                for (int i = 0; i<54; i++) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, item);
                        break;
                    }
                }
            }
        }

        return inv;
    }

    public static Inventory getNetworkReports(UUID menuOpener, OfflinePlayer target) {
        Inventory inv = Bukkit.createInventory(null, 54, "Network Reports by " + target.getName());

        ModuleUtils.fillGlassAround(inv);
        inv.setItem(49, getToggleItem(userToggleOption.get(menuOpener)));

        return inv;
    }

    private static ItemStack getPageItem() {
        return new ItemStack(Material.BEDROCK);
    }

    private static ItemStack getToggleItem(String s) {
        ItemStack item = new ItemStack(Material.BEDROCK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        ArrayList<String> lore = new ArrayList<>();

        if (s.equalsIgnoreCase("open")) {
            item.setType(Material.SLIME_BALL);
            meta.setDisplayName(CC.translate("&aOpen Reports"));

            lore.add(CC.translate("&7View all currently open reports"));
            lore.add(CC.translate("&7submitted by players."));

        } else if (s.equalsIgnoreCase("closed")) {
            item.setType(Material.RED_DYE);
            meta.setDisplayName(CC.translate("&eClosed Reports"));

            lore.add(CC.translate("&7View all resolved and closed reports"));
            lore.add(CC.translate("&7previously submitted by players."));

        } else if (s.equalsIgnoreCase("closedWithPunishment")) {
            item.setType(Material.NETHERITE_AXE);
            meta.setDisplayName(CC.translate("&eClosed &7(With Punishment)"));

            lore.add(CC.translate("&7View all resolved and closed reports"));
            lore.add(CC.translate("&7that received a punishment as a consequence."));

        } else if (s.equalsIgnoreCase("closedWithoutPunishment")) {
            item.setType(Material.MAP);
            meta.setDisplayName(CC.translate("&eClosed &7(Without Punishment)"));

            lore.add(CC.translate("&7View all resolved and closed reports"));
            lore.add(CC.translate("&7that received no punishment as a consequence."));
        }

        lore.add(" ");
        lore.add(CC.translate("&e➤ Click to toggle next"));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}












