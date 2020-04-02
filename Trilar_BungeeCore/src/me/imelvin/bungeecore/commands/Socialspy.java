package me.imelvin.bungeecore.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Socialspy extends Command {

	public Socialspy() {
		super("socialspy", "", "sspy");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (!(sender instanceof ProxiedPlayer)) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "You must be a player to perform this command!"));
			} else {
				if (args.length != 0) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /socialspy"));
				} else {
					if (Msg.socialspy.contains(sender.getName())) {
						Msg.socialspy.remove(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Your Social Spy has been " + ChatColor.AQUA + "disabled" + ChatColor.DARK_AQUA + "!"));
					} else {
						Msg.socialspy.add(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "Your Social Spy has been " + ChatColor.AQUA + "enabled" + ChatColor.DARK_AQUA + "!"));
					}
				}
			}
	}
}
