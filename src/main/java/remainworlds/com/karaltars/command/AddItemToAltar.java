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

public class AddItemToAltar implements CommandExecutor {



    private AltarsDB db;
    private KARaltars plugin;

    public AddItemToAltar(KARaltars plugin){
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

        if (!sender.hasPermission("KARaltars.additemtoaltar")) {
            sender.sendMessage(plugin.getConfig().getString("messages.commands.dont_have_permissions").replace("&", "§"));
            return true;
        }
        if(Objects.equals(((Player) sender).getItemInHand().getType().toString(), "AIR")){
            sender.sendMessage("Do not AIR");
            return true;
        }

        String xyz = ((Player) sender).getLocation().getBlockX() + "; " + ((Player) sender).getLocation().getBlockY() + "; " + ((Player) sender).getLocation().getBlockZ()+";";
        String result = db.GetAltarName(xyz, ((Player) sender).getWorld().getName());

        if(result == null){
            String look = ((Player) sender).getLocation().getBlockX() + " "+ ((Player) sender).getLocation().getBlockY() +" "+((Player) sender).getLocation().getBlockZ();
            sender.sendMessage(plugin.getConfig().getString("messages.commands.No_altar").replace("&", "§").replace("{cords}", look));
            return true;
        }
        String[] arr = result.split("; ");
        

        File file = new File("plugins/KARaltars/altars.yml");

        FileConfiguration altars = YamlConfiguration.loadConfiguration(file);

        String item = ((Player) sender).getItemInHand().getType().toString();

        String lore;

        if(((Player) sender).getItemInHand().getLore() == null){
            lore = "none";
        }
        else {
            lore = Objects.requireNonNull(((Player) sender).getItemInHand().getLore().get(0).toString());
        }

        //запись в файл алтарей .yml

        altars.set("altars." + arr[0] + "." + arr[1] + ".item", item);
        altars.set("altars." + arr[0] + "." + arr[1] + ".lore", lore);

        try{
            altars.save(file);
        }
        catch (IOException e1){
            e1.printStackTrace();
        }

        db.addItemToAltar(arr[0], arr[1], item, lore);

        sender.sendMessage(plugin.getConfig().getString("messages.commands.Item_added_to_altar").replace("&", "§").replace("{name}", arr[0]).replace("{item}", item).replace("{lore}", lore));


        return true;
    }
}
