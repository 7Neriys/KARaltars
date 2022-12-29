package remainworlds.com.karaltars.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import remainworlds.com.karaltars.KARaltars;

import java.util.Objects;

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
        if(Objects.equals(args[0], "reload")){
            if(!sender.hasPermission("KARaltars.reload")){
                sender.sendMessage(ChatColor.RED + "you don't have permissions");
                return;
            }
            KARaltars.getInstance().reloadConfig();
            sender.sendMessage("KARaltars reloaded");
            return;
        }


        if(Objects.equals(args[0], "create")){
            if(!sender.hasPermission("KARaltars.create")){
                sender.sendMessage(ChatColor.RED + "you don't have permissions");
                return;
            }
            if(args.length==1){
                sender.sendMessage(label + " " + args[0] + "<name>");
                return;
            }
            KARaltars.getData().getConfig().set("altars.", args[1]);
            sender.sendMessage("Altar " + args[1] + " created");
            return;

        }















        sender.sendMessage(ChatColor.RED + "Unknown command: " + args[0]);
    }
}
