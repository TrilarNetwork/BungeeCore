package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Unban extends Command 	{

	public Unban() {
		super("unban");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.MODERATOR)) {
			if (args.length != 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /unban <player>"));
			} else if (args.length == 1) {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					BanHandler.unbanPlayer(name);
					Chat.msgAllOps(Main.prefix + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been unbanned by " + ChatColor.GOLD + sender.getName()
							+ ChatColor.YELLOW + "!");
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player is not banned!"));
				}
			}
		} else {
			p.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
