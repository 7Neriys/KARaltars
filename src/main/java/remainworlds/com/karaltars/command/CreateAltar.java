package remainworlds.com.karaltars.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.KARaltars;
import remainworlds.com.karaltars.SQLite.AltarsDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateAltar implements CommandExecutor {

    private KARaltars plugin;

    private AltarsDB db;

    public CreateAltar(KARaltars plugin){
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


        if(Objects.equals(((Player) sender).getItemInHand().getType().toString(), "AIR")){
            sender.sendMessage("Do not AIR");
            return true;
        }

        altars.set("altars." + args[0] + ".count", Integer.parseInt(args[1]));

        String xyz = ((Player) sender).getLocation().getBlockX() + "; " + ((Player) sender).getLocation().getBlockY() + "; " + ((Player) sender).getLocation().getBlockZ()+";";
        int i = -1;
        db.add(args[0], i, ((Player) sender).getItemInHand().getType().toString(), ((Player) sender).getLocation().getWorld().getName().toString(),xyz, "none", "none");

        altars.set("altars." + args[0] + "." + i + ".block", ((Player) sender).getItemInHand().getType().toString());
        altars.set("altars." + args[0] + "." + i + ".world", ((Player) sender).getLocation().getWorld().getName().toString());
        altars.set("altars." + args[0] + "." + i + ".coord.x", ((Player) sender).getLocation().getBlockX());
        altars.set("altars." + args[0] + "." + i + ".coord.y", ((Player) sender).getLocation().getBlockY());
        altars.set("altars." + args[0] + "." + i + ".coord.z", ((Player) sender).getLocation().getBlockZ());
        altars.set("altars." + args[0] + "." + i + ".commands", new ArrayList<String>());
        altars.set("altars." + args[0] + "." + i + ".item", "none");
        altars.set("altars." + args[0] + "." + i + ".lore", "none");
        altars.set("altars." + args[0] + "." + i + ".activated", false);
        List<String> message = new ArrayList<>();
        altars.set("messages." + xyz + ".Altar_Name", args[0]);
        altars.set("messages." + xyz + ".Block_Name", ((Player) sender).getItemInHand().getType().toString());
        altars.set("messages." + xyz + ".riddle", message);
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
