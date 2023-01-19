package remainworlds.com.karaltars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import remainworlds.com.karaltars.SQLite.AltarsDB;

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


        if(db.Find_block(block.getType().toString(), xyz)){
            player.sendMessage("Это тот блок!");

        }
        else player.sendMessage("хуйня блок");

        player.sendMessage("Block: " + block.getType());
        player.sendMessage("B_loc: x = " + b_loc.getBlockX() + "; y = " + b_loc.getBlockY() + "; z = " + b_loc.getBlockZ());
    }

}
