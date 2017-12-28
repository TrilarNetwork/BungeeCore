package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.IPSaver;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Banip extends Command {

	public Banip() {
		super("banip");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length == 0) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /banip <player|ip> [reason]"));
			} else if (args.length == 1) {
				if (args[0].contains(".")) {
					String name = IPSaver.getNameFromIp(args[0]);
					if (BanHandler.isIPBanned(name, false)) {
						sender.sendMessage(new TextComponent(ChatColor.GREEN + "That player has already been ip-banned!"));
					} else {
						BanHandler.setIPBanned(name, null, args[0]);
					}
				} else {
					if (BanHandler.isBanned(args[0])) {
						sender.sendMessage(new TextComponent(ChatColor.GREEN + "That player has already been ip-banned!"));
					} else {
						ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
						String ip = tp.getAddress().toString();
						BanHandler.setIPBanned(tp.getName(), null, ip);
					}
				}
			} else if (args.length > 1) {
				if (args[0].contains(".")) {
					String name = IPSaver.getNameFromIp(args[0]);
					if (BanHandler.isIPBanned(name, false)) {
						sender.sendMessage(new TextComponent(ChatColor.GREEN + "That player has already been ip-banned!"));
					} else {
						String reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						BanHandler.setIPBanned(name, reason.trim(), args[0]);
					}
				} else {
					if (BanHandler.isBanned(args[0])) {
						sender.sendMessage(new TextComponent(ChatColor.GREEN + "That player has already been ip-banned!"));
					} else {
						ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
						String ip = tp.getAddress().toString();
						String reason = "";
						for (int i = 1; i < args.length; i++) {
							reason += args[i] + " ";
						}
						BanHandler.setIPBanned(tp.getName(), reason.trim(), ip);
					}
				}
			}
	}
}
