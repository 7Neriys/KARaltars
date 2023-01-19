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
import java.util.Objects;

public class AddBlockToAltar implements CommandExecutor {


    private AltarsDB db;
    private KARaltars plugin;

    public AddBlockToAltar(KARaltars plugin){
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



        if (!sender.hasPermission("KARaltars.addblocktoaltar")) {
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


        if(Objects.equals(((Player) sender).getItemInHand().getType().toString(), "AIR")){
            sender.sendMessage("Do not AIR");
            return true;
        }

        int[] l = new int[ altars.getInt("altars." + args[0] + ".count")];
        for(int i = 0; i< l.length; i++){
            if(altars.getString("altars." + args[0] + "." + i) == null){
                String xyz = ((Player) sender).getLocation().getBlockX() + "; " + ((Player) sender).getLocation().getBlockY() + "; " + ((Player) sender).getLocation().getBlockZ()+";";

                db.add(args[0], i, ((Player) sender).getItemInHand().getType().toString(), ((Player) sender).getLocation().getWorld().getName().toString(),xyz);

                altars.set("altars." + args[0] + "." + i + ".block", ((Player) sender).getItemInHand().getType().toString());
                altars.set("altars." + args[0] + "." + i + ".world", ((Player) sender).getLocation().getWorld().getName().toString());
                altars.set("altars." + args[0] + "." + i + ".coord.x", ((Player) sender).getLocation().getBlockX());
                altars.set("altars." + args[0] + "." + i + ".coord.y", ((Player) sender).getLocation().getBlockY());
                altars.set("altars." + args[0] + "." + i + ".coord.z", ((Player) sender).getLocation().getBlockZ());
                altars.set("altars." + args[0] + "." + i + ".command", "");
                altars.set("altars." + args[0] + "." + i + ".particle.true", "");
                altars.set("altars." + args[0] + "." + i + ".particle.false", "");
                try{
                    altars.save(file);
                }
                catch (IOException e1){
                    e1.printStackTrace();
                }
                sender.sendMessage("Block " + ((Player) sender).getItemInHand().getType() + " for altar " + args[0] + " created");
                return true;
            }
        }
        sender.sendMessage("max alredy created");
        return true;
    }
}
