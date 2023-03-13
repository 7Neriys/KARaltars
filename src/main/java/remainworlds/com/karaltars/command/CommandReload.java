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
import java.util.Objects;

public class CommandReload implements CommandExecutor {

    private KARaltars plugin;
    private AltarsDB db;

    public CommandReload(KARaltars plugin){

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
        if(!sender.hasPermission("KARaltars.reload")){
            sender.sendMessage(plugin.getConfig().getString("messages.commands.dont_have_permissions").replace("&", "§"));
            return true;
        }
        //загружаем файл altars.yml
        File file = new File(plugin.getDataFolder(), "altars.yml");
        YamlConfiguration.loadConfiguration(file);
        sender.sendMessage(plugin.getConfig().getString("messages.commands.altars_yml_reloaded").replace("&", "§"));
        //создаем файл конфигурации из altars.yml
        FileConfiguration altars = YamlConfiguration.loadConfiguration(file);


        //обновляем БД основываясь на конфиге алтарей
        sender.sendMessage("-----------------------------");

        ConfigurationSection sec = altars.getConfigurationSection("altars");
        if(sec != null) {
            db.clearBD();
            String block;
            String world;
            String XYZ;
            ConfigurationSection blockid;
            for (String key : sec.getKeys(false)) {
                blockid = altars.getConfigurationSection("altars." + key);
                if(blockid!=null) {

                    for (String Block_ID : blockid.getKeys(false)) {
                        if(!Objects.equals(Block_ID, "count")) {

                            block = altars.getString("altars." + key + "." + Block_ID + ".block");
                            world = altars.getString("altars." + key + "." + Block_ID + ".world");
                            XYZ = altars.getInt("altars." + key + "." + Block_ID + ".coord.x")  + "; " + altars.getInt("altars." + key + "." + Block_ID + ".coord.y") + "; " + altars.getInt("altars." + key + "." + Block_ID + ".coord.z")+";";

                            db.add(key, Integer.parseInt(Block_ID), block, world, XYZ);
                        }

                    }
                }
                else {
                    sender.sendMessage("altar" + key + "is empty");
                }

            }
            sender.sendMessage("altars.db is reloaded");

        }
        else{
            sender.sendMessage("altars.yml is empty");
        }


        sender.sendMessage("-----------------------------");



        sender.sendMessage(plugin.getConfig().getString("messages.commands.altars_yml_reloaded").replace("&", "§"));
        plugin.reloadConfig();
        sender.sendMessage(plugin.getConfig().getString("messages.commands.config_reloaded").replace("&", "§"));
        return true;
    }
}
