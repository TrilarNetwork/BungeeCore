package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Unban extends Command 	{

	public Unban() {
		super("unban");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length != 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /unban <player>"));
			} else {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					BanHandler.unbanPlayer(name);
					Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been unbanned by " + ChatColor.GOLD + sender.getName()
							+ ChatColor.YELLOW + "!");
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player is not banned!"));
				}
			}
	}
}
