package dev.alphacentauri.stafftools.modules.spymode;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.ItemBuilder;
import dev.alphacentauri.stafftools.utils.ItemStackUtil;
import dev.alphacentauri.stafftools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SpyModeListeners implements Listener {

    private final SpyModeSystem system = StaffToolsPlugin.getInstance().getSpyModeSystem();

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (system.isInSpyMode((Player) event.getEntity())) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && system.isInSpyMode((Player) event.getEntity())) {
            event.setDamage(0);
            event.setCancelled(true);
        }

        if (event.getDamager() instanceof Player && system.isInSpyMode((Player) event.getDamager())) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player && system.isInSpyMode((Player) event.getEntity())) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && system.isInSpyMode((Player) event.getEntity())) {
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (cause == EntityDamageEvent.DamageCause.FALL
                    || cause == EntityDamageEvent.DamageCause.SUFFOCATION
                    || cause == EntityDamageEvent.DamageCause.DROWNING
                    || cause == EntityDamageEvent.DamageCause.LIGHTNING
                    || cause == EntityDamageEvent.DamageCause.CONTACT
                    || cause == EntityDamageEvent.DamageCause.LAVA) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && system.isInSpyMode(player) && event.getClickedBlock() instanceof Chest) {
            Chest chest = (Chest) event.getClickedBlock();

            for (ItemStack item : chest.getBlockInventory().getContents()) {
                player.sendMessage(item.toString());
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) return;
        Player player = event.getPlayer();
        Player clicked = (Player) event.getRightClicked();
        if (!system.isInSpyMode(player)) return;

        Inventory inv = Bukkit.createInventory(null, 54, clicked.getDisplayName() + "'s Inventory");
        inv.setContents(clicked.getInventory().getContents());

        ItemStack glass = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        for (int i = 36; i < 45; i++) {
            inv.setItem(i, glass);
        }

        ItemStack profile = ItemStackUtil.createPlayerSkull(clicked.getName());
        ItemMeta profileMeta = profile.getItemMeta();
        profileMeta.setDisplayName(Utils.prefixWithName(clicked.getUniqueId()));
        profile.setItemMeta(profileMeta);

        ItemStack effects = new ItemStack(Material.POTION);
        ItemMeta effectsMeta = effects.getItemMeta();
        effectsMeta.setDisplayName(CC.translate("&a" + clicked.getDisplayName() + "'s Effects"));
        effects.setItemMeta(effectsMeta);

        inv.setItem(45, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("&aEnder Chest").setLore(CC.translate("&7View the clicked target's"), CC.translate("ender chest and its content."), "", CC.translate("&eClick to view ender chest")).build());
        inv.setItem(46, profile);
        inv.setItem(48, effects);
        inv.setItem(49, glass);
        inv.setItem(50, clicked.getInventory().getHelmet());
        inv.setItem(51, clicked.getInventory().getChestplate());
        inv.setItem(52, clicked.getInventory().getLeggings());
        inv.setItem(53, clicked.getInventory().getBoots());

        player.openInventory(inv);

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 10, 1);
        player.sendMessage(CC.translate("&7Viewing the inventory of " + clicked.getDisplayName() + "..."));
    }




}













