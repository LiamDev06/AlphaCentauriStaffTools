package dev.alphacentauri.stafftools;

import dev.alphacentauri.stafftools.commands.ReportCommand;
import dev.alphacentauri.stafftools.commands.StaffChatCommand;
import dev.alphacentauri.stafftools.commands.StaffNotifyCommand;
import dev.alphacentauri.stafftools.commands.ViewReportsCommand;
import dev.alphacentauri.stafftools.data.ReportManager;
import dev.alphacentauri.stafftools.data.StaffUserManager;
import dev.alphacentauri.stafftools.discord.DiscordWebhook;
import dev.alphacentauri.stafftools.listeners.ChatListener;
import dev.alphacentauri.stafftools.listeners.JoinLeaveListener;
import dev.alphacentauri.stafftools.listeners.LuckPermsGroupChangeListener;
import dev.alphacentauri.stafftools.modules.ModuleUtils;
import dev.alphacentauri.stafftools.modules.viewReports.ManageReportMenu;
import dev.alphacentauri.stafftools.modules.viewReports.ViewReportsListener;
import dev.alphacentauri.stafftools.utils.CC;
import dev.alphacentauri.stafftools.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

        PluginManager manager = getServer().getPluginManager();
        new LuckPermsGroupChangeListener(this, luckPermsApi);
        manager.registerEvents(new JoinLeaveListener(), this);
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new ViewReportsListener(), this);
        manager.registerEvents(new ManageReportMenu(), this);

        new StaffNotifyCommand();
        new StaffChatCommand();
        new ReportCommand();
        new ViewReportsCommand();

        getLogger().info(CC.GREEN + NAME + " has been SUCCESSFULLY loaded in " + (System.currentTimeMillis() - time) + "ms! This plugin is running version " + VERSION);
        getLogger().info(CC.GREEN + "This plugin was made by " + CC.YELLOW + AUTHOR);
    }

    @Override
    public void onDisable() {
        staffUserManager.offLoadPlayersFromCache();

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
}
