package remainworlds.com.karaltars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import remainworlds.com.karaltars.SQLite.AltarsDB;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class EventsChecker implements Listener {

    private KARaltars plugin;
    private AltarsDB db;
    EventsChecker(KARaltars plugin){
        this.plugin = plugin;

        try {
            db = new AltarsDB();
        }
        catch (Exception e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }



    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        Location b_loc = block.getLocation();
        String xyz = b_loc.getBlockX() + "; " + b_loc.getBlockY() + "; " + b_loc.getBlockZ()+";";

        String result = db.Find_block(block.getType().toString(), xyz);
        String error = null;
        if(result != null){
            String[] arr = result.split("; ");
            error = arr[0];
        }

        if(Objects.equals(error, "NOT")){
            File file = new File("plugins/KARaltars/altars.yml");

            FileConfiguration altars = YamlConfiguration.loadConfiguration(file);

            String[] arr = result.split("; ");


            String altar_name = arr[1];
            int BlockID = Integer.parseInt(arr[2]);


            List<String> message = altars.getStringList("messages." + xyz + ".riddle");
            if(!message.isEmpty()){
                if(altars.getBoolean("altars." + altar_name + "." + BlockID + ".activated"))
                {
                    player.sendMessage("Колонна уже активированна");
                }
                else {
                    for(String say : message){
                        player.sendMessage(say.replace("&", "§"));
                    }
                }
            }
        }
        if(result!=null && !Objects.equals(error, "NOT")) {
            File file = new File("plugins/KARaltars/altars.yml");

            FileConfiguration altars = YamlConfiguration.loadConfiguration(file);

            String[] arr = result.split("; ");


            String altar_name = arr[0];
            int BlockID = Integer.parseInt(arr[1]);

            if(BlockID == -1){
                boolean active = false;
                for(int i = 0; i < altars.getInt("altars." + altar_name + ".count"); i++){
                    if(!altars.getBoolean("altars." + altar_name + "." + i + ".activated")){
                       active = false;
                       break;
                    }
                    else active = true;
                }

                if(active){
                    b_loc.getBlock().setType(Material.BEDROCK);
                    block.getWorld().strikeLightningEffect(b_loc);
                }
            }
            else{
                if(!altars.getBoolean("altars." + altar_name + "." + BlockID + ".activated")) {
                    b_loc.getBlock().setType(Material.BEDROCK);
                    block.getWorld().strikeLightningEffect(b_loc);

                    List<String> commands = altars.getStringList("altars." + altar_name + "." + BlockID + ".commands");
                    if(!commands.isEmpty()){
                        for(String cmd : commands){
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
                        }
                    }


                    altars.set("altars." + altar_name + "." + BlockID + ".activated", true);
                    try{
                        altars.save(file);
                    }
                    catch (IOException e1){
                        e1.printStackTrace();
                    }
                }else player.sendMessage("Колонна уже активированна");

            }
        }

    }

}
