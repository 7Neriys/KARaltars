package remainworlds.com.karaltars;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ListAltars {

    private File file;
    private FileConfiguration config;

    public ListAltars(String name){
        file = new File(KARaltars.getInstance().getDataFolder(), name);
        try {
            if(!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e){
            throw new RuntimeException("failed to create", e);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e){
            throw new RuntimeException("failed to save a", e);
        }
    }
}
