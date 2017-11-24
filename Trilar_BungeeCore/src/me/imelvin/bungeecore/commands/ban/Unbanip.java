package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.IPSaver;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Unbanip extends Command {

	public Unbanip() {
		super("unbanip");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.MODERATOR)) {
			if (args.length != 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /unbanip <player|ip>"));
			} else {
				if (args[0].contains(".")) {
					String ip = args[0];
					if (BanHandler.isIPBanned(ip, true)) {
						BanHandler.unbanIP(IPSaver.getNameFromIp(ip), true);
						Chat.msgAllOps(Main.prefix + "IP: " + ChatColor.GOLD + ip + ChatColor.YELLOW + "(" + ChatColor.GOLD + IPSaver.getNameFromIp(ip) 
							+ ChatColor.YELLOW + ") has been unbanned by " + ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + "!");
					}
				} else {
					String name = args[0];
					if (BanHandler.isIPBanned(name, false)) {
						BanHandler.unbanIP(name, false);
						String ip = ProxyServer.getInstance().getPlayer(name).getAddress().toString();
						Chat.msgAllOps(Main.prefix + "IP: " + ChatColor.GOLD + ip + ChatColor.YELLOW + "(" + ChatColor.GOLD + IPSaver.getNameFromIp(ip) 
						+ ChatColor.YELLOW + ") has been unbanned by " + ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + "!");
					}
				}
			}
		} else {
			p.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
