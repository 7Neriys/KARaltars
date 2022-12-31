package remainworlds.com.karaltars.command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
                sender.sendMessage(label + " " + args[0] + " <name> <count of blocks>");
                return;
            }

            if(args.length==2){
                sender.sendMessage(label + " " + args[0] + args[1] + " <count of blocks>");
                return;
            }

            try {
                Integer.parseInt(args[2]);

            } catch (NumberFormatException e){
                sender.sendMessage("Not a number");
                return;
            }


            KARaltars.getData().getConfig().set("altars." + args[1] + ".count", Integer.parseInt(args[2]));
            KARaltars.getData().save();
            sender.sendMessage("Altar " + args[1] + " created. " + args[2] + " blocks ");
            return;

        }
        if(Objects.equals(args[0], "addblock")) {

                if (!sender.hasPermission("KARaltars.addblock")) {
                    sender.sendMessage(ChatColor.RED + "you don't have permissions");
                    return;
                }
                if (args.length == 1) {
                    sender.sendMessage(label + " " + args[0] + " <altar name>");
                    return;
                }


                if(Objects.equals(((Player) sender).getItemInHand().getType().toString(), "AIR")){
                    sender.sendMessage("Do not AIR");
                    return;
                }

                int[] l = new int[KARaltars.getData().getConfig().getInt("altars." + args[1] + ".count")];
                for(int i = 0; i< l.length; i++){
                    if(KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i) == null){

                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".block", ((Player) sender).getItemInHand().getType().toString());
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".world", ((Player) sender).getLocation().getWorld().getName().toString());
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".coord.x", ((Player) sender).getLocation().getBlockX());
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".coord.y", ((Player) sender).getLocation().getBlockY());
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".coord.z", ((Player) sender).getLocation().getBlockZ());
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".command", "");
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".particle.true", "");
                        KARaltars.getData().getConfig().set("altars." + args[1] + "." + i + ".particle.false", "");
                        KARaltars.getData().save();
                        sender.sendMessage("Block " + ((Player) sender).getItemInHand().getType() + " for altar " + args[1] + " created");
                        return;
                    }
                }
                sender.sendMessage("max alredy created");


            }
        if(Objects.equals(args[0], "listblocks")) {

            if (!sender.hasPermission("KARaltars.listblocks")) {
                sender.sendMessage(ChatColor.RED + "you don't have permissions");
                return;
            }
            if (args.length == 1) {
                sender.sendMessage(label + " " + args[0] + " <altar name>");
                return;
            }

            int[] l = new int[KARaltars.getData().getConfig().getInt("altars." + args[1] + ".count")];
            for(int i = 0; i< l.length; i++){
                if(KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i) != null){


                    sender.sendMessage(i + ": " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".block"));
                    sender.sendMessage("world: " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".world"));
                    sender.sendMessage("cords: " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".coord.x") + " " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".coord.y") + " " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".coord.z"));
                    sender.sendMessage("command: " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".command"));
                    sender.sendMessage("particle true: " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".particle.true"));
                    sender.sendMessage("particle false: " + KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i + ".particle.false"));

                }
                else {
                    sender.sendMessage(i + ": none");
                }
            }
            return;
        }
        if(Objects.equals(args[0], "removeblock")) {

            if (!sender.hasPermission("KARaltars.removeblock")) {
                sender.sendMessage(ChatColor.RED + "you don't have permissions");
                return;
            }
            if (args.length == 1) {
                sender.sendMessage(label + " " + args[0] + " <altar name> <number of block>");
                return;
            }
            if (args.length == 2) {
                sender.sendMessage(label + " " + args[0] +" " +args[1] + " <number of block>");
                return;
            }
            try {
                Integer.parseInt(args[2]);

            } catch (NumberFormatException e){
                sender.sendMessage("Not a number");
                return;
            }

            int[] l = new int[KARaltars.getData().getConfig().getInt("altars." + args[1] + ".count")];
            for(int i = 0; i< l.length; i++){
                if(i == Integer.parseInt(args[2]) && KARaltars.getData().getConfig().getString("altars." + args[1] + "." + i) != null){

                    KARaltars.getData().getConfig().set("altars." + args[1] + "." + i, null);
                    KARaltars.getData().save();
                    sender.sendMessage("block "+i+ "deleted");
                    return;

                }
            }
            sender.sendMessage("Unknown block");
            return;
        }


        sender.sendMessage(ChatColor.RED + "Unknown command: " + args[0]);
    }
}
