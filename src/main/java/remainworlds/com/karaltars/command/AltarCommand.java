package remainworlds.com.karaltars.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import remainworlds.com.karaltars.KARaltars;

public class AltarCommand extends AbstractCommand{

    public AltarCommand(){
        super("altars");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("Reload: /" + label + " reload");
            return;
        }
        if(args[0]=="reload"){
            if(!sender.hasPermission("KARaltars.reload")){
                sender.sendMessage(ChatColor.RED + "you don't have permissions");
                return;
            }
            KARaltars.getInstance().reloadConfig();
            sender.sendMessage("KARaltars reloaded");
            return;
        }
        sender.sendMessage(ChatColor.RED + "Unknown command: " + args[0]);
    }
}
