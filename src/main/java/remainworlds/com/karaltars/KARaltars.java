package remainworlds.com.karaltars;

import org.bukkit.plugin.java.JavaPlugin;
import remainworlds.com.karaltars.command.AddBlockToAltar;
import remainworlds.com.karaltars.command.CommandReload;
import remainworlds.com.karaltars.command.CreateAltar;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class KARaltars extends JavaPlugin {

    private static KARaltars instance;
    Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        instance = this;

        File file = new File("plugins/KARaltars");
        file.mkdir();



        File config = new File("plugins/KARaltars/config.yml");
        if(!config.exists()){
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        File altarsFile = new File("plugins/KARaltars/altars.yml");
        if(!altarsFile.exists()){
            try {
                altarsFile.createNewFile();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        getCommand("altarreload").setExecutor(new CommandReload(this));
        getCommand("createaltar").setExecutor(new CreateAltar(this));
        getCommand("addaltarblock").setExecutor(new AddBlockToAltar(this));

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static KARaltars getInstance(){
        return instance;
    }

}
