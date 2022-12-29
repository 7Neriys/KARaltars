package remainworlds.com.karaltars;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import remainworlds.com.karaltars.command.AltarCommand;

public final class KARaltars extends JavaPlugin {


    private static KARaltars instance;
    @Override
    public void onEnable() {
       instance = this;
       new AltarCommand();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static KARaltars getInstance(){
        return instance;
    }
}
