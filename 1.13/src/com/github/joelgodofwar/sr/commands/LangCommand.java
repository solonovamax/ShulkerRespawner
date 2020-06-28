package com.github.joelgodofwar.sr.commands;

import com.github.joelgodofwar.sr.ShulkerRespawner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class LangCommand implements CommandExecutor {
    ShulkerRespawner shulkerRespawner;
    
    public LangCommand(ShulkerRespawner sp) {
        this.shulkerRespawner = sp;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) { // no arguments, so send help message
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "list":
                StringBuilder langStringBuilder = new StringBuilder();
                langStringBuilder.append("Here are a list of languages: \n");
                for (String lang : shulkerRespawner.getLang().getStringList("lang-types")) {
                    langStringBuilder.append(lang).append(", ");
                }
                langStringBuilder.delete(langStringBuilder.length() - 2, langStringBuilder.length());
                langStringBuilder.append(".");
                sender.sendMessage(langStringBuilder.toString());
                return true;
            case "set":
                if (args.length < 2)
                    return false;
                if (shulkerRespawner.getLang().getStringList("lang-types").contains(args[1])) {
                    shulkerRespawner.setDaLang(args[1]);
                    sender.sendMessage(ChatColor.YELLOW + shulkerRespawner.getLang()
                                                                          .getString(
                                                                                  "changedlanguage" + shulkerRespawner.getDaLang() + " " +
                                                                                  args[1]));
                    return true;
                } else {
                    sender.sendMessage(
                            ChatColor.YELLOW + shulkerRespawner.getLang().getString("invalidlanguage" + shulkerRespawner.getDaLang()));
                    return false;
                }
            default:
                return false;
        }
    }
}
