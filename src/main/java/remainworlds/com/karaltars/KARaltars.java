package remainworlds.com.karaltars;

import org.bukkit.plugin.java.JavaPlugin;
import remainworlds.com.karaltars.command.AltarCommand;

import java.io.File;
import java.util.logging.Logger;

public final class KARaltars extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    private Database db;
    private static KARaltars instance;
    private ListAltars date;
    @Override
    public void onEnable() {


       instance = this;
       new AltarCommand();

        saveDefaultConfig();
        date = new ListAltars("altars.yml");

        File file = new File("plugins/KARaltars");
        boolean flag = file.mkdir();
        if(flag) log.info("Folder had been created");
        else log.info("Folder hadn't been created");


    }
    public Database getRDatabase() {
        return this.db;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static KARaltars getInstance(){
        return instance;
    }
    public static ListAltars getData(){
        return instance.date;
    }
}
