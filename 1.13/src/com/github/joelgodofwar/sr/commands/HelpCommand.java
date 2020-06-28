package com.github.joelgodofwar.sr.commands;

import com.github.joelgodofwar.sr.ShulkerRespawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class HelpCommand implements CommandExecutor {
    ShulkerRespawner shulkerRespawner;
    
    public HelpCommand(ShulkerRespawner sp) {
        this.shulkerRespawner = sp;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
