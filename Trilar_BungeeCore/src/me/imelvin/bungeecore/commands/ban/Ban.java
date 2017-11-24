package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Ban extends Command {

	public Ban() {
		super("ban");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.MODERATOR)) {
			if (args.length < 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /ban <player> [reason]"));
			} else if (args.length == 1) {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					sender.sendMessage(new TextComponent(ChatColor.GREEN + "This player is already banned!"));
				} else {
					BanHandler.banPlayer(name, null);
				}
			} else if (args.length > 1) {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					sender.sendMessage(new TextComponent(Main.prefix + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has already been banned!"));
				} else {
					String reason = "";
					for (int i = 1; i < args.length; i++) {
						reason += args[i] + " ";
					}
					BanHandler.banPlayer(name, reason.trim());
				}
			}
		} else {
			p.sendMessage(new TextComponent(Main.prefix + "You do not have permission to perform this command!"));
		}
	}
}