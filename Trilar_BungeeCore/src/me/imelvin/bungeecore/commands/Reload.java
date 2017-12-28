package me.imelvin.bungeecore.commands;

import me.imelvin.bungeecore.ConfigManager;
import me.imelvin.bungeecore.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Reload extends Command {
	
	public Reload() {
		super("trilarreload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Reloading..."));
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
				sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Successfully established connection with database..."));
			}
			Main.sql.createTable();
			ConfigManager.getInstance().reloadConfig();
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Successfully created tables that did not exist..."));
			sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "Reload of Trilar Core has been completed!"));
	}	
}
