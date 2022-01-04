package dev.alphacentauri.stafftools.modules.viewReports;

import dev.alphacentauri.stafftools.modules.ModuleUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

public class MenuGUI {

    public static Inventory getNetworkReports() {
        Inventory inv = Bukkit.createInventory(null, 54, "Network Reports");
        ModuleUtils.fillGlassAround(inv);

        return inv;
    }

    public static Inventory getNetworkReports(OfflinePlayer player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Network Reports by " + player.getName());
        ModuleUtils.fillGlassAround(inv);

        return inv;
    }

}












