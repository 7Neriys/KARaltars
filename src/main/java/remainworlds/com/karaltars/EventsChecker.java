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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        String world = block.getWorld().getName();
        String xyz = b_loc.getBlockX() + "; " + b_loc.getBlockY() + "; " + b_loc.getBlockZ()+";";
        // String resultWorld = db.Find_block(block.getType().toString(), xyz);
        String result = db.Find_block(block.getType().toString(), xyz, world);
        String error = null;
        if(result != null){
            String[] arr = result.split("; ");
            error = arr[4];
        }

        if(Objects.equals(error, "NOT")){
            File file = new File("plugins/KARaltars/altars.yml");

            FileConfiguration altars = YamlConfiguration.loadConfiguration(file);

            String[] arr = result.split("; ");


            String altar_name = arr[0];
            int BlockID = Integer.parseInt(arr[1]);


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
                    List<String> commands = altars.getStringList("altars." + altar_name + "." + BlockID + ".commands");
                    if(!commands.isEmpty()){
                        for(String cmd : commands){

                            String sendcmd = cmd.replace(
                                            "{player}", player.getName())
                                    .replace("{altarName}", altar_name);
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), sendcmd);
                        }
                    }
                }
            }
            else{
                if(!altars.getBoolean("altars." + altar_name + "." + BlockID + ".activated")) {
                    if(!Objects.equals(arr[2], "none")){
                        block.getWorld().strikeLightningEffect(b_loc);
                        player.sendMessage("Нужный блок установлен, осталось его активировать");
                        return;
                    }

                    b_loc.getBlock().setType(Material.BEDROCK);
                    block.getWorld().strikeLightningEffect(b_loc);

                    List<String> commands = altars.getStringList("altars." + altar_name + "." + BlockID + ".commands");
                    if(!commands.isEmpty()){
                        for(String cmd : commands){

                            String sendcmd = cmd.replace(
                                    "{player}", player.getName())
                                    .replace("{altarName}", altar_name);
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), sendcmd);
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

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getPlayer().getItemInHand().getType() == Material.AIR) return;
        Block block = e.getClickedBlock();
        Location b_loc = block.getLocation();
        String xyz = b_loc.getBlockX() + "; " + b_loc.getBlockY() + "; " + b_loc.getBlockZ()+";";
        String world = b_loc.getWorld().getName();

        String result = db.Find_block(block.getType().toString(), xyz, world);

        if(result == null) return;

        String[] arr = result.split("; ");

        e.getPlayer().sendMessage("Ты кликнул по блоку алтаря!");

        File file = new File("plugins/KARaltars/altars.yml");

        FileConfiguration altars = YamlConfiguration.loadConfiguration(file);

        if(altars.getBoolean("altars." + arr[0] + "." + arr[1] + ".activated")){
            e.getPlayer().sendMessage("Колонна уже активированна");
            return;
        }

        if(Objects.equals(arr[2], "none")) return;


    //    if(!Objects.equals(arr[2], altars.getString("altars." + arr[0] + "." + arr[1] + ".item"))) return;

        if(!Objects.equals(block.getType().toString(), altars.getString("altars." + arr[0] + "." + arr[1] + ".block"))) return;
        if(!Objects.equals(e.getPlayer().getItemInHand().getType().toString(), arr[2])) return;
        if(e.getPlayer().getItemInHand().getLore() == null) return;


        if(!Objects.equals(Objects.requireNonNull(e.getPlayer().getItemInHand().getLore()).get(0), arr[3])) return;


//если нужный итем
        b_loc.getBlock().setType(Material.BEDROCK);
        block.getWorld().strikeLightningEffect(b_loc);

        List<String> commands = altars.getStringList("altars." + arr[0] + "." + arr[1] + ".commands");
        if(!commands.isEmpty()){
            for(String cmd : commands){

                String sendcmd = cmd.replace(
                                "{player}", e.getPlayer().getName())
                        .replace("{altarName}", arr[0]);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), sendcmd);
            }
        }


        altars.set("altars." + arr[0] + "." + arr[1] + ".activated", true);
        try{
            altars.save(file);
        }
        catch (IOException e1){
            e1.printStackTrace();
        }
        e.getPlayer().getItemInHand().add(-1);


    }
}
