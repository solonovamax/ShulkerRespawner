package com.github.joelgodofwar.sr.commands;

import com.github.joelgodofwar.sr.ShulkerRespawner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class DebugCommand implements CommandExecutor {
    ShulkerRespawner shulkerRespawner;
    
    public DebugCommand(ShulkerRespawner sp) {
        this.shulkerRespawner = sp;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                               shulkerRespawner.getLang().getString("debuggetvalue." + shulkerRespawner.getDaLang()) + " " +
                               shulkerRespawner.isDebug() + ".");
            return false;
        }
        switch (args[0]) {
            case "set":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                       shulkerRespawner.getLang().getString("noargument." + shulkerRespawner.getDaLang()));
                    return false;
                }
                switch (args[1].toLowerCase()) {
                    case "false":
                        shulkerRespawner.setDebug(false);
                        sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                           shulkerRespawner.getLang().getString("debugfalse." + shulkerRespawner.getDaLang()));
                        return true;
                    case "true":
                        shulkerRespawner.setDebug(true);
                        sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                           shulkerRespawner.getLang().getString("debugtrue." + shulkerRespawner.getDaLang()));
                        return true;
                    default:
                        sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                           shulkerRespawner.getLang().getString("noargument." + shulkerRespawner.getDaLang()));
                        return false;
                }
            case "get":
                sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                   shulkerRespawner.getLang().getString("debuggetvalue." + shulkerRespawner.getDaLang()) + " " +
                                   shulkerRespawner.isDebug() + ".");
                return true;
            default:
                return false;
        }
    }
}
