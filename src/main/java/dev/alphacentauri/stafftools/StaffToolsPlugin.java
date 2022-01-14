package dev.alphacentauri.stafftools;

import dev.alphacentauri.stafftools.commands.*;
import dev.alphacentauri.stafftools.data.ReportManager;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.discord.DiscordBot;
import dev.alphacentauri.stafftools.discord.DiscordWebhook;
import dev.alphacentauri.stafftools.listeners.ChatListener;
import dev.alphacentauri.stafftools.listeners.JoinLeaveListener;
import dev.alphacentauri.stafftools.listeners.LuckPermsGroupChangeListener;
import dev.alphacentauri.stafftools.modules.ModuleUtils;
import dev.alphacentauri.stafftools.modules.globalstats.GlobalStatsManager;
import dev.alphacentauri.stafftools.modules.spymode.SpyModeListeners;
import dev.alphacentauri.stafftools.modules.spymode.SpyModeSystem;
import dev.alphacentauri.stafftools.modules.viewreports.ManageReportMenu;
import dev.alphacentauri.stafftools.modules.viewreports.ViewReportsListener;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class StaffToolsPlugin extends JavaPlugin {

    private static StaffToolsPlugin INSTANCE;
    public static String NAME;
    public static String VERSION;
    public static String AUTHOR;

    private StaffUserManager staffUserManager;
    private LuckPerms luckPermsApi;
    private ReportManager reportManager;
    private DiscordWebhook discordWebhook;
    private DiscordBot discordBot;
    private SpyModeSystem spyModeSystem;
    private GlobalStatsManager globalStatsManager;

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        INSTANCE = this;

        NAME = getInstance().getDescription().getName();
        VERSION = "V" + getInstance().getDescription().getVersion();
        AUTHOR = getInstance().getDescription().getAuthors().get(0) + " and " + getInstance().getDescription().getAuthors().get(1);

        saveDefaultConfig();
        Utils.setup();
        ModuleUtils.initGuiUtils();

        File reportsFile = new File(getDataFolder(), "reports.yml");
        if (!reportsFile.exists()) {
            try {
                reportsFile.createNewFile();

                FileConfiguration config = YamlConfiguration.loadConfiguration(reportsFile);
                config.set("reports", "");
                config.save(reportsFile);
            } catch (IOException exception) {
                getLogger().warning("Something went wrong while trying to create reports.yml!");
            }
        }

        staffUserManager = new StaffUserManager();
        reportManager = new ReportManager();
        luckPermsApi = LuckPermsProvider.get();
        discordWebhook = new DiscordWebhook();
        discordBot = new DiscordBot();
        spyModeSystem = new SpyModeSystem();
        globalStatsManager = new GlobalStatsManager(this);

        PluginManager manager = getServer().getPluginManager();
        new LuckPermsGroupChangeListener(this, luckPermsApi);
        manager.registerEvents(new JoinLeaveListener(), this);
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new ViewReportsListener(), this);
        manager.registerEvents(new ManageReportMenu(), this);
        manager.registerEvents(new SpyModeListeners(), this);

        new StaffNotifyCommand();
        new StaffChatCommand();
        new ReportCommand();
        new ViewReportsCommand();
        new SpyModeCommand();

        this.getServer().getScheduler().runTaskTimer(this, () -> {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                for (Player online : Bukkit.getOnlinePlayers()) {

                    if (spyModeSystem.isInSpyMode(online)) {
                        if (online.getGameMode() == GameMode.SPECTATOR) {
                            online.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate("&fYou are currently &cIN SPY MODE &7(SPECTATOR)")));
                        } else {
                            online.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(CC.translate("&fYou are currently &cIN SPY MODE")));
                        }
                    }
                }
            }
        }, 0, 30);

        getLogger().info(CC.GREEN + NAME + " has been SUCCESSFULLY loaded in " + (System.currentTimeMillis() - time) + "ms! This plugin is running version " + VERSION);
        getLogger().info(CC.GREEN + "This plugin was made by " + CC.YELLOW + AUTHOR);
    }

    @Override
    public void onDisable() {
        staffUserManager.offLoadPlayersFromCache();
        spyModeSystem.resetAllPlayers();

        INSTANCE = null;
        getLogger().info(CC.GREEN + NAME + " has SUCCESSFULLY been disabled.");
    }

    public static StaffToolsPlugin getInstance() {
        return INSTANCE;
    }

    public StaffUserManager getStaffUserManager() {
        return staffUserManager;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public LuckPerms getLuckPermsApi() {
        return luckPermsApi;
    }

    public DiscordWebhook getDiscordWebhook() {
        return discordWebhook;
    }

    public DiscordBot getDiscordBot() {
        return discordBot;
    }

    public SpyModeSystem getSpyModeSystem() {
        return spyModeSystem;
    }

    public GlobalStatsManager getGlobalStatsManager() {
        return globalStatsManager;
    }
}
