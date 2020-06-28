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
