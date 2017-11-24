package me.imelvin.bungeecore.commands;

import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Socialspy extends Command {

	public Socialspy() {
		super("socialspy", "", "sspy");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer pl = (ProxiedPlayer) sender;
		if (Perm.hasPerm(pl, PermGroup.HELPER)) {
			if (!(sender instanceof ProxiedPlayer)) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "You must be a player to perform this command!"));
			} else {
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(sender.getName());
				if (args.length != 0) {
					p.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /socialspy"));
				} else {
					if (Msg.socialspy.contains(p.getName())) {
						Msg.socialspy.remove(p.getName());
						p.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Your Socialspy has been " + ChatColor.AQUA + "disabled" + ChatColor.DARK_AQUA + "!"));
					} else {
						Msg.socialspy.add(p.getName());
						p.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Your Socialspy has been " + ChatColor.AQUA + "enabled" + ChatColor.DARK_AQUA + "!"));
					}
				}
			}
		} else {
			pl.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
