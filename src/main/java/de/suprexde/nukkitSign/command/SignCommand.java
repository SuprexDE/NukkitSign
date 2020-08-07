package de.suprexde.nukkitSign.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import de.suprexde.nukkitSign.NukkitSign;


public class SignCommand extends Command {

    private NukkitSign nukkitSign;
    public SignCommand(NukkitSign nukkitSign){

        super("sign");
        this.nukkitSign = nukkitSign;
        this.setPermission("signcommand.use");
        this.setDescription(nukkitSign.getMessage("signCommandDescription"));

        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("Text", CommandParamType.TEXT, true)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(nukkitSign.getPrefix() + "§cError: You are not a Player");
            return true;
        }

        if(!testPermission(sender)) return true;

        Player player = (Player) sender;
        Item itemSign = player.getInventory().getItemInHand();

        if(itemSign.count == 0){
            player.sendMessage(nukkitSign.getPrefix() + nukkitSign.getMessage("noIteminHand"));
            return true;
        }

        if((itemSign.count != 1 && !nukkitSign.getAllowMultiSign())){
            player.sendMessage(nukkitSign.getPrefix() + nukkitSign.getMessage("disableMultiItemSign"));
            return true;
        }

        if(args.length <= 0){
            Item newSignItem = itemSign.setLore(nukkitSign.getSignMessage(player, "", false));
            player.getInventory().setItemInHand(newSignItem);
        } else {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            if(builder.length() > nukkitSign.getMaxSignlenght()){
                player.sendMessage(nukkitSign.getPrefix() + nukkitSign.getMessage("maxSignlenghterror").replace("%signlenght%", nukkitSign.getMaxSignlenght().toString()));
                return true;
            }
            Item newSignItem = itemSign.setLore(nukkitSign.getSignMessage(player, builder.toString(), true));
            player.getInventory().setItemInHand(newSignItem);
        }

        player.sendMessage(nukkitSign.getPrefix() + nukkitSign.getMessage("successfulmessage"));
        return false;
    }
}
