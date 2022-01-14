package dev.alphacentauri.stafftools.modules.spymode;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import dev.alphacentauri.stafftools.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class SpyModeProfile {

    private Player owner;

    private Location location;
    private HashMap<Integer, ItemStack> inventory;
    private ItemStack[] armorSet;
    private GameMode gameMode;
    private int foodLevel;
    private double healthScale;
    private double health;
    private float exp;
    private int level;
    private float walkSpeed;
    private float flySpeed;
    private boolean allowFlight;
    private boolean isFlying;
    private ItemStack itemInMainHand;
    private ItemStack itemInOffHand;
    private Player target;

    public SpyModeProfile(Player owner) {
        this.owner = owner;
        this.inventory = new HashMap<>();

        for (int i = 0; i < 36; i++) {
            if (owner.getInventory().getItem(i) == null) {
                this.inventory.put(i, new ItemStack(Material.AIR));
            } else {
                this.inventory.put(i, owner.getInventory().getItem(i));
            }
        }

        this.location = owner.getLocation();
        this.armorSet = owner.getInventory().getArmorContents();
        this.gameMode = owner.getGameMode();
        this.foodLevel = owner.getFoodLevel();
        this.healthScale = owner.getHealthScale();
        this.health = owner.getHealth();
        this.exp = owner.getExp();
        this.level = owner.getLevel();
        this.walkSpeed = owner.getWalkSpeed();
        this.flySpeed = owner.getFlySpeed();
        this.allowFlight = owner.getAllowFlight();
        this.isFlying = owner.isFlying();
        this.itemInMainHand = owner.getInventory().getItemInMainHand();
        this.itemInOffHand = owner.getInventory().getItemInOffHand();
        this.target = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HashMap<Integer, ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventory inventory) {
        this.inventory.clear();

        for (int i = 0; i < 36; i++) {
            if (inventory.getItem(i) == null) {
                this.inventory.put(i, new ItemStack(Material.AIR));
            } else {
                this.inventory.put(i, inventory.getItem(i));
            }
        }
    }

    public ItemStack[] getArmorSet() {
        return armorSet;
    }

    public void setArmorSet(ItemStack[] armorSet) {
        this.armorSet = armorSet;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }

    public double getHealthScale() {
        return healthScale;
    }

    public void setHealthScale(double healthScale) {
        this.healthScale = healthScale;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public boolean getAllowFlight() {
        return allowFlight;
    }

    public void setAllowFlight(boolean allowFlight) {
        this.allowFlight = allowFlight;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public ItemStack getItemInMainHand() {
        return itemInMainHand;
    }

    public void setItemInMainHand(ItemStack itemInMainHand) {
        this.itemInMainHand = itemInMainHand;
    }

    public ItemStack getItemInOffHand() {
        return itemInOffHand;
    }

    public void setItemInOffHand(ItemStack itemInOffHand) {
        this.itemInOffHand = itemInOffHand;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void saveToProfile() {
        SpyModeSystem system = StaffToolsPlugin.getInstance().getSpyModeSystem();

        if (system.getSpyModeProfiles().containsKey(owner)) {
            system.getSpyModeProfiles().replace(owner, this);
        } else {
            system.getSpyModeProfiles().put(owner, this);
        }
    }

}
