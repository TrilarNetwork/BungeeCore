package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Kick extends Command {

	public Kick() {
		super("kick");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.MODERATOR)) {
			if (args.length == 0) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /kick <player> [reason]"));
			} else if (args.length == 1) {
				ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
				if (tp.isConnected()) {
					BanHandler.kickPlayer(tp.getName(), null);
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player is not online!"));
				}
			} else if (args.length > 1) {
				String reason = "";
				for (int i = 1; i < args.length; i++) {
					reason += args[i] + " ";
				}
				ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
				if (tp.isConnected()) {
					BanHandler.kickPlayer(tp.getName(), reason.trim());
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player is not online!"));
				}
			}
		} else {
			p.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
