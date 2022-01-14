package dev.alphacentauri.stafftools.modules.globalstats;

import dev.alphacentauri.stafftools.StaffToolsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class GlobalStatsManager {

    private final File globalStatFile;
    private final String startDate;

    public GlobalStatsManager(StaffToolsPlugin plugin) {
        globalStatFile = new File(plugin.getDataFolder(), "globalstats.yml");
        startDate = "01 01 2022";

        if (!globalStatFile.exists()) {
            try {
                globalStatFile.createNewFile();
            } catch (IOException exception) {
                Bukkit.getLogger().warning("Something went wrong when trying to create a global stats file!");
            }
        }
    }

    public void setStat(String stat, long value) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(globalStatFile);

        if (config.getString(stat) == null) {
            config.set(stat, "");
        }

        config.set(stat, value);
        save(config);
    }

    public void increaseStat(String stat) {
        setStat(stat, getStatLifetime(stat) + 1L);
    }

    public void decreaseStat(String stat) {
        setStat(stat, getStatLifetime(stat) - 1L);
    }

    public void resetStat(String stat) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(globalStatFile);
        config.set(stat, "");
        save(config);
    }

    public Long getStatLifetime(String stat) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(globalStatFile);
        return config.getLong(stat);
    }

    public Long getStatMonthly(String stat) {
        return getStatLifetime(stat) / ChronoUnit.MONTHS.between(getStartDate(), getCurrentDate());
    }

    public Long getStatWeekly(String stat)  {
        return getStatLifetime(stat) / ChronoUnit.WEEKS.between(getStartDate(), getCurrentDate());
    }

    public Long getStatDaily(String stat) {
        return getStatLifetime(stat) / Duration.between(getStartDate(), getCurrentDate()).toDays();
    }

    private LocalDate getStartDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
        return LocalDate.parse(startDate, dtf);
    }

    private LocalDate getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");

        return LocalDate.parse(formatter.format(date).replace("/", " "), dtf);
    }

    private void save(FileConfiguration config) {
        try {
            config.save(globalStatFile);
        } catch (IOException exception) {
            Bukkit.getLogger().warning("Something went wrong while trying to set a global stat!");
        }
    }
}
