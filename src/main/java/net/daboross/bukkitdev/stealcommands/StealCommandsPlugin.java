/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.stealcommands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 */
public class StealCommandsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getScheduler().runTaskTimer(this, new StealCommandsRunnable(), 0, TimeUnit.MINUTES.toSeconds(14) * 20);
    }

    @Override
    public void onDisable() {
    }

    private void stealCommands() {
        CommandExecutor real = new StealCommand();
        for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
            if (plugin instanceof JavaPlugin) {
                JavaPlugin javaPlugin = (JavaPlugin) plugin;
                Map<String, ?> commandMap = plugin.getDescription().getCommands();
                if (commandMap != null) {
                    for (String name : commandMap.keySet()) {
                        PluginCommand pluginCommand = javaPlugin.getCommand(name);
                        if (pluginCommand != null) {
                            pluginCommand.setExecutor(real);
                        }
                    }
                }
            }
        }
    }

    public class StealCommandsRunnable implements Runnable {

        @Override
        public void run() {
            StealCommandsPlugin.this.stealCommands();
        }
    }

    public static class StealCommand implements TabExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            sender.sendMessage(rainbow("Trololololol."));
            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            sender.sendMessage(rainbow("Trololololol."));
            if (args.length == 0) {
                return Arrays.asList(rainbow("I-am-a-terrible-troll"));
            } else {
                return Arrays.asList(args[args.length - 1] + rainbow("-that's-ok"));
            }
        }
    }
    private static int colorNum;
    private static ChatColor[] colors = ChatColor.values();

    private static String rainbow(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append(colors[colorNum++]).append(input.charAt(i));
            if (colorNum >= colors.length) {
                colorNum = 0;
            }
        }
        return output.toString();
    }
}
