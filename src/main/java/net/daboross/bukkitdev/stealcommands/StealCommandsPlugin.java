/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
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

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
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
        for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
            if (plugin instanceof JavaPlugin) {
                JavaPlugin javaPlugin = (JavaPlugin) plugin;
                for (String name : plugin.getDescription().getCommands().keySet()) {
                    PluginCommand pluginCommand = javaPlugin.getCommand(name);
                    pluginCommand.setExecutor(this);
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "Trololololol.");
        return true;
    }

    public class StealCommandsRunnable implements Runnable {

        @Override
        public void run() {
            StealCommandsPlugin.this.stealCommands();
        }
    }
}
