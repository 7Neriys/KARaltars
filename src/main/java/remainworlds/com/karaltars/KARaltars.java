package remainworlds.com.karaltars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.command.AltarCommand;

public final class KARaltars extends JavaPlugin {


    private static KARaltars instance;
    private ListAltars date;
    @Override
    public void onEnable() {
       instance = this;
       new AltarCommand();

        saveDefaultConfig();
        date = new ListAltars("altars.yml");
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
