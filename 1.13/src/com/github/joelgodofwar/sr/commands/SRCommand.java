package com.github.joelgodofwar.sr.commands;

import com.github.joelgodofwar.sr.ShulkerRespawner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SRCommand implements CommandExecutor {
    ShulkerRespawner plugin;
    
    public SRCommand(ShulkerRespawner sr) {
        this.plugin = sr;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String... args) {
        if (sender instanceof Player) { /* check if player has permission. (assume if being executed as mobs or as console, already
                                            has permission. */
            Player player = (Player) sender;
            if (!(player.hasPermission("shulkerrespawner.op") || player.isOp())) {
                player.sendMessage(ChatColor.DARK_RED + "" + plugin.getLang().get("noperm." + ShulkerRespawner.daLang + ""));
                return true;
            }
        }
    
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) { // no arguments, so send help message
            // Command code
            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
            sender.sendMessage(ChatColor.GOLD + "Commands:");
            sender.sendMessage(
                    ChatColor.GOLD + " /sr DEBUG [true|false] - " + plugin.getLang().get("srdebuguse." + ShulkerRespawner.daLang + ""));
            sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------------");
            return true;
        }
        if (args[0].equalsIgnoreCase("DEBUG")) { //set debug command
            if (args.length < 2) {
                return false;
            }
            // Command code
            if (!args[1].equalsIgnoreCase("true") & !args[1].equalsIgnoreCase("false")) {
                sender.sendMessage(
                        ChatColor.YELLOW + plugin.getName() + " �c" +
                        plugin.getLang().get("boolean." + ShulkerRespawner.daLang + "") + ": /SR DEBUG True/False");
            } else {
                switch (args[1].toLowerCase()) {
                    case "false":
                        plugin.setDebug(false);
                        sender.sendMessage(ChatColor.YELLOW + plugin.getName() + " " +
                                           plugin.getLang().get("debugfalse." + ShulkerRespawner.daLang + ""));
                        break;
                    case "true":
                        plugin.setDebug(true);
                        sender.sendMessage(ChatColor.YELLOW + plugin.getName() + " " +
                                           plugin.getLang().get("debugtrue." + ShulkerRespawner.daLang + ""));
                        break;
                    default:
                        sender.sendMessage(
                                "Something went very very wrong and even though we already determined that your second argument was " +
                                "either true or false, it was neither true nor false. If you are seeing this, then do panic. This " +
                                "should " +
                                "never happen.");
                        return false;
                }
            }
        }
        return false;
    }
}
