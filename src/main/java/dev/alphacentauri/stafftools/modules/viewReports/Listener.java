package dev.alphacentauri.stafftools.modules.viewReports;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;

        // All network reports
        if (event.getView().getTitle().equalsIgnoreCase("Network Reports")) {
            event.setCancelled(true);

        }

        // Per player reports
        if (event.getView().getTitle().startsWith("Network Reports by ")) {
            event.setCancelled(true);

        }
    }
}











