package dev.alphacentauri.stafftools.modules;

import dev.alphacentauri.stafftools.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ModuleUtils {

    public static void fillGlassAround(Inventory inventory) {
        ItemStack glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ").build();

        for (int i = 0; i<9; i++) {
            inventory.setItem(i, glass);
        }

        inventory.setItem(9, glass);
        inventory.setItem(17, glass);
        inventory.setItem(18, glass);
        inventory.setItem(26, glass);
        inventory.setItem(27, glass);
        inventory.setItem(35, glass);
        inventory.setItem(36, glass);
        inventory.setItem(44, glass);

        for (int i = 45; i<54; i++) {
            inventory.setItem(i, glass);
        }
    }

}









