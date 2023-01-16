package remainworlds.com.karaltars;

import java.util.logging.Level;

public class Error {
    public static void execute(KARaltars plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(KARaltars plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}