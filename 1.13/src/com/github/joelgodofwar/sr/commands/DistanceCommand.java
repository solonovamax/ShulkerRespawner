package com.github.joelgodofwar.sr.commands;

import com.github.joelgodofwar.sr.ShulkerRespawner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class DistanceCommand implements CommandExecutor {
    ShulkerRespawner shulkerRespawner;
    
    public DistanceCommand(ShulkerRespawner sp) {
        this.shulkerRespawner = sp;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                               shulkerRespawner.getLang().getString("distancegetvalue." + shulkerRespawner.getDistanceBetweenShulkers()));
        }
        switch (args[0]) {
            case "set":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                       shulkerRespawner.getLang().getString("noargument." + shulkerRespawner.getDaLang()));
                    return false;
                }
                try {
                    double distance = Double.parseDouble(args[1]);
                    shulkerRespawner.setDistanceBetweenShulkers(distance);
                    sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                       shulkerRespawner.getLang().getString("distancesetvalue." + shulkerRespawner.getDaLang()) + " " +
                                       distance);
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                       shulkerRespawner.getLang().getString("distancebadvalue." + shulkerRespawner.getDaLang()));
                    return false;
                }
            case "get":
                sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getName() + " " +
                                   shulkerRespawner.getLang().getString("distancegetvalue." + shulkerRespawner.getDaLang()) + " " +
                                   shulkerRespawner.getDistanceBetweenShulkers());
                return true;
            default:
                return false;
        }
    }
}
