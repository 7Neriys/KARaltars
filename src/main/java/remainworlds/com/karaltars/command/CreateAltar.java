package remainworlds.com.karaltars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.KARaltars;

import java.io.File;
import java.io.IOException;

public class CreateAltar implements CommandExecutor {

    private KARaltars plugin;

    public CreateAltar(KARaltars plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("KARaltars.createaltar")){
            sender.sendMessage(plugin.getConfig().getString("messages.commands.dont_have_permissions").replace("&", "§"));
            return true;
        }
        File file = new File("plugins/KARaltars/altars.yml");
        FileConfiguration altars = YamlConfiguration.loadConfiguration(file);
        if(args.length < 2) return false;



        try {
            Integer.parseInt(args[1]);

        } catch (NumberFormatException e){
            sender.sendMessage("Not a number");
            return true;
        }

        altars.set("altars." + args[0] + ".count", Integer.parseInt(args[1]));
        try{
            altars.save(file);
        }
        catch (IOException e1){
            e1.printStackTrace();
        }
        sender.sendMessage(plugin.getConfig().getString("messages.commands.Altar_created").replace("&", "§").replace("{name}", args[0]).replace("{count}", args[1]));
        return true;
    }

}
