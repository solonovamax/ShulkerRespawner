/*
 * Copyright © 2020 solonovamax <solonovamax@12oclockpoint.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        if (args.length == 0) {
            sender.sendMessage("----------------------Commands----------------------");
            sender.sendMessage("/sr-help <command-name> or /sr-help:");
            sender.sendMessage("    The help command.");
            sender.sendMessage("    Aliases: shulkerrespawner-help");
            
            sender.sendMessage("/sr-debug set [true|false] or /sr-debug get:");
            sender.sendMessage("    Allows you to set the debug value.");
            sender.sendMessage("    Aliases: shulkerrespawner-help");
            
            sender.sendMessage("/sr-lang set <language-code> or /sr-lang list:");
            sender.sendMessage("    Allows you to set the language.");
            sender.sendMessage("    Aliases: shulkerrespawner-lang");
            
            sender.sendMessage("/sr-distance <command-name> or /sr-distance:");
            sender.sendMessage("    Allows you to change the distance.");
            sender.sendMessage("    Aliases: shulkerrespawner-distance");
            
            sender.sendMessage("\n Do /sr-help <command-name> for more info on each command.");
            sender.sendMessage("----------------------------------------------------");
            return true;
        }
        switch (args[0]) {
            case "distance":
            case "sr-distance":
            case "shulkerrespawner-distance":
                sender.sendMessage("-----------------------Distance-----------------------");
                sender.sendMessage("Usage:");
                sender.sendMessage("    /sr-distance set <distance>");
                sender.sendMessage("        Allows you to set the distance to the provide value.");
                sender.sendMessage("    /sr-distance get");
                sender.sendMessage("        Allows you to get the current distance between\n" +
                                   "        shulkers.");
                sender.sendMessage("Aliases:");
                sender.sendMessage("    /shulkerrespawner-distance");
                sender.sendMessage("-----------------------------------------------------");
                return true;
            case "debug":
            case "sr-debug":
            case "shulkerrespawner-debug":
                sender.sendMessage("-----------------------Debug-----------------------");
                sender.sendMessage("Usage:");
                sender.sendMessage("    /sr-debug set [true|false]");
                sender.sendMessage("        Sets the debug value to either true or false.");
                sender.sendMessage("    /sr-distance get");
                sender.sendMessage("        Allows you to check if debug is true or false.");
                sender.sendMessage("Aliases:");
                sender.sendMessage("    /shulkerrespawner-debug");
                sender.sendMessage("---------------------------------------------------");
                return true;
            case "lang":
            case "sr-lang":
            case "shulkerrespawner-lang":
                sender.sendMessage("----------------------Language----------------------");
                sender.sendMessage("Usage:");
                sender.sendMessage("    /sr-lang set <language-code>");
                sender.sendMessage("        Sets the language.");
                sender.sendMessage("    /sr-lang list");
                sender.sendMessage("        Lists the available languages.");
                sender.sendMessage("Aliases:");
                sender.sendMessage("    /shulkerrespawner-lang");
                sender.sendMessage("----------------------------------------------------");
                return true;
            default:
                return false;
        }
    }
}
