package me.imelvin.bungeecore.commands.ban;

import me.imelvin.bungeecore.handlers.BanHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Baninfo extends Command {

	public Baninfo() {
		super("lookup", "", "plookup", "playerlookup", "lookupplayer");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /lookup <player>"));
		} else {
			String name = args[0];
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "--=[ " + ChatColor.AQUA + "Player lookup: "
					+ name + ChatColor.DARK_AQUA + " ]=--"));
			String pban;
			boolean pbanr = false;
			if (BanHandler.isPermBanned(name)) {
				pban = ChatColor.GREEN + "true";
				pbanr = true;
			} else {
				pban = ChatColor.RED + "false";
			}
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Permanently Banned: " + pban));
			if (pbanr) {
				sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Reason: "
						+ ChatColor.translateAlternateColorCodes('&', BanHandler.getBanReason(name))));
			}
			String tban;
			boolean tbanr = false;
			if (BanHandler.isTempBanned(name)) {
				tban = ChatColor.GREEN + "true";
				tbanr = true;
			} else {
				tban = ChatColor.RED + "false";
			}
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Temporarily Banned: " + tban));
			if (tbanr) {
				sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Reason: "
						+ ChatColor.translateAlternateColorCodes('&', BanHandler.getTempBanReason(name))));
			}
			String ipban;
			boolean ipbanr = false;
			if (BanHandler.isIPBanned(name, false)) {
				ipban = ChatColor.GREEN + "true";
				ipbanr = true;
			} else {
				ipban = ChatColor.RED + "false";
			}
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "IP-Banned: " + ipban));
			if (ipbanr) {
				sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Reason: "
						+ ChatColor.translateAlternateColorCodes('&', BanHandler.getIPBanReason(name, false))));
			}
			String kicks = ChatColor.AQUA + "" + BanHandler.getKicks(name);
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Kicks: " + kicks));
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Last Reason: "
					+ ChatColor.translateAlternateColorCodes('&', BanHandler.getKickReason(name))));
			String mutes = ChatColor.AQUA + "" + BanHandler.getMutes(name);
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Mutes: " + mutes));
			String ismuted;
			if (BanHandler.isMuted(name)) {
				ismuted = ChatColor.GREEN + "true";
			} else {
				ismuted = ChatColor.RED + "false";
			}
			sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Muted: " + ismuted));
		}
	}
}
