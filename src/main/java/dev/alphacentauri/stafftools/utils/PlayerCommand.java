package dev.alphacentauri.stafftools.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class PlayerCommand extends Command {

    public PlayerCommand(String command) {
        super(command);
        init();
    }

    public PlayerCommand(String command, String... aliases) {
        super(command, "", "/" + command, Arrays.asList(aliases));
        init();
    }

    private void init() {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap)bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("command", this);
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    public abstract void onPlayerCommand(Player player, String[] args);

    @Override
    public boolean execute(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            this.onPlayerCommand((Player) commandSender, args);
        } else {
            commandSender.sendMessage(CC.translate("&cThis command can only be performed by a player!"));
        }
        return false;
    }

}
