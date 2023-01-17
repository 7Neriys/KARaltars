package remainworlds.com.karaltars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.KARaltars;

public class CommandReload implements CommandExecutor {

    private KARaltars plugin;

    public CommandReload(KARaltars plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("KARaltars.reload")){
            sender.sendMessage(plugin.getConfig().getString("messages.commands.dont_have_permissions").replace("&", "§"));
            return true;
        }
        plugin.reloadConfig();
        sender.sendMessage(plugin.getConfig().getString("messages.commands.config_reloaded").replace("&", "§"));
        return true;
    }
}
