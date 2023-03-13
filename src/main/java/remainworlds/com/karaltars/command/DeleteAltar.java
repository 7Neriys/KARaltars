package remainworlds.com.karaltars.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.KARaltars;
import remainworlds.com.karaltars.SQLite.AltarsDB;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DeleteAltar implements CommandExecutor {
    private KARaltars plugin;
    private AltarsDB db;

    public DeleteAltar(KARaltars plugin){

        this.plugin = plugin;
        try {
            db = new AltarsDB();
        }
        catch (Exception e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(!sender.hasPermission("KARaltars.deletealtar")){
            sender.sendMessage(plugin.getConfig().getString("messages.commands.dont_have_permissions").replace("&", "§"));
            return true;
        }
        File file = new File("plugins/KARaltars/altars.yml");

        FileConfiguration altars = YamlConfiguration.loadConfiguration(file);
        if (args.length < 1) {
            return false;
        }
        if(altars.get("altars." + args[0]) == null){
            sender.sendMessage(plugin.getConfig().getString("messages.commands.Altar_not_be").replace("&", "§").replace("{name}", args[0]));
            return true;
        }
        sender.sendMessage(args[0]);

        db.removeAltar(args[0]);
        altars.set("altars." + args[0], null);
        //удаление сообщений о блоках
        ConfigurationSection sec = altars.getConfigurationSection("messages");
        if(sec != null) {
            for (String key : sec.getKeys(false)) {
                sender.sendMessage(key);
                if(Objects.equals(altars.getString("messages." + key + ".Altar_Name"), args[0])){
                    altars.set("messages." + key, null);
                }
            }

        }
        //сохранение файла


        try{
            altars.save(file);
            sender.sendMessage(plugin.getConfig().getString("messages.commands.altar_delete").replace("&", "§").replace("{name}", args[0]));
        }
        catch (IOException e1){
            e1.printStackTrace();
        }

        return true;
    }
}
